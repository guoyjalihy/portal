package com.common.portal.controller;

import com.common.portal.controller.vo.RoleVO;
import com.common.portal.controller.vo.UserVO;
import com.common.portal.service.RoleService;
import com.common.portal.service.UserService;
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
@RequestMapping(value = "/role")
public class RoleController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	RoleService roleService;

	@GetMapping("/list")
	public String roleList(Model model) {
		List<RoleVO> roles =  roleService.findAll();
		model.addAttribute("roles",roles) ;
		return "role/roleList";
	}

	@PostMapping("/search")
	public String userSearch(Model model,RoleVO roleVO) {
		List<RoleVO> roles =  roleService.findByQuery(roleVO);
		model.addAttribute("roles",roles) ;
		model.addAttribute("roleVO",roleVO) ;
		return "role/roleList";
	}

	@GetMapping("/add")
	public String add(Model model) {
		return "role/roleEdit";
	}

	@PostMapping("/doEdit")
	public String doAdd(RoleVO roleVO) {
		roleService.saveOrUpdate(roleVO);
		return "redirect:/role/list";
	}

	@GetMapping("/edit")
	public String edit(RoleVO roleVO,Model model) {
		model.addAttribute("roleVO",roleVO);
		return "role/roleEdit";
	}

	@GetMapping("/del")
	public String del(Long id) {
		String msg = "执行成功！";
		try{
			roleService.delById(id);
		}catch (Exception e){
			logger.error("user  del fail.id:{}",id,e);
			msg = "执行失败！";
		}
		return "redirect:/role/list";
	}
}
