package com.common.portal.controller;

import com.common.portal.controller.vo.MenuVO;
import com.common.portal.controller.vo.PrivilegeVO;
import com.common.portal.entity.Privilege;
import com.common.portal.service.MenuService;
import com.common.portal.service.PrivilegeService;
import com.common.portal.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/json/privilege")
public class PrivilegeJSONController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	PrivilegeService privilegeService;
	@Autowired
	MenuService menuService;

	@RequestMapping("/save")
	public String save(@RequestBody PrivilegeVO ids) {
		logger.info(ids.toString());
		String menuIds = ids.getMenuIds();
		if (StringUtils.isEmpty(menuIds)){
			return "未选择权限";
		}
		if (menuIds.endsWith(",")){
			menuIds = menuIds.substring(0,menuIds.lastIndexOf(","));
		}
		String[] idsArr = menuIds.split(",");
		List<Long> menuIdList = new ArrayList<>();
		for (int i=0;i<idsArr.length;i++){
			menuIdList.add(Long.valueOf(idsArr[i]));
		}
		privilegeService.saveAll(ids.getRoleId(),menuIdList);
		return "保存成功！";
	}

	@RequestMapping(value = "/list/{roleId}")
	@ResponseBody
	public List<MenuVO> list(@PathVariable("roleId")Long roleId){
		List<Long> menuIds = privilegeService.findMenuIdsByRoleId(roleId);
		if (CollectionUtils.isEmpty(menuIds)){
			return new ArrayList<>();
		}
		return menuService.findByIds(menuIds);
	}
}
