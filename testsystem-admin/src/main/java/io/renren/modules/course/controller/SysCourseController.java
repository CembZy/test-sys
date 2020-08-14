package io.renren.modules.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.course.entity.SysCourseEntity;
import io.renren.modules.course.service.SysCourseService;
import io.renren.modules.subject.dao.SysSubjectDao;
import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.test.dao.SysTestDao;
import io.renren.modules.test.entity.SysTestEntity;
import io.renren.modules.title.dao.SysTitleDao;
import io.renren.modules.title.entity.SysTitleEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 科目表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:21:48
 */
@RestController
@RequestMapping("sys/syscourse")
public class SysCourseController extends AbstractController {
    @Autowired
    private SysCourseService sysCourseService;

    @Autowired
    private SysSubjectDao sysSubjectDao;
    @Autowired
    private SysTestDao sysTestDao;

    @Autowired
    private SysTitleDao sysTitleDao;

    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:syscourse:select")
    public R select() {
        List<SysCourseEntity> sysCourseEntityList = sysCourseService.queryList(new HashMap<String, Object>());


        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysCourseEntity root = new SysCourseEntity();
            root.setCourseId(0L);
            root.setName("一级科目");
            root.setParentId(-1L);
            root.setOpen(true);
            sysCourseEntityList.add(root);
        }

        return R.ok().put("courseList", sysCourseEntityList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:syscourse:list")
    public List<SysCourseEntity> list() {
        return sysCourseService.queryList(null);
    }


    /**
     * 上级科目Id(管理员则为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:syscourse:list")
    public R info() {
        long courseId = 0;
        if (getUserId() != Constant.SUPER_ADMIN) {
            List<SysCourseEntity> courseList = sysCourseService.queryList(new HashMap<String, Object>());
            Long parentId = null;
            for (SysCourseEntity sysCourseEntity : courseList) {
                if (parentId == null) {
                    parentId = sysCourseEntity.getParentId();
                    continue;
                }
                if (parentId > sysCourseEntity.getParentId().longValue()) {
                    parentId = sysCourseEntity.getParentId();
                }
            }
            courseId = parentId;
        }
        return R.ok().put("courseId", courseId);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{courseId}")
    @RequiresPermissions("sys:syscourse:info")
    public R info(@PathVariable("courseId") Long courseId) {
        SysCourseEntity sysCourse = sysCourseService.getById(courseId);

        return R.ok().put("sysCourse", sysCourse);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:syscourse:save")
    public R save(@RequestBody SysCourseEntity sysCourse) {
        sysCourse.setAdmin(String.valueOf(ShiroUtils.getUserId()));
        sysCourse.setCreateTime(new Date());
        sysCourseService.save(sysCourse);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:syscourse:update")
    public R update(@RequestBody SysCourseEntity sysCourse) {
        ValidatorUtils.validateEntity(sysCourse);
        sysCourseService.updateById(sysCourse);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:syscourse:delete")
    public R delete(@RequestBody Long[] courseIds) {
        if (courseIds != null && courseIds.length > 0) {
            for (Long id1 : courseIds) {
                //判断是否有子部门
                List<Long> courseList = sysCourseService.queryCourseIdList(id1);
                if (courseList != null && courseList.size() > 0) {
                    for (Long id : courseList) {
                        sysCourseService.removeById(id);
                        List<SysSubjectEntity> sysSubjectEntities = sysSubjectDao.selectList(new QueryWrapper<SysSubjectEntity>().eq("course_id", id));
                        if (sysSubjectEntities != null) {
                            sysSubjectDao.delete(new QueryWrapper<SysSubjectEntity>().eq("course_id", id));
                            for (SysSubjectEntity sysSubjectEntity : sysSubjectEntities) {
                                sysTestDao.delete(new QueryWrapper<SysTestEntity>().eq("subject_id", sysSubjectEntity.getSubjectId()));
                                sysTitleDao.delete(new QueryWrapper<SysTitleEntity>().eq("subject_id", sysSubjectEntity.getSubjectId()));
                            }
                        }
                    }
                }
                List<SysSubjectEntity> sysSubjectEntities = sysSubjectDao.selectList(new QueryWrapper<SysSubjectEntity>().eq("course_id", id1));
                if (sysSubjectEntities != null) {
                    sysSubjectDao.delete(new QueryWrapper<SysSubjectEntity>().eq("course_id", id1));
                    for (SysSubjectEntity sysSubjectEntity : sysSubjectEntities) {
                        sysTestDao.delete(new QueryWrapper<SysTestEntity>().eq("subject_id", sysSubjectEntity.getSubjectId()));
                        sysTitleDao.delete(new QueryWrapper<SysTitleEntity>().eq("subject_id", sysSubjectEntity.getSubjectId()));
                    }
                }
                sysCourseService.removeById(id1);

            }
        }

        return R.ok();
    }

}
