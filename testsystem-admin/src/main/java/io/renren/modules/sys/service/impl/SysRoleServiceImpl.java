/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.dao.SysRoleDao;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.service.*;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        List<SysRoleEntity> sysRoleEntities = sysRoleDao.selectList(new QueryWrapper<SysRoleEntity>()
                .like(StringUtils.isNotBlank(roleName), "role_name", roleName).orderByDesc("create_time")
                .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER)));

        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(deptId);
        Iterator<SysRoleEntity> iterator = sysRoleEntities.iterator();
        while (iterator.hasNext()) {
            SysRoleEntity sysRoleEntity = iterator.next();
            Long deptId1 = sysRoleEntity.getDeptId();

            if (!subDeptIdList.contains(deptId1) && deptId != deptId1) {
                iterator.remove();
                continue;
            }

            List<Long> parentDetpIdList = sysDeptService.queryParentDetpIdList(deptId1);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = parentDetpIdList.size() - 1; i >= 0; i--) {
                stringBuffer.append(sysDeptDao.selectById(parentDetpIdList.get(i)).getName());
                if (i != 0) {
                    stringBuffer.append("-");
                }
            }
            sysRoleEntity.setDeptName(stringBuffer.toString());
        }

        IPage<SysRoleEntity> page = new Page<>();
        long total = sysRoleEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysRoleEntity> subList = null;
        if (total < toIndex) {
            subList = sysRoleEntities.subList(index, (int) (total));
        } else {
            subList = sysRoleEntities.subList(index, toIndex);
        }
        page.setRecords(subList);
        page.setTotal(total);
        page.setPages(total / size);
        page.setCurrent(current);
        page.setSize(size);


        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleEntity role) {
        role.setCreateTime(new Date());
        this.save(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {
        this.updateById(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与部门关联
        sysRoleDeptService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }


}
