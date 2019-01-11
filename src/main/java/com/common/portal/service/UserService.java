package com.common.portal.service;

import com.common.portal.controller.vo.UserVO;
import com.common.portal.dao.RoleRepository;
import com.common.portal.dao.UserRepository;
import com.common.portal.entity.Role;
import com.common.portal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	public UserVO findByNameAndPassword(String username,String password){
		User user = userRepository.findByUsernameAndPassword(username,password);
		return buildUserVO(user);
	}

	public List<UserVO> findAll() {
		List<User> users = userRepository.findAll();
		List<Role> roles = roleRepository.findAll();
		return buildVOs(users,roles);
	}

	private List<UserVO> buildVOs(List<User> users,List<Role> roles){
		List<UserVO> result = new ArrayList<>();
		if (CollectionUtils.isEmpty(users)){
			return result;
		}
		Map<Long,String> roleMap = new HashMap<>();
		roles.forEach(role -> {
			roleMap.put(role.getId(),role.getName());
		});

		users.forEach(user -> {
			UserVO userVO = buildUserVO(user);
			userVO.setRoleName(roleMap.get(user.getRoleId()));
			result.add(userVO);
		});
		return result;
	}

	private UserVO buildUserVO(User user) {
		if (user == null ){
			return null;
		}
		UserVO userVO = new UserVO();
		userVO.setId(user.getId());
		userVO.setUsername(user.getUsername());
		userVO.setPassword(user.getPassword());
		userVO.setRoleId(user.getRoleId());
		return userVO;
	}

	public void saveOrUpdate(UserVO userVO) {
		userRepository.saveAndFlush(build(userVO));
	}

	private User build(UserVO userVO) {
		User result = new User();
		result.setPassword(userVO.getPassword());
		result.setUsername(userVO.getUsername());
		result.setId(userVO.getId());
		result.setRoleId(userVO.getRoleId());
		return result;
	}


	public void delById(Long id) {
		userRepository.deleteById(id);
	}

	public List<UserVO> findByQuery(UserVO userVO) {
		List<User> users = userRepository.findByUsernameOrRoleId(userVO.getUsername(),userVO.getRoleId());
		List<Role> roles = roleRepository.findAll();
		return buildVOs(users,roles);
	}

	public UserVO findById(Long id) {
		User user = userRepository.findById(id).get();
		if(user == null){
			return null;
		}
		return buildUserVO(user);
	}
}
