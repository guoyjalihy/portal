package com.common.portal.controller;

import com.common.portal.controller.vo.UserVO;
import com.common.portal.entity.User;
import com.common.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	/**
	 * 登录跳转
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/login")
	public String loginGet(Model model) {
		return "login";
	}

	/**
	 * 登录
	 * 
	 * @param userVO
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@PostMapping("/login")
	public String loginPost(UserVO userVO, Model model, HttpSession httpSession) {
		UserVO user = userService.findByNameAndPassword(userVO.getUsername(),userVO.getPassword());
		if (user != null) {
			httpSession.setAttribute("user", userVO);
			return "redirect:home";
		} else {
			model.addAttribute("error", "用户名或密码错误，请重新登录！");
			return "login";
		}
	}

	/**
	 * 注册
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/register")
	public String registerGet(Model model) {
		return "register";
	}
	/**
	 * 注册
	 *
	 * @param model
	 * @return
	 */
	@PostMapping("/register")
	public String register(UserVO userVO,Model model) {
		userService.saveOrUpdate(userVO);
		model.addAttribute("msg", "注册成功，请重新登录！");
		return "register";
	}

	/**
	 * 登出
	 * @param httpSession
	 * @return
	 */
	@GetMapping("logout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute("user");
		httpSession.removeAttribute("menus");
		httpSession.removeAttribute("roles");
		httpSession.removeAttribute("currMenu");
		return "login";
	}
}
