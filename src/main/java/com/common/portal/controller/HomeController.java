package com.common.portal.controller;

import com.common.portal.controller.vo.MenuVO;
import com.common.portal.controller.vo.RoleVO;
import com.common.portal.controller.vo.UserVO;
import com.common.portal.service.MenuService;
import com.common.portal.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

	@Autowired
    MenuService menuService;
	@Autowired
	RoleService roleService;

	@GetMapping({"/","/home"})
	public String home(HttpSession httpSession) {
		UserVO user = (UserVO) httpSession.getAttribute("user");
		List<MenuVO> menus = menuService.findMenuTree();
		httpSession.setAttribute("user",user);
		if (!CollectionUtils.isEmpty(menus)){
			httpSession.setAttribute("menus",menus.get(0).getSubMenus());
		}
		List<RoleVO> roles = roleService.findAll();
		httpSession.setAttribute("roles",roles);
		return "layout";
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

}
