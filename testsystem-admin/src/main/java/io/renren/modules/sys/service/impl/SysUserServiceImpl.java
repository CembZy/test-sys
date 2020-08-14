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
import io.renren.common.utils.*;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserDao sysUserService;
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        int type = Integer.parseInt((String) params.get("type"));
        Object deptId1 = params.get("deptId");
        List<Long> subDeptIdList = null;
        QueryWrapper<SysUserEntity> sysUserEntityQueryWrapper = new QueryWrapper<SysUserEntity>();
        if (deptId1 != null && !"".equals(deptId1)) {
            Long deptId = Long.parseLong((String) deptId1);
            subDeptIdList = sysDeptService.getSubDeptIdList(deptId);
            if (subDeptIdList != null) {
                int size = subDeptIdList.size();
                for (int i = 0; i < size; i++) {
                    sysUserEntityQueryWrapper.eq("dept_id", subDeptIdList.get(i)).or();
                }
                sysUserEntityQueryWrapper.eq("dept_id", deptId1);
                sysUserEntityQueryWrapper
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER));
            }

        } else {
            sysUserEntityQueryWrapper
                    .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER));
        }
        sysUserEntityQueryWrapper.eq("type", type).orderByDesc("create_time");

        List<SysUserEntity> sysUserEntities = sysUserDao.selectList(sysUserEntityQueryWrapper);

        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList2 = sysDeptService.getSubDeptIdList(deptId);
        Iterator<SysUserEntity> iterator = sysUserEntities.iterator();
        while (iterator.hasNext()) {
            SysUserEntity sysUserEntity = iterator.next();
            SysUserEntity sysUserEntity1 = sysUserService.selectById(Long.parseLong(sysUserEntity.getAdmin()));
            sysUserEntity.setAdmin(sysUserEntity1.getName());

            Long deptId2 = sysUserEntity.getDeptId();
            if (!subDeptIdList2.contains(deptId2) && deptId != deptId2) {
                iterator.remove();
                continue;
            }

            if (deptId1 != null && !"".equals(deptId1) && !"0".equals(deptId1) && deptId2 == 0) {
                iterator.remove();
                continue;
            }

//            if (type != sysUserEntity.getType()) {
//                iterator.remove();
//                continue;
//            }

            if (username != null && !"".equals(username) && !sysUserEntity.getUsername().equals(username)) {
                iterator.remove();
                continue;
            }

            List<Long> parentDetpIdList = sysDeptService.queryParentDetpIdList(deptId2);
            StringBuffer stringBuffer = new StringBuffer();
            if (parentDetpIdList != null && parentDetpIdList.size() > 0) {
                for (int i = parentDetpIdList.size() - 1; i >= 0; i--) {
                    stringBuffer.append(sysDeptDao.selectById(parentDetpIdList.get(i)).getName());
                    if (i != 0) {
                        stringBuffer.append("-");
                    }
                }
            }

            sysUserEntity.setDeptName(stringBuffer.toString());
        }


        IPage<SysUserEntity> page = new Page<>();
        long total = sysUserEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysUserEntity> subList = null;
        if (total < toIndex) {
            subList = sysUserEntities.subList(index, (int) (total));
        } else {
            subList = sysUserEntities.subList(index, toIndex);
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
    public void saveUser(SysUserEntity user) {
        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        user.setIp(IPUtils.getIpAddr(request));
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        this.save(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            SysUserEntity userEntity = this.getById(user.getUserId());
            user.setPassword(ShiroUtils.sha256(user.getPassword(), userEntity.getSalt()));
        }
        this.updateById(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }


    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

}
