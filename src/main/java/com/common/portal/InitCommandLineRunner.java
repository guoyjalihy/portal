package com.common.portal;

import com.common.portal.dao.MenusRepository;
import com.common.portal.dao.NewsRepository;
import com.common.portal.dao.UserRepository;
import com.common.portal.entity.Menus;
import com.common.portal.entity.News;
import com.common.portal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InitCommandLineRunner implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;
	@Autowired
	NewsRepository newsRepository;
	@Autowired
	MenusRepository menusRepository;

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

//		List<Menus> all = new ArrayList<>();
//		Menus menus1 = new Menus();
//		menus1.setId(1L);
//		menus1.setImage("fa-diamond");
//		menus1.setLevel(1);
//		menus1.setName("仪表盘");
//		menus1.setParent(0L);
//		menus1.setUrl("/dashboard");
//		menus1.setStatus(1);
//		all.add(menus1);
//		Menus menus2 = new Menus();
//		menus2.setId(2L);
//		menus2.setImage("fa-desktop");
//		menus2.setLevel(1);
//		menus2.setName("内容管理");
//		menus2.setParent(0L);
//		menus2.setUrl(null);
//		menus2.setStatus(1);
//		Menus menus3 = new Menus();
//		menus3.setId(3L);
//		menus3.setImage(null);
//		menus3.setLevel(2);
//		menus3.setName("文章管理");
//		menus3.setParent(2L);
//		menus3.setUrl("/newsManage_0_0_0");
//		menus3.setStatus(1);
//		List menus2Sub = new ArrayList();
//		menus2Sub.add(menus3);
//		menus2.setSubMenus(menus2Sub);
//
//		Menus menus4= new Menus();
//		menus4.setImage(null);
//		menus4.setLevel(3);
//		menus4.setName("三级菜单");
//		menus4.setParent(3L);
//		menus4.setUrl("/test");
//		menus4.setStatus(1);
//		List menus3Sub = new ArrayList();
//		menus3Sub.add(menus4);
//		menus3.setSubMenus(menus3Sub);
//		all.add(menus2);
//		menusRepository.saveAll(all);
	}
}
