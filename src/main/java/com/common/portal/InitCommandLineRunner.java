package com.common.portal;

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

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("admin");
		user.setPassword("admin");
		userRepository.saveAndFlush(user);
		News news = new News();
		news.setAddDate(new Date());
		news.setCategory("经济");
		news.setDescription("文章描述");
		news.setTitle("测试文章");
		newsRepository.saveAndFlush(news);
	}
}
