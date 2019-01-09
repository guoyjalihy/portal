package com.common.portal.controller;

import com.common.portal.controller.vo.UserVO;
import com.common.portal.service.RoleService;
import com.common.portal.service.UserService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@GetMapping("/list")
	public String userList(Model model) {
		List<UserVO> users =  userService.findAll();
		model.addAttribute("users",users) ;
		return "user/userList";
	}

	@PostMapping("/search")
	public String userSearch(Model model,UserVO userVO) {
		List<UserVO> users =  userService.findByQuery(userVO);
        model.addAttribute("users",users) ;
        model.addAttribute("userVO",userVO) ;
		return "user/userList";
	}

	@GetMapping("/add")
	public String add(Model model) {
		return "user/userEdit";
	}

	@PostMapping("/doEdit")
	public String doAdd(UserVO userVO) {
		userService.saveOrUpdate(userVO);
		return "redirect:/user/list";
	}

	@GetMapping("/edit")
	public String edit(UserVO userVO,Model model) {
		model.addAttribute("userVO",userService.findById(userVO.getId()));
		return "user/userEdit";
	}

	@GetMapping("/del")
	public String del(Long id) {
		String msg = "执行成功！";
		try{
			userService.delById(id);
		}catch (Exception e){
			logger.error("user  del fail.id:{}",id,e);
			msg = "执行失败！";
		}
		return "redirect:/user/list";
	}

}
