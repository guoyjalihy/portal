package com.common.portal.controller;

import com.common.portal.controller.vo.MenusVO;
import com.common.portal.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MenusController {
	@Autowired
	MenusService menusService;

	@PostMapping("/menus")
	public List<MenusVO> menus() {
		List<MenusVO> result = menusService.findAll();
		return result;
	}

}
