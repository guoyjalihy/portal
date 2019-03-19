package com.common.portal.service;

import com.common.portal.aop.OperationLog;
import com.common.portal.aop.OperationType;
import com.common.portal.dao.PrivilegeRepository;
import com.common.portal.entity.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 权限
 * @author: guoyanjun
 * @date: 2019/1/11 9:34
 */
@Service
@Transactional
public class PrivilegeService {
    @Autowired
    PrivilegeRepository privilegeRepository;

    @OperationLog(operationType = OperationType.ADD,content = "权限")
    public void saveAll(Long roleId,List<Long> menuIds){
        if (CollectionUtils.isEmpty(menuIds)){
            return;
        }
        List<Privilege> result = new ArrayList<>();
        menuIds.forEach(menuId -> {
            result.add(build(roleId,menuId));
        });
        privilegeRepository.deleteByRoleId(roleId);
        privilegeRepository.saveAll(result);
    }

    private Privilege build(Long roleId,Long menuId) {
        Privilege result = new Privilege();
        result.setMenuId(menuId);
        result.setRoleId(roleId);
        return result;
    }

    public List<Long> findMenuIdsByRoleId(Long roleId) {
            List<Privilege> privileges = privilegeRepository.findByRoleId(roleId);
            List<Long> menuIds = new ArrayList<>();
            privileges.forEach(privilege -> {
                menuIds.add(privilege.getMenuId());
            });
            return menuIds;
    }
}
