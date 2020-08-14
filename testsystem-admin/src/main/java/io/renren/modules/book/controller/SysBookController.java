package io.renren.modules.book.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.book.entity.SysBookEntity;
import io.renren.modules.book.service.SysBookService;
import io.renren.modules.course.dao.SysCourseDao;
import io.renren.modules.course.entity.SysCourseEntity;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 电子书籍表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:23:53
 */
@RestController
@RequestMapping("sys/sysbook")
public class SysBookController {
    @Autowired
    private SysBookService sysBookService;
    @Autowired
    private SysUserDao sysUserService;
    @Autowired
    private SysCourseDao sysCourseDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysbook:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysBookService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{bookId}")
    @RequiresPermissions("sys:sysbook:info")
    public R info(@PathVariable("bookId") Long bookId) {
        SysBookEntity sysBook = sysBookService.getById(bookId);
        sysBook.setNum(sysBook.getNum() + 1);
        sysBookService.updateById(sysBook);
        sysBook.setAdmin(sysUserService.selectById(Long.parseLong(sysBook.getAdmin())).getUsername());
        SysCourseEntity sysCourseEntity = sysCourseDao.selectById(sysBook.getCourseId());
        if (sysCourseEntity != null) {
            sysBook.setCourseName(sysCourseEntity.getName());
        }
        return R.ok().put("sysBook", sysBook);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysbook:save")
    public R save(@RequestBody SysBookEntity sysBook) {
        sysBook.setAdmin(String.valueOf(ShiroUtils.getUserId()));
        sysBook.setNum(1L);
        sysBook.setCreateTime(new Date());
        sysBookService.save(sysBook);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysbook:update")
    public R update(@RequestBody SysBookEntity sysBook) {
        ValidatorUtils.validateEntity(sysBook);
        sysBook.setAdmin(String.valueOf(ShiroUtils.getUserId()));
        sysBookService.updateById(sysBook);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysbook:delete")
    public R delete(@RequestBody Long[] bookIds) {
        sysBookService.removeByIds(Arrays.asList(bookIds));

        return R.ok();
    }

}
