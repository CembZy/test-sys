package io.renren.modules.news.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.news.entity.SysNewsEntity;
import io.renren.modules.news.service.SysNewsService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-26 11:34:21
 */
@RestController
@RequestMapping("sys/sysnews")
public class SysNewsController {
    @Autowired
    private SysNewsService sysNewsService;
    @Autowired
    private SysUserDao sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysnews:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysNewsService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{newId}")
    @RequiresPermissions("sys:sysnews:info")
    public R info(@PathVariable("newId") Long newId) {
        SysNewsEntity sysNews = sysNewsService.getById(newId);
        sysNews.setNum(sysNews.getNum() + 1);
        sysNewsService.updateById(sysNews);
        sysNews.setAdmin(sysUserService.selectById(Long.parseLong(sysNews.getAdmin())).getUsername());

        String[] split = sysNews.getDeptId().split(",");
        if (split != null) {
            List<Long> list = new ArrayList<>();
            for (String s : split) {
                list.add(Long.parseLong(s));
            }
            sysNews.setDeptIdList(list);
        }

        return R.ok().put("sysNews", sysNews);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysnews:save")
    public R save(@RequestBody SysNewsEntity sysNews) {

        sysNews.setAdmin(String.valueOf(ShiroUtils.getUserId()));
        sysNews.setNum(1L);
        List<Long> deptIdList = sysNews.getDeptIdList();
        StringBuffer stringBuffer = new StringBuffer();
        for (Long deptId : deptIdList) {
            stringBuffer.append(deptId + ",");
        }
        sysNews.setCreateTime(new Date());
        sysNews.setDeptId(stringBuffer.toString());
        sysNewsService.save(sysNews);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysnews:update")
    public R update(@RequestBody SysNewsEntity sysNews) {
        ValidatorUtils.validateEntity(sysNews);
        List<Long> deptIdList = sysNews.getDeptIdList();
        StringBuffer stringBuffer = new StringBuffer();
        for (Long deptId : deptIdList) {
            stringBuffer.append(deptId + ",");
        }
        sysNews.setAdmin(String.valueOf(ShiroUtils.getUserId()));
        sysNews.setCreateTime(new Date());
        sysNews.setDeptId(stringBuffer.toString());
        sysNewsService.updateById(sysNews);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysnews:delete")
    public R delete(@RequestBody Long[] newIds) {
        sysNewsService.removeByIds(Arrays.asList(newIds));

        return R.ok();
    }

}
