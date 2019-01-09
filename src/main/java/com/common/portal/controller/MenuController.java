package com.common.portal.controller;

import com.common.portal.controller.vo.MenuVO;
import com.common.portal.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	MenuService menuService;

	@GetMapping("/list")
	public String list(Model model) {
		List<MenuVO> menus = menuService.findAll();
		model.addAttribute("menus",menus);
		return "menu/menuList";
	}

	@PostMapping("/search")
	public String search(Model model,MenuVO menuVO) {
		List<MenuVO> menus = menuService.findByQuery(menuVO);
		model.addAttribute("menus",menus);
		model.addAttribute("menuVO",menuVO);
		return "menu/menuList";
	}

	@GetMapping({"/add","/edit"})
	public String edit(Long id,Model model) {
		if (id != null){
			MenuVO menuVO = menuService.findById(id);
			model.addAttribute("menuVO",menuVO);
		}
		model.addAttribute("menus",menuService.findMenuTree().get(0).getSubMenus());
		return "menu/menuEdit";
	}

	@PostMapping("/doEdit")
	public String doAdd(MenuVO menuVO) {
		menuService.saveOrUpdate(menuVO);
		return "redirect:/menu/list";
	}

	@GetMapping("/del")
	public String del(Long id) {
		String msg = "执行成功！";
		try{
			menuService.deleteAllByParentOrId(id);
		}catch (Exception e){
			logger.error("menu  del fail.id:{}",id,e);
			msg = "执行失败！";
		}
		return "redirect:/menu/list";
	}
}
