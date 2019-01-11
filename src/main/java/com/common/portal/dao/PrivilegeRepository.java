package com.common.portal.dao;

import com.common.portal.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    void deleteByRoleId(Long roleId);

    List<Privilege> findByRoleId(Long roleId);
}
