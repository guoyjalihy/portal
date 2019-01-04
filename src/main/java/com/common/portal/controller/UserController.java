package com.common.portal.controller;

import com.common.portal.controller.vo.RoleVO;
import com.common.portal.controller.vo.UserVO;
import com.common.portal.service.RoleService;
import com.common.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Index;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@GetMapping("/list")
	public String userList(Model model) {
		List<UserVO> users =  userService.findAll();
		List<RoleVO> roles = roleService.findAll();
		model.addAttribute("users",users) ;
		model.addAttribute("roles",roles) ;
		return "user/userList";
	}

	@PostMapping("/search")
	public String userSearch(Model model,UserVO userVO) {
		List<UserVO> users =  userService.findByQuery(userVO);
		model.addAttribute("users",users) ;
		return "user/userList";
	}

	@GetMapping("/add")
	public String add(Model model) {
		List<RoleVO> roles = roleService.findAll();
		model.addAttribute("roles",roles) ;
		return "userEdit";
	}

	@PostMapping("/doEdit")
	public String doAdd(UserVO userVO) {
		userService.saveOrUpdate(userVO);
		return "redirect:/list";
	}

	@GetMapping("/edit")
	public String edit(UserVO userVO,Model model) {
		model.addAttribute("user",userService.findById(userVO.getId()));
		List<RoleVO> roles = roleService.findAll();
		model.addAttribute("roles",roles) ;
		return "userEdit";
	}
	@GetMapping("/del")
	public String del(@PathVariable Long id) {
		userService.delById(id);
		return "redirect:user/list";
	}

}
