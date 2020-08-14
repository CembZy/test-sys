/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.annotation.DataFilter;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {
    @Autowired
    private SysDeptDao sysDeptDao;

    @Override
    @DataFilter(subDept = true, user = false, tableAlias = "t1")
    public List<SysDeptEntity> queryList(Map<String, Object> params) {
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        Long userId = ShiroUtils.getUserId();
        List<Long> subDeptIdList = getSubDeptIdList(deptId);
        List<SysDeptEntity> sysDeptEntities = new ArrayList<>();
        if (subDeptIdList != null) {
            params.put("userId", userId);
            sysDeptEntities = baseMapper.queryList(params);
            Iterator<SysDeptEntity> iterator = sysDeptEntities.iterator();
            while (iterator.hasNext()) {
                SysDeptEntity next = iterator.next();
                if (!userId.equals(next.getUserId()) && !subDeptIdList.contains(next.getDeptId())) {
                    iterator.remove();
                    continue;
                }
            }


        }
        SysDeptEntity e = sysDeptDao.selectById(deptId);
        if (e != null) {
            sysDeptEntities.add(e);
        }

        return sysDeptEntities;
    }

    @Override
    public List<Long> queryDetpIdList(Long parentId) {
        return baseMapper.queryDetpIdList(parentId);
    }

    @Override
    public List<Long> getSubDeptIdList(Long deptId) {
        //部门及子部门ID列表
        List<Long> deptIdList = new ArrayList<>();

        //获取子部门ID
        List<Long> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        return deptIdList;
    }

    /**
     * 递归
     */
    private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
        for (Long deptId : subIdList) {
            List<Long> list = queryDetpIdList(deptId);
            if (list.size() > 0) {
                getDeptTreeList(list, deptIdList);
            }

            deptIdList.add(deptId);
        }
    }

    /**
     * 查询父部门ID列表
     *
     * @param chiledId 子部门ID
     */
    @Override
    public List<Long> queryParentDetpIdList(Long chiledId) {

        ArrayList<Long> parentIds = new ArrayList<>();
        getDeptTreeList(chiledId, parentIds);
        return parentIds;
    }

    private void getDeptTreeList(Long chiledId, List<Long> parentIds) {
        SysDeptEntity sysDeptEntity = sysDeptDao.selectById(chiledId);
        if (sysDeptEntity != null) {
            Long parentId = sysDeptEntity.getParentId();
            parentIds.add(chiledId);
            if (sysDeptEntity.getParentId() != 0) {
                getDeptTreeList(parentId, parentIds);
            }
        }
    }

}
