package com.common.portal.service;

import com.common.portal.controller.vo.RoleVO;
import com.common.portal.controller.vo.UserVO;
import com.common.portal.dao.RoleRepository;
import com.common.portal.dao.UserRepository;
import com.common.portal.entity.Role;
import com.common.portal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	public boolean saveOrUpdate(RoleVO roleVO){
		Role result = build(roleVO);
		roleRepository.saveAndFlush(result);
		return true;
	}

	private Role build(RoleVO roleVO) {
		Role result  = new Role();
		result.setId(roleVO.getId());
		result.setName(roleVO.getName());
		return result;
	}


	public List<RoleVO> findAll() {
		List<Role> roles = roleRepository.findAll();
		return buildVOs(roles);
	}

	private List<RoleVO> buildVOs(List<Role> roles) {
		List<RoleVO> result = new ArrayList<>();
		if (CollectionUtils.isEmpty(roles)){
			return result;
		}
		roles.forEach(role -> {
			RoleVO roleVO = new RoleVO();
			roleVO.setId(role.getId());
			roleVO.setName(role.getName());
			result.add(roleVO);
		});
		return result;
	}
}
