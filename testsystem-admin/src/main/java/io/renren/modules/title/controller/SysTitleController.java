package io.renren.modules.title.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.course.dao.SysCourseDao;
import io.renren.modules.course.entity.SysCourseEntity;
import io.renren.modules.subject.dao.SysSubjectDao;
import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.test.dao.SysTestDao;
import io.renren.modules.test.entity.SysTestEntity;
import io.renren.modules.title.entity.SysTitleEntity;
import io.renren.modules.title.service.SysTitleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 试题表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-25 11:03:33
 */
@RestController
@RequestMapping("sys/systitle")
public class SysTitleController extends AbstractController {
    @Autowired
    private SysTitleService sysTitleService;

    @Autowired
    private SysTestDao sysTestDao;

    @Autowired
    private SysSubjectDao sysSubjectDao;

    @Autowired
    private SysCourseDao sysCourseDao;

    @Autowired
    private SysDeptDao sysDeptDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:systitle:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysTitleService.queryPage(params);

        return R.ok().put("page", page);
    }


    @RequestMapping("/type/{type}/{deptId}")
    @RequiresPermissions("sys:systitle:type")
    public R type(@PathVariable("type") String type, @PathVariable("deptId") Long deptId) {
        List<SysTestEntity> sysTestEntities = sysTestDao.selectList(new QueryWrapper<SysTestEntity>().eq("type", type)
                .eq("dept_id", deptId));

        IdentityHashMap<Long, String> map = new IdentityHashMap<>();
        if (sysTestEntities != null) {
            Iterator<SysTestEntity> iterator = sysTestEntities.iterator();
            int i = 1;
            while (iterator.hasNext()) {
                SysTestEntity next = iterator.next();
//                Long deptId1 = next.getDeptId();
//                if (subDeptIdList != null) {
//                    if (!subDeptIdList.contains(deptId1) && deptId1 != deptId) {
//                        iterator.remove();
//                    }
//                } else {
//                    if (deptId1 != deptId) {
//                        iterator.remove();
//                    }
//                }
                if (isValue(map, next.getName())) {
                    map.put(next.getTestId(), next.getName() + " (同名" + (i++) + ")");
                } else {
                    map.put(next.getTestId(), next.getName());
                }
            }
        }
        return R.ok().put("sysTests", map);
    }

    private boolean isValue(Map<Long, String> map, String value) {
        if (map.size() == 0) {
            return false;
        }
        for (Map.Entry<Long, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }


    @RequestMapping("/subject/{topicId}/{courseId}")
    @RequiresPermissions("sys:systest:subject")
    public R getSubject(@PathVariable("topicId") Long topicId, @PathVariable("courseId") Long courseId) {
        return R.ok().put("subjects", sysTitleService.getSubjects(topicId, courseId, getUser()));
    }


    @RequestMapping("/topic/{id}")
    @RequiresPermissions("sys:systest:topic")
    public R getTopicName(@PathVariable("id") Long id) {
        return R.ok().put("topics", sysTitleService.getTopic(id, getUser()));
    }


    @RequestMapping("/courses")
    @RequiresPermissions("sys:systest:courses")
    public R courses() {
        return R.ok().put("courses", sysTitleService.getCpurse(getUser()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{titleId}")
    @RequiresPermissions("sys:systitle:info")
    public R info(@PathVariable("titleId") Long titleId) {
        SysTitleEntity sysTitle = sysTitleService.getById(titleId);

        Long testId = sysTitle.getTestId();
        SysTestEntity sysTestEntity = sysTestDao.selectById(testId);
        if (sysTestEntity != null) {
            String type = sysTestEntity.getType();
            sysTitle.setType(type);
            String typet = sysTestEntity.getTypet();
            List<SysTestEntity> sysTestEntities = sysTestDao.selectList(new QueryWrapper<SysTestEntity>().eq("type", type).eq("typet", typet));
            if (sysTestEntities != null) {
                IdentityHashMap<Long, String> map = new IdentityHashMap<>();
                int i = 1;
                for (SysTestEntity sysTestEntity1 : sysTestEntities) {
                    if (isValue(map, sysTestEntity1.getName())) {
                        map.put(sysTestEntity1.getTestId(), sysTestEntity1.getName() + " (同名" + (i++) + ")");
                    } else {
                        map.put(sysTestEntity1.getTestId(), sysTestEntity1.getName());
                    }
                }
                sysTitle.setTests(map);
            }
            String[] split = sysTestEntity.getDeptId().split(",");
            if (split != null) {
                long id = Long.parseLong(split[0]);
                SysDeptEntity sysDeptEntity = sysDeptDao.selectById(id);
                sysTitle.setDeptId(id);
                sysTitle.setDeptName(sysDeptEntity != null ? sysDeptEntity.getName() : "所属部门");
            }
        }
        List<IdentityHashMap<String, Long>> cpurse = sysTitleService.getCpurse(getUser());
        sysTitle.setCourses(cpurse);


        SysSubjectEntity sysSubjectEntity = sysSubjectDao.selectById(sysTitle.getSubjectId());
        Long courseId = sysSubjectEntity.getCourseId();

        SysCourseEntity sysCourseEntity = sysCourseDao.selectById(courseId);

        sysTitle.setCourseName(sysCourseEntity != null ? sysCourseEntity.getName() : "一级科目");
        sysTitle.setCourseId(courseId);
        sysTitle.setTopics(sysTitleService.getTopic(courseId, getUser()));

        sysTitle.setSubjects(sysTitleService.getSubjects(sysSubjectEntity.getTopicId(), courseId, getUser()));

        return R.ok().put("sysTitle", sysTitle);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:systitle:save")
    public R save(@RequestBody SysTitleEntity sysTitle) {

        sysTitle.setAdmin(String.valueOf(ShiroUtils.getUserId()));
        sysTitle.setCreateTime(new Date());

        sysTitleService.save(sysTitle);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:systitle:update")
    public R update(@RequestBody SysTitleEntity sysTitle) {
        ValidatorUtils.validateEntity(sysTitle);
        sysTitleService.updateById(sysTitle);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:systitle:delete")
    public R delete(@RequestBody Long[] titleIds) {
        for (int i = 0; i < titleIds.length; i++) {
            SysTestEntity sysTestEntity = sysTestDao.selectById(titleIds[i]);
            sysTestDao.deleteById(sysTestEntity.getTestId());
        }
        sysTitleService.removeByIds(Arrays.asList(titleIds));
        return R.ok();
    }

}
