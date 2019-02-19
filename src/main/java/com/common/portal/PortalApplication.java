package com.common.portal;

import com.common.portal.socket.AlarmSocketServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@SpringBootApplication
@EnableScheduling
@ComponentScan
public class PortalApplication implements CommandLineRunner {
	private int[] portArr = new int[]{31232,31233,31234};
	@Resource
	private AlarmSocketServer alarmSocketServer;
	@Override
	public void run(String... args) throws Exception{
		alarmSocketServer.startServer(portArr);
	}

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}
}
