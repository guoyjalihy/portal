package com.common.portal.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 告警上报服务端
 * @author: guoyanjun
 * @date: 2018/11/16 10:20
 */
@Component
public class AlarmSocketServer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *  启动服务
     */
    public void startServer(int[] portArr){
        /**
         * 基于TCP协议的Socket通信，实现用户登录，服务端
         */
        //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket = null;//1024-65535的某个端口
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(portArr[0]);
            logger.info("socket start listen.port:{}" , portArr[0]);

            //2、调用accept()方法开始监听，等待客户端的连接
            while(true){
                socket = serverSocket.accept();
                logger.info("socket client:{}" , socket.getRemoteSocketAddress());
                new ClientMsgHandleThread(socket).start();
            }
        } catch (IOException e) {
            logger.error("startServer error.",e);
        }finally {
            try {
                if (socket != null){
                    socket.close();
                }
                if (serverSocket != null){
                    serverSocket.close();
                }
            } catch (IOException e) {
                logger.error("startServer error.",e);
            }
        }
    }
}
