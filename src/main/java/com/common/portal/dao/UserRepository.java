package com.common.portal.dao;

import com.common.portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    List<User> findByUsernameOrRoleId(String username, Long roleId);
}
