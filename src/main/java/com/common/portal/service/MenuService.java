package com.common.portal.service;

import com.common.portal.controller.vo.MenuVO;
import com.common.portal.dao.MenuRepository;
import com.common.portal.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class MenuService {

	@Autowired
	MenuRepository menuRepository;

	public List<MenuVO> findMenuTree(){
		List<Menu> menus = menuRepository.findByLevel(0);
		if (CollectionUtils.isEmpty(menus)){
			return new ArrayList<>();
		}
		return buildVOsToTree(menus);
	}

	private List<MenuVO> buildVOsToTree(List<Menu> menus) {
		List<MenuVO> result = new ArrayList<>();
		menus.forEach(menu -> {
			MenuVO menuVO = buildVO(menu);
			if (!CollectionUtils.isEmpty(menu.getSubMenus())){
				menuVO.setSubMenus(buildVOsToTree(menu.getSubMenus()));
			}
			result.add(menuVO);
		});
		return result;
	}

	private MenuVO buildVO(Menu menu) {
		MenuVO menuVO = new MenuVO();
		menuVO.setId(menu.getId());
		menuVO.setImage(menu.getImage());
		menuVO.setLevel(menu.getLevel());
		menuVO.setName(menu.getName());
		menuVO.setParent(menu.getParent());
		menuVO.setUrl(menu.getUrl());
		return menuVO;
	}

	public void saveOrUpdate(MenuVO menuVO) {
		menuRepository.saveAndFlush(build(menuVO));
	}

	private Menu build(MenuVO menuVO) {
		Menu menu = new Menu();
		menu.setId(menuVO.getId());
		menu.setImage(menuVO.getImage());
		menu.setLevel(menuVO.getLevel());
		menu.setName(menuVO.getName());
		menu.setParent(menuVO.getParent());
		menu.setStatus(1);
		menu.setUrl(menuVO.getUrl());
		return menu;
	}

	public List<MenuVO> findAll() {
		List<Menu> menus = menuRepository.findAll();
		List<MenuVO> result = new ArrayList<>();
		menus.forEach(menu -> {
			result.add(buildVO(menu));
		});
		return result;
	}
}
