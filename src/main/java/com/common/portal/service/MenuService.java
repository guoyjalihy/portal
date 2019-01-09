package com.common.portal.service;

import com.common.portal.controller.vo.MenuVO;
import com.common.portal.dao.MenuRepository;
import com.common.portal.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
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

	public MenuVO findById(Long menuId) {
		Menu menu = menuRepository.findById(menuId).get();
		if (menu == null){
			return null;
		}
		return buildVO(menu);
	}

	public List<MenuVO> findByQuery(MenuVO menuVO) {
		List<Menu> menus = menuRepository.findByIdOrNameOrLevel(menuVO.getId(),menuVO.getName(),menuVO.getLevel());
		if (CollectionUtils.isEmpty(menus)){
			return new ArrayList<>();
		}
		return buildVOs(menus);
	}

	private List<MenuVO> buildVOs(List<Menu> menus) {
		if (CollectionUtils.isEmpty(menus)){
			return new ArrayList<>();
		}
		List<MenuVO> result = new ArrayList<>();
		menus.forEach(menu -> {
			result.add(buildVO(menu));
		});
		return result;
	}

	public void delById(Long id) {
		menuRepository.deleteById(id);
	}

	public void deleteAllByParentOrId(Long id) {
		menuRepository.deleteAllByParentOrId(id,id);
	}
}
