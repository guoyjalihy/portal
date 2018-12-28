package com.common.portal.controller;

import com.common.portal.controller.vo.MenusVO;
import com.common.portal.controller.vo.UserVO;
import com.common.portal.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

	@Autowired
	MenusService menusService;

	@GetMapping({"/","/home"})
	public String home(HttpSession httpSession,Model model) {
		UserVO user = (UserVO) httpSession.getAttribute("user");
		List<MenusVO> menus = menusService.findAll();
		model.addAttribute("user",user);
		if (!CollectionUtils.isEmpty(menus)){
			model.addAttribute("menus",menus.get(0).getSubMenus());
		}
		return "home";
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboard(ModelAndView modelAndView) {
		modelAndView.setViewName("dashboard");
		return modelAndView;
	}

}
