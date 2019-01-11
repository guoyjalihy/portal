package com.common.portal.controller;

import com.common.portal.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/privilege")
public class PrivilegeController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	RoleService roleService;

	@GetMapping("/view")
	public String view(Model model) {
		return "privilege/privilege";
	}


}
