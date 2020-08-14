package io.renren.modules.testsubs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.testsubs.entity.SysTestsubsEntity;
import io.renren.modules.testsubs.service.SysTestsubsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 试卷随机试题表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-31 11:52:00
 */
@RestController
@RequestMapping("sys/systestsubs")
public class SysTestsubsController {
    @Autowired
    private SysTestsubsService sysTestsubsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:systestsubs:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysTestsubsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{testsubsId}")
    @RequiresPermissions("sys:systestsubs:info")
    public R info(@PathVariable("testsubsId") Long testsubsId){
        SysTestsubsEntity sysTestsubs = sysTestsubsService.getById(testsubsId);

        return R.ok().put("sysTestsubs", sysTestsubs);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:systestsubs:save")
    public R save(@RequestBody SysTestsubsEntity sysTestsubs){
        sysTestsubsService.save(sysTestsubs);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:systestsubs:update")
    public R update(@RequestBody SysTestsubsEntity sysTestsubs){
        ValidatorUtils.validateEntity(sysTestsubs);
        sysTestsubsService.updateById(sysTestsubs);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:systestsubs:delete")
    public R delete(@RequestBody Long[] testsubsIds){
        sysTestsubsService.removeByIds(Arrays.asList(testsubsIds));

        return R.ok();
    }

}
