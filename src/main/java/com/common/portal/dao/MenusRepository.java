package com.common.portal.dao;

import com.common.portal.entity.Menus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenusRepository extends JpaRepository<Menus, Long> {
    List<Menus> findByLevel(int level);
}
