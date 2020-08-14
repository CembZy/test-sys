/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.*;
import io.renren.common.validator.Assert;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.dao.SysUserRoleDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    @ResponseBody
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    @ResponseBody
    public R info() {
        return R.ok().put("user", getUser());
    }

    @RequestMapping("/info2")
    @ResponseBody
    public R info2() {
        SysUserEntity sysUserEntity = sysUserDao.selectById(getUserId());
        if (sysUserEntity.getDeptId() == 0) {
            sysUserEntity.setDeptName("一级部门");
        } else {
            SysDeptEntity sysDeptEntity = sysDeptDao.selectById(sysUserEntity.getDeptId());
            sysUserEntity.setDeptName(sysDeptEntity != null ? sysDeptEntity.getName() : "");
        }
        return R.ok().put("user", sysUserEntity);
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    @ResponseBody
    public R password(String password, String newPassword) {
        Assert.isBlank(newPassword, "新密码不为能空");

        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.error("原密码不正确");
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    @ResponseBody
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);
        SysDeptEntity sysDeptEntity = sysDeptDao.selectById(user.getDeptId());
        if (sysDeptEntity != null) {
            user.setDeptName(sysDeptEntity.getName());
        }

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    @ResponseBody
    public R save(@RequestBody SysUserEntity user) {

        Long deptId = user.getDeptId();
        if (deptId == null) {
            return R.error("部门不能为空!");
        }
        user.setAdmin(String.valueOf(ShiroUtils.getUserId()));

        sysUserService.saveUser(user);

        return R.ok();
    }


    /**
     * 批量保存用户
     */
    @SysLog("批量保存用户")
    @RequestMapping("/saves")
    @RequiresPermissions("sys:user:saves")
    @ResponseBody
    public R saves(@RequestBody SysUserEntity user) {
        Long deptId = user.getDeptId();
        if (deptId == null) {
            return R.error("部门不能为空!");
        }

        long num = user.getNum();
        String username = user.getUsername();
        String name = user.getName();
        String password1 = user.getPassword();
        user.setAdmin(String.valueOf(ShiroUtils.getUserId()));
        user.setPassw(password1);
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String password = ShiroUtils.sha256(password1, salt);

        for (int i = 0; i < num; i++) {
            user.setUsername(username + (i + 1));
            user.setName(name + (i + 1));
            //获取request
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            user.setIp(IPUtils.getIpAddr(request));
            user.setCreateTime(new Date());
            user.setSalt(salt);
            user.setPassword(password);

            sysUserDao.insert(user);

            //保存用户与角色关系
            sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
        }

        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    @ResponseBody
    public R update(@RequestBody SysUserEntity user) {

        if (user.getPassword() != null && !"".equals(user.getPassword())) {
            user.setPassw(user.getPassword());
            //sha256加密
            String salt = RandomStringUtils.randomAlphanumeric(20);
            String password = ShiroUtils.sha256(user.getPassword(), salt);
            user.setSalt(salt);
            user.setPassword(password);
        }

        sysUserDao.updateById(user);

        Long userId = user.getUserId();
        sysUserRoleDao.delete(new QueryWrapper<SysUserRoleEntity>().eq("user_id", userId));

        List<Long> roleIdList = user.getRoleIdList();
        if (roleIdList != null && roleIdList.size() > 0) {
            sysUserRoleDao.delete(new QueryWrapper<SysUserRoleEntity>().eq("user_id", user));
            for (Long id : roleIdList) {
                SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
                sysUserRoleEntity.setRoleId(id);
                sysUserRoleEntity.setUserId(userId);

                sysUserRoleService.save(sysUserRoleEntity);
            }
        }


        return R.ok();
    }

    @SysLog("修改用户")
    @RequestMapping("/update2")
    @ResponseBody
    public R update2(@RequestBody SysUserEntity user) {

        if (user.getPassword() != null && !"".equals(user.getPassword())) {
            user.setPassw(user.getPassword());
            //sha256加密
            String salt = RandomStringUtils.randomAlphanumeric(20);
            String password = ShiroUtils.sha256(user.getPassword(), salt);
            user.setSalt(salt);
            user.setPassword(password);
        }
        sysUserDao.updateById(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    @ResponseBody
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }


    /**
     * 导入用户
     *
     * @return
     */
    @SysLog("导入用户")
    @RequestMapping("/import")
    @RequiresPermissions("sys:user:import")
    @ResponseBody
    public R importUsers(@RequestBody SysUserEntity user) {
        Long deptId = user.getDeptId();
        if (deptId == null) {
            return R.error("部门不能为空!");
        }

        String key = user.getPassword();
        List<SysUserEntity> cache = CacheUtil.getCache(key);

        for (SysUserEntity sysUserEntity : cache) {
            sysUserEntity.setType(user.getType());
            sysUserEntity.setDeptId(user.getDeptId());
            sysUserEntity.setDeptName(user.getDeptName());
            sysUserEntity.setStatus(user.getStatus());
            sysUserEntity.setRoleIdList(user.getRoleIdList());
            sysUserEntity.setAdmin(String.valueOf(ShiroUtils.getUserId()));
            sysUserEntity.setPassw(sysUserEntity.getPassword());
            sysUserService.saveUser(sysUserEntity);
        }
        CacheUtil.removeCache(key);
        return R.ok();
    }


    /**
     * 导出用户
     *
     * @return
     */
    @SysLog("导出用户")
    @RequestMapping("/outport")
    @RequiresPermissions("sys:user:outport")
    public void outportUsers(@RequestParam("userIds") Long[] userIds, HttpServletResponse response) throws Exception {

        List<SysUserEntity> sysUserEntities = new ArrayList<>();
        for (Long id : userIds) {
            SysUserEntity sysUserEntity = sysUserDao.selectById(id);
            List<Long> parentDetpIdList = sysDeptService.queryParentDetpIdList(sysUserEntity.getDeptId());
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
            sysUserEntities.add(sysUserEntity);
        }

        // 定义表的标题
        String title = "用户账号";
        //定义表的列名
        String[] rowsName = new String[]{"所属部门", "用户名", "密码", "姓名", "性别",
                "证件类型", "证件号码", "手机号", "邮箱", "创建时间"};
        //定义表的内容
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        int length = 10;
        for (int i = 0; i < sysUserEntities.size(); i++) {
            SysUserEntity per = sysUserEntities.get(i);
            objs = new Object[length];
            objs[0] = per.getDeptName() == null ? "" : per.getDeptName();
            objs[1] = per.getUsername() == null ? "" : per.getUsername();
            objs[2] = per.getPassw() == null ? "" : per.getPassw();
            objs[3] = per.getName() == null ? "" : per.getName();
            objs[4] = per.getSex() == null ? "" : per.getSex();
            objs[5] = per.getIdtype() == null ? "" : per.getIdtype();
            objs[6] = per.getIdcard() == null ? "" : per.getIdcard();
            objs[7] = per.getMobile() == null ? "" : per.getMobile();
            objs[8] = per.getEmail() == null ? "" : per.getEmail();
//            if (per.getStatus() != null) {
//                objs[9] = per.getStatus() == 0 ? "禁用" : "正常";
//            } else {
//                objs[9] = "禁用";
//            }
            objs[9] = per.getCreateTime() == null ? "" : per.getCreateTime();
            dataList.add(objs);
        }

        // 创建ExportExcel对象
        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        // 输出Excel文件
        OutputStream output = null;

        try {
            output = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户账号表.xls", "utf-8"));
            response.setContentType("application/vnd.ms-excel");
            ex.export(output);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
