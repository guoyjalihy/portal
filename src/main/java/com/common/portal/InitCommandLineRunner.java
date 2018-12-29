package com.common.portal;

import com.common.portal.dao.MenuRepository;
import com.common.portal.dao.NewsRepository;
import com.common.portal.dao.UserRepository;
import com.common.portal.entity.News;
import com.common.portal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InitCommandLineRunner implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;
	@Autowired
	NewsRepository newsRepository;
	@Autowired
	MenuRepository menuRepository;

	@Override
	public void run(String... args) throws Exception {
		
	}
}
