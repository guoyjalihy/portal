package com.common.portal.controller;

import com.common.portal.controller.vo.MenuVO;
import com.common.portal.controller.vo.RoleVO;
import com.common.portal.controller.vo.UserVO;
import com.common.portal.service.MenuService;
import com.common.portal.service.RoleService;
import com.common.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

	@Autowired
    MenuService menuService;
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;

	@GetMapping({"/","/home"})
	public String home(HttpSession httpSession) {
		UserVO userVO = (UserVO) httpSession.getAttribute("user");
		UserVO user = userService.findByNameAndPassword(userVO.getUsername(),userVO.getPassword());
		List<MenuVO> menus;
		if ("admin".equals(user.getUsername())){
			menus = menuService.findAllMenuTree();
		}else{
			menus = menuService.findMenuTreeByRoleId(user.getRoleId());
		}
		httpSession.setAttribute("user",user);
		if (!CollectionUtils.isEmpty(menus)){
			httpSession.setAttribute("menus",menus);
		}
		List<RoleVO> roles = roleService.findAll();
		httpSession.setAttribute("roles",roles);
		return "dashboard";
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

}
