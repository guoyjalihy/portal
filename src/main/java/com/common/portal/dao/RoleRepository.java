package com.common.portal.dao;

import com.common.portal.entity.Role;
import com.common.portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
