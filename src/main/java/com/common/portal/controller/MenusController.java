package com.common.portal.controller;

import com.common.portal.controller.vo.MenuVO;
import com.common.portal.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MenusController {
	@Autowired
	MenuService menuService;

	@PostMapping("/menu/list")
	public List<MenuVO> menus() {
		List<MenuVO> result = menuService.findAll();
		return result;
	}


	@GetMapping("/menu/add")
	public String add(Model model) {
		return "menuAdd";
	}

	@PostMapping("/menu/doAdd")
	public String doAdd(MenuVO menuVO) {
		menuService.saveOrUpdate(menuVO);
		return "redirect:menu/list";
	}

	@PostMapping("/menu/edit")
	public String edit(MenuVO menuVO, Model model) {
		model.addAttribute("menu", menuVO);
		return "menuEdit";
	}

}
