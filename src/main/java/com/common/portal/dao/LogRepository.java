package com.common.portal.dao;

import com.common.portal.entity.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    int countByType(int type);

    List<Log> findByType(int type, Pageable pageable);
}
