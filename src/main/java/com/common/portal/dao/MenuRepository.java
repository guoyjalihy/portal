package com.common.portal.dao;

import com.common.portal.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByLevel(int level);

    List<Menu> findByIdOrNameOrLevel(Long id, String name, int level);

    void deleteAllByParentOrId(Long parent,Long id);
}
