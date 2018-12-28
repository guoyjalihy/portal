package com.common.portal.service;

import com.common.portal.controller.vo.MenusVO;
import com.common.portal.controller.vo.NewsVO;
import com.common.portal.dao.MenusRepository;
import com.common.portal.dao.NewsRepository;
import com.common.portal.entity.Menus;
import com.common.portal.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class MenusService {

	@Autowired
	MenusRepository menusRepository;

	public List<MenusVO> findAll(){
		List<Menus> menus = menusRepository.findByLevel(0);
		if (CollectionUtils.isEmpty(menus)){
			return new ArrayList<>();
		}
		return buildVO(menus);
	}

	private List<MenusVO> buildVO(List<Menus> menus) {
		List<MenusVO> result = new ArrayList<>();
		menus.forEach(menu -> {
			MenusVO menusVO = new MenusVO();
			menusVO.setId(menu.getId());
			menusVO.setImage(menu.getImage());
			menusVO.setLevel(menu.getLevel());
			menusVO.setName(menu.getName());
			menusVO.setParent(menu.getParent());
			menusVO.setUrl(menu.getUrl());
			if (!CollectionUtils.isEmpty(menu.getSubMenus())){
				menusVO.setSubMenus(buildVO(menu.getSubMenus()));
			}
			result.add(menusVO);
		});
		return result;
	}

}
