package com.common.portal.rpc.zabbix;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.portal.rpc.zabbix.vo.EventVO;
import com.common.portal.rpc.zabbix.vo.HostVO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class DefaultZabbixApi implements ZabbixApi {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CloseableHttpClient httpClient;

    @Value("${zabbix.url}")
    private URI uri;

    @Value("${zabbix.username}")
    private String username;

    @Value("${zabbix.password}")
    private String password;

    private volatile String auth;

    @PostConstruct
    public void init() {
        if (httpClient == null) {
            httpClient = HttpClients.custom().build();
        }

        login();
    }

    @Override
    public void destroy() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public List<EventVO> eventGetByQuery(EventVO eventVO) throws ParseException {
        RequestBuilder requestBuilder = RequestBuilder.newBuilder().method("problem.get");
        requestBuilder.paramEntry("sortfield", new String[] { "eventid"});
        requestBuilder.paramEntry("sortorder", "DESC");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        if (!StringUtils.isEmpty(eventVO.getHostId())){
            requestBuilder.paramEntry("hostids", eventVO.getHostId());
        }
        if (!StringUtils.isEmpty(eventVO.getTimeFrom())){
            requestBuilder.paramEntry("time_from", format.parse(eventVO.getTimeFrom()).getTime()/1000);
        }
        if (!StringUtils.isEmpty(eventVO.getTimeTill())){
            requestBuilder.paramEntry("time_till", format.parse(eventVO.getTimeTill()).getTime()/1000);
        }

        Request request = requestBuilder.build();
        JSONObject jsonObject = call(request);
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        List<EventVO> result = jsonArray.toJavaList(EventVO.class);
        logger.info("eventGetByQuery,result:{}",result);

        //关联主机
        result.forEach(event -> {
            event.setHost(getHostByObjectAndObjectId(event.getObject(),event.getObjectId()));
        });
        return result;
    }

    private boolean login() {

        this.auth = null;
        Request request = RequestBuilder.newBuilder().paramEntry("user", username).paramEntry("password", password)
                .method("user.login").build();
        JSONObject response = call(request);
        String auth = response.getString("result");
        if (auth != null && !auth.isEmpty()) {
            this.auth = auth;
            return true;
        }
        return false;
    }

    @Override
    public List<HostVO> hostGetAll() {
        Request request = RequestBuilder.newBuilder()
                .method("host.get")
                .paramEntry("output", new String[] { "hostid","host","status" })
                .paramEntry("selectInterfaces", new String[] {"ip"})
                .build();
        JSONObject jsonObject = call(request);
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        List<HostVO> result = jsonArray.toJavaList(HostVO.class);
        logger.info("hostGetAll,result:{}",result);
        return result;
    }

    @Override
    public List<EventVO> eventGetAll() {
        Request request = RequestBuilder.newBuilder()
                .method("problem.get")
                .paramEntry("sortfield", new String[] { "eventid"})
                .paramEntry("sortorder", "DESC")
                .build();
        JSONObject jsonObject = call(request);
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        List<EventVO> result = jsonArray.toJavaList(EventVO.class);
        logger.info("eventGetAll,result:{}",result);

        //关联主机
        result.forEach(eventVO -> {
            eventVO.setHost(getHostByObjectAndObjectId(eventVO.getObject(),eventVO.getObjectId()));
        });
        return result;
    }

    private String getHostByObjectAndObjectId(Integer object,Long objectId){
        if (object == 0){//triggerid
            return getHostByTriggerId(objectId);
        }else if (object == 4) {//items
            return getHostByItemId(objectId);
        }else{
            return null;
        }
    }

    private String getHostByItemId(Long objectId) {
        return null;
    }

    private String getHostByTriggerId(Long objectId) {
        Request request = RequestBuilder.newBuilder()
                .method("item.get")
                .paramEntry("output", new String[] { "hostid"})
                .paramEntry("triggerids", new String[] {String.valueOf(objectId)})
                .build();
        JSONObject jsonObject = call(request);
        if (jsonObject.getJSONArray("result").size() == 0){
            return null;
        }
        JSONObject result = (JSONObject) jsonObject.getJSONArray("result").get(0);
        String hostId = (String) result.get("hostid");

        request = RequestBuilder.newBuilder()
                .method("host.get")
                .paramEntry("output", new String[] { "host"})
                .paramEntry("hostids", new String[] {String.valueOf(hostId)})
                .build();
        jsonObject = call(request);
        return (String) ((JSONObject) jsonObject.getJSONArray("result").get(0)).get("host");
    }

    @Override
    public JSONObject call(Request request) {
        if (request.getAuth() == null) {
            request.setAuth(this.auth);
        }

        try {
            HttpUriRequest httpRequest = org.apache.http.client.methods.RequestBuilder.post().setUri(uri)
                    .addHeader("Content-Type", "application/json")
                    .setEntity(new StringEntity(JSON.toJSONString(request), ContentType.APPLICATION_JSON)).build();
            CloseableHttpResponse response = httpClient.execute(httpRequest);
            HttpEntity entity = response.getEntity();
            byte[] data = EntityUtils.toByteArray(entity);
            return (JSONObject) JSON.parse(data);
        } catch (IOException e) {
            throw new RuntimeException("DefaultZabbixApi call exception!", e);
        }
    }

}
