package com.common.portal.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @description:循环获取告警数据并发送客户端
 * @author: guoyanjun
 * @date: 2018/11/17 18:27
 */
public class LoopAlarmThread extends Thread {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private short startSign = (short)0xFFFF;
    private int timeStamp = (int)System.currentTimeMillis()/1000;
    private byte alarmType = 0;
    Socket socket ;
    DataOutputStream dos ;
    BufferedInputStream bis ;
    long maxId = 0;

    private SyncSwitch syncSwitch = new SyncSwitch();
    /**
     * 构造方法
     */
    public LoopAlarmThread(Socket socket){
        this.socket = socket;
    }

    /**
     * 线程启动
     */
    @Override
    public void run() {
        init();
        maxId = getMaxId();
        logger.info("LoopAlarmThread first connect.maxId:{}", maxId);
        try {
            while (true) {
                Thread.sleep(5000);
                if (syncSwitch.isSyncSwitch()){
                    continue;
                }
                if(hasNewAlarmData(maxId)){
                    //dosomething
                }
            }
        }catch (Exception e){
            logger.error("LoopAlarmThread run error.",e);
        }
    }

    private boolean hasNewAlarmData(long maxId) {
        return false;
    }

    private long getMaxId() {
        //返回最大Id
        return 0;
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
