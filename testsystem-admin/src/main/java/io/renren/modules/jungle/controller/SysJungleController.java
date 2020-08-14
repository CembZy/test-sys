package io.renren.modules.jungle.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.jungle.entity.SysJungleEntity;
import io.renren.modules.jungle.service.SysJungleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-20 11:54:53
 */
@RestController
@RequestMapping("sys/sysjungle")
public class SysJungleController {
    @Autowired
    private SysJungleService sysJungleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysjungle:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysJungleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{jungleId}")
    @RequiresPermissions("sys:sysjungle:info")
    public R info(@PathVariable("jungleId") Long jungleId){
        SysJungleEntity sysJungle = sysJungleService.getById(jungleId);

        return R.ok().put("sysJungle", sysJungle);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysjungle:save")
    public R save(@RequestBody SysJungleEntity sysJungle){
        sysJungleService.save(sysJungle);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysjungle:update")
    public R update(@RequestBody SysJungleEntity sysJungle){
        ValidatorUtils.validateEntity(sysJungle);
        sysJungleService.updateById(sysJungle);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysjungle:delete")
    public R delete(@RequestBody Long[] jungleIds){
        sysJungleService.removeByIds(Arrays.asList(jungleIds));

        return R.ok();
    }

}
