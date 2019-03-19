package com.common.portal.service;

import com.common.portal.aop.OperationLog;
import com.common.portal.aop.OperationType;
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
	@OperationLog(operationType = OperationType.ADD,content = "角色")
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
			result.add(buildVO(role));
		});
		return result;
	}

	private RoleVO buildVO(Role role) {
		RoleVO roleVO = new RoleVO();
		roleVO.setId(role.getId());
		roleVO.setName(role.getName());
		return roleVO;
	}

	public List<RoleVO> findByQuery(RoleVO roleVO) {
		if (roleVO.getId() == 0){
			return buildVOs(roleRepository.findAll());
		}
		Role role = roleRepository.findById(roleVO.getId()).get();
		List<RoleVO> result = new ArrayList<>();
		result.add(buildVO(role));
		return result;
	}

	@OperationLog(operationType = OperationType.DELETE,content = "角色")
	public void delById(Long id) {
		roleRepository.deleteById(id);
	}
}
