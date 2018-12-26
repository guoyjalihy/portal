package com.common.portal.dao;

import com.common.portal.entity.News;
import com.common.portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    long countByTitleOrCategory(String title, String category);

    List<News> findByTitleOrCategory(String title, String category);

}
