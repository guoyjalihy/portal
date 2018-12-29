package com.common.portal.controller;

import com.common.portal.controller.vo.RoleVO;
import com.common.portal.controller.vo.UserVO;
import com.common.portal.service.RoleService;
import com.common.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoleController {
	@Autowired
	RoleService roleService;

	@GetMapping("/role/list")
	public String roleList(Model model) {
		List<RoleVO> roles =  roleService.findAll();
		model.addAttribute("roles",roles) ;
		return "roleList";
	}

	@GetMapping("/role/add")
	public String add(Model model) {
		return "roleAdd";
	}

	@PostMapping("/role/doAdd")
	public String doAdd(RoleVO roleVO) {
		roleService.saveOrUpdate(roleVO);
		return "redirect:role/list";
	}

	@PostMapping("/role/edit")
	public String edit(RoleVO roleVO,Model model) {
		model.addAttribute("role",roleVO);
		return "roleEdit";
	}

}
