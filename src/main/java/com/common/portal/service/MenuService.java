package com.common.portal.service;

import com.common.portal.controller.vo.MenuVO;
import com.common.portal.dao.MenuRepository;
import com.common.portal.dao.PrivilegeRepository;
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
	@Autowired
	PrivilegeService privilegeService;

	public List<MenuVO> findAllMenuTree(){
		List<Menu> menus = menuRepository.findAll();
		if (CollectionUtils.isEmpty(menus)){
			return new ArrayList<>();
		}
		return buildVOsToTree(menus);
	}

	private List<MenuVO> buildVOsToTree(List<Menu> menus) {
		Map<Long,MenuVO> map = new HashMap<>();
		menus.forEach(menu -> {
			if (menu.getLevel() == 1){
				MenuVO menuVO = buildVO(menu);
				map.put(menu.getId(),menuVO);
			}
		});
		menus.forEach(menu -> {
			if (menu.getLevel() == 2) {
				map.get(menu.getParent()).getSubMenus().add(buildVO(menu));
			}
		});
		return new ArrayList<>(map.values());
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


	public void deleteAllByParentOrId(Long id) {
		menuRepository.deleteAllByParentOrId(id,id);
	}

	public List<MenuVO> findByIds(List<Long> menuIds) {
		List<Menu> menus = menuRepository.findAllById(menuIds);
		return buildVOs(menus);
	}

	public List<MenuVO> findMenuTreeByRoleId(Long roleId) {
		List<Long> menuIds = privilegeService.findMenuIdsByRoleId(roleId);
		return buildVOsToTree(menuRepository.findAllById(menuIds));
	}
}
