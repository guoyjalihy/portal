package com.common.portal.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @description:处理客户端请求消息并做出响应
 * @author: guoyanjun
 * @date: 2018/11/17 18:27
 */
public class ClientMsgHandleThread extends Thread {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private short startSign = (short)0xFFFF;
    private int timeStamp = (int)System.currentTimeMillis()/1000;
    private byte alarmType = 0;
    private SyncSwitch syncSwitch = new SyncSwitch();
    Socket socket ;
    DataOutputStream dos ;
    BufferedInputStream bis ;

    /**
     * 构造方法
     */
    public ClientMsgHandleThread(Socket socket){
        this.socket = socket;
    }

    /**
     * 启动线程
     */
    @Override
    public void run() {
        init();
        String inputStr;
        String outputStr;
        DataInputStream dis;
        Thread loopThread = null;

        try {
            while (true) {
                byte[] bytes = new byte[9];
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                dis = new DataInputStream(bis);
                dis.readFully(bytes);
                DataInputStream ois = new DataInputStream(bais);
                short s1 = ois.readShort();
                byte s2 = ois.readByte();
                int s3 = ois.readInt();
                short length = ois.readShort();
                byte[] body = new byte[length];
                dis.readFully(body);
                inputStr = new String(body, "UTF-8");
                logger.info("socket client input info:{}", inputStr);
                if (inputStr.contains("closeConnAlarm")) {
                    logger.info("==============msg contains closeConnAlarm");
                    socket.close();
                    break;
                }

                //4、获取输出流，响应客户端的请求
                outputStr = buildMessage();
                logger.info("socket client output info:{}", outputStr);
                if (StringUtils.isEmpty(outputStr)) {
                    continue;
                }
                dos.writeShort(startSign);
                dos.writeByte(alarmType);
                dos.writeInt(timeStamp);
                dos.writeShort(outputStr.getBytes("UTF-8").length);
                dos.write(outputStr.getBytes("UTF-8"));
                if (inputStr.contains("reqLoginAlarm")
                        || inputStr.contains("reqSyncAlarmFile")){
                    continue;
                }
                if (loopThread == null){
                    loopThread = new LoopAlarmThread(socket);
                    loopThread.start();
                }
            }
        }catch (IOException e){
            logger.error("QueryAlarmThread run error.", e);
            throw new RuntimeException(e);
        }
    }

    private String buildMessage() {
        return "ackLoginAlarm;result=succ;resDesc=null";
    }


    private void init() {
        try {
            bis = new BufferedInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            logger.error("QueryAlarmThread init error.",e);
        }
    }


}
