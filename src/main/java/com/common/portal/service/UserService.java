package com.common.portal.service;

import com.common.portal.controller.vo.UserVO;
import com.common.portal.dao.UserRepository;
import com.common.portal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User findByNameAndPassword(UserVO userVO){
		return userRepository.findByUsernameAndPassword(userVO.getUsername(),userVO.getPassword());
	}

	public boolean insert(UserVO userVO){
		User result = new User();
		result.setUsername(userVO.getUsername());
		result.setPassword(userVO.getPassword());
		userRepository.saveAndFlush(result);
		return true;
	}

}
