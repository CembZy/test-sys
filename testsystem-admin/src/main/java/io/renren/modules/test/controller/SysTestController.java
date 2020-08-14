package io.renren.modules.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.*;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.course.service.SysCourseService;
import io.renren.modules.jungle.dao.SysJungleDao;
import io.renren.modules.jungle.entity.SysJungleEntity;
import io.renren.modules.subject.dao.SysSubjectDao;
import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.test.dao.SysTestDao;
import io.renren.modules.test.entity.SysTestEntity;
import io.renren.modules.test.service.SysTestService;
import io.renren.modules.testsubs.dao.SysTestsubsDao;
import io.renren.modules.testsubs.entity.SysTestsubsEntity;
import io.renren.modules.testsubs.service.SysTestsubsService;
import io.renren.modules.title.dao.SysTitleDao;
import io.renren.modules.title.entity.SysTitleEntity;
import io.renren.modules.title.service.SysTitleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 考试表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-25 11:00:46
 */
@RestController
@RequestMapping("sys/systest")
public class SysTestController extends AbstractController {
    @Autowired
    private SysTestService sysTestService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTitleService sysTitleService;

    @Autowired
    private SysSubjectDao sysSubjectDao;

    @Autowired
    private SysTitleDao sysTitleDao;

    @Autowired
    private SysTestDao sysTestDao;

    @Autowired
    private SysJungleDao sysJungleDao;

    @Autowired
    private SysDeptDao sysDeptDao;

    @Autowired
    private SysTestsubsService sysTestsubsService;

    @Autowired
    private SysTestsubsDao sysTestsubsDao;

    @Autowired
    private SysCourseService sysCourseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:systest:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysTestService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list2")
    @RequiresPermissions("sys:systest:list2")
    public R list2(@RequestParam Map<String, Object> params) {
        PageUtils page = sysTestService.queryPage2(params);

        return R.ok().put("page", page);
    }


    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:systest:select")
    public R select() {
        List<SysUserEntity> list = sysUserService.list(new QueryWrapper<SysUserEntity>().eq("type", 1).eq("status", 1));
        Long deptId = getUser().getDeptId();
        List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(deptId);
        if (list != null) {
            Iterator<SysUserEntity> iterator = list.iterator();

            while (iterator.hasNext()) {
                SysUserEntity next = iterator.next();
                Long deptId1 = next.getDeptId();

                if (subDeptIdList != null) {
                    if (!subDeptIdList.contains(deptId1) && deptId1 != deptId) {
                        iterator.remove();
                    }
                } else {
                    if (deptId1 != deptId) {
                        iterator.remove();
                    }
                }
                if (next.getName() == null || "".equals(next.getName())) {
                    next.setName("无昵称");
                }
            }
        }
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{testId}")
    @RequiresPermissions("sys:systest:info")
    public R info(@PathVariable("testId") Long testId) {
        SysTestEntity sysTest = sysTestService.getById(testId);
        String[] split = (sysTest.getTestType() != null && !"".equals(sysTest.getTestType())) ? sysTest.getTestType().split(",") : null;
        if (split != null) {
            List<Long> list = new ArrayList<>();
            for (String str : split) {
                list.add(Long.parseLong(str));
            }
            sysTest.setTestTypeList(list);
        }

        String[] strings = (sysTest.getAdminId() != null && !"".equals(sysTest.getAdminId())) ? sysTest.getAdminId().split(",") : null;
        if (strings != null) {
            List<Long> list = new ArrayList<>();
            for (String str : strings) {
                list.add(Long.parseLong(str));
            }
            sysTest.setAdminsList(list);
        }

        if ("随机组卷".equals(sysTest.getTypet())) {
            sysTest.setCourses(sysTitleService.getCpurse(getUser()));
            List<SysTestsubsEntity> sysTestsubsEntities = sysTestsubsDao.selectList(
                    new QueryWrapper<SysTestsubsEntity>().eq("test_id", testId));
            sysTest.setSysTestEntityVos(sysTestsubsEntities);
        }

        SysDeptEntity sysDeptEntity = sysDeptDao.selectById(sysTest.getDeptId());
        if (sysDeptEntity != null) {
            sysTest.setDeptName(sysDeptEntity.getName());
        }

        String[] split2 = sysTest.getDeptId().split(",");
        if (split2 != null) {
            List<Long> list = new ArrayList<>();
            for (String s : split2) {
                if (!"".equals(s)) {
                    list.add(Long.parseLong(s));
                }
            }
            sysTest.setDeptIdList(list);
        }


        return R.ok().put("sysTest", sysTest);
    }


    /**
     * 信息
     */
    @RequestMapping("/count/{courseId}/{topicId}")
    @RequiresPermissions("sys:systest:num")
    public R count(@PathVariable("courseId") Long courseId, @PathVariable("topicId") Long topicId) {
//        List<Long> subCourseIdList = sysCourseService.getSubCourseIdList(courseId);
//        if (subCourseIdList != null && subCourseIdList.size() > 0) {
//            subCourseIdList.add(courseId);
//        } else {
//            subCourseIdList = new ArrayList<>();
//            subCourseIdList.add(courseId);
//        }
//        int num = 0;
//        if (subCourseIdList != null && subCourseIdList.size() > 0) {
//            for (Long id : subCourseIdList) {
//                num += sysSubjectDao.selectCount(new QueryWrapper<SysSubjectEntity>()
//                        .eq("course_id", id).eq("topic_id", topicId));
//            }
//        }
        int num = sysSubjectDao.selectCount(new QueryWrapper<SysSubjectEntity>()
                .eq("course_id", courseId).eq("topic_id", topicId));
        return R.ok().put("num", num);
    }


    @RequestMapping("/titles/{testId}")
    @RequiresPermissions("sys:systest:titles")
    public R titles(@PathVariable("testId") Long testId) {
        List<SysSubjectEntity> subjects = sysTitleDao.getSubjects(testId, ShiroUtils.getUserId());
        Map<String, Set<SysSubjectEntity>> map = new HashMap<>();
        Map<Long, List<SysJungleEntity>> jungles = new HashMap<>();
        Set<Long> set = new HashSet();
        Map<Long, String> map1 = new HashMap<>();
        long num = 0;
        if (subjects != null) {
            Iterator<SysSubjectEntity> iterator = subjects.iterator();
            while (iterator.hasNext()) {
                SysSubjectEntity next = iterator.next();
                String topicName = next.getTopicName();
                Long topicId = next.getTopicId();
                set.add(topicId);
                map1.put(topicId, topicName);
                Set<SysSubjectEntity> sysSubjectEntities = map.get(topicName);
                if (sysSubjectEntities != null) {
                    sysSubjectEntities.add(next);
                    map.put(topicName, sysSubjectEntities);
                    num++;
                } else {
                    sysSubjectEntities = new HashSet<>();
                    sysSubjectEntities.add(next);
                    map.put(topicName, sysSubjectEntities);
                    num++;
                }
            }
        }
        for (Map.Entry<String, Set<SysSubjectEntity>> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.contains("单") || key.contains("多")) {
                Set<SysSubjectEntity> value = entry.getValue();
                for (SysSubjectEntity sysSubjectEntity : value) {
                    Long subjectId = sysSubjectEntity.getSubjectId();
                    List<SysJungleEntity> sysJungleEntities = sysJungleDao.selectList(new QueryWrapper<SysJungleEntity>().eq("subject_id", subjectId));
                    if (sysJungleEntities != null && sysJungleEntities.size() > 0) {
                        jungles.put(subjectId, sysJungleEntities);
                    }
                }
            }
        }
        Map<String, Long> counts = new HashMap<>();
        for (Long str : set) {
            counts.put(map1.get(str), sysTitleDao.getCount(testId, str, getUserId()));
        }


        return R.ok().put("counts", counts).put("subjects", map).put("jungles", jungles).put("num", num)
                .put("test", sysTestDao.selectById(testId)).put("size", subjects != null ? subjects.size() : 0);
    }


    @RequestMapping("/titles2/{testId}")
    @RequiresPermissions("sys:systest:titles2")
    public R titles2(@PathVariable("testId") Long testId) {
        SysTestEntity sysTestEntity = sysTestDao.selectById(testId);

        Date startTime = sysTestEntity.getStartTime();
        if (DateUtil.parseYears(startTime)) {
            return R.error("考试还未开考，不能进入考试!");
        }

        if (!DateUtil.parseYears(sysTestEntity.getEndTime())) {
            return R.error("考试已过，不能进入考试!");
        }

        if (sysTestEntity.getTypet() != null && "随机组卷".equals(sysTestEntity.getTypet())) {
            saveSubs(testId);
        }


        long num = 0;
        Long userId = ShiroUtils.getUserId();
        List<SysSubjectEntity> subjects = sysTitleDao.getSubjects(testId, userId);
        Map<String, Set<SysSubjectEntity>> map = new HashMap<>();
        Map<Long, List<SysJungleEntity>> jungles = new HashMap<>();
        Set<Long> set = new HashSet();
        Map<Long, String> map1 = new HashMap<>();
        int orders = 0;
        if (subjects != null) {
            Iterator<SysSubjectEntity> iterator = subjects.iterator();
            List<SysTitleEntity> sysTitleEntities = sysTitleDao.selectList(
                    new QueryWrapper<SysTitleEntity>().eq("test_id", testId).eq("admin", userId)
                            .orderByDesc("orders"));
            if (sysTitleEntities != null && sysTitleEntities.size() > 0) {
                orders = sysTitleEntities.get(0).getOrders();
            }
            while (iterator.hasNext()) {
                SysSubjectEntity next = iterator.next();
                String topicName = next.getTopicName();
                Long topicId = next.getTopicId();
                set.add(topicId);
                map1.put(topicId, topicName);
                Set<SysSubjectEntity> sysSubjectEntities = map.get(topicName);
                if (sysSubjectEntities != null) {
                    sysSubjectEntities.add(next);
                    map.put(topicName, sysSubjectEntities);
                    num++;
                } else {
                    sysSubjectEntities = new HashSet<>();
                    sysSubjectEntities.add(next);
                    map.put(topicName, sysSubjectEntities);
                    num++;
                }
            }
        }
        for (Map.Entry<String, Set<SysSubjectEntity>> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.contains("单") || key.contains("多")) {
                Set<SysSubjectEntity> value = entry.getValue();
                for (SysSubjectEntity sysSubjectEntity : value) {
                    Long subjectId = sysSubjectEntity.getSubjectId();
                    List<SysJungleEntity> sysJungleEntities = sysJungleDao.selectList(new QueryWrapper<SysJungleEntity>().eq("subject_id", subjectId));
                    if (sysJungleEntities != null && sysJungleEntities.size() > 0) {
                        jungles.put(subjectId, sysJungleEntities);
                    }
                }
            }
        }
        Map<String, Long> counts = new HashMap<>();
        for (Long str : set) {
            counts.put(map1.get(str), sysTitleDao.getCount(testId, str, getUserId()));
        }
        return R.ok().put("counts", counts).put("subjects", map).put("jungles", jungles).put("num", num)
                .put("test", sysTestEntity).put("size", subjects != null ? subjects.size() : 0).put("orders", orders);
    }


    private void saveSubs(Long testId) {
        List<SysTestsubsEntity> sysTestEntityVos = sysTestsubsDao.selectList(new QueryWrapper<SysTestsubsEntity>().eq("test_id", testId));
        Integer one = sysTitleDao.getOne(testId, ShiroUtils.getUserId());
        if (one == null) {
            one = 0;
        } else {
            one++;
        }
        for (int i = 0; i < sysTestEntityVos.size(); i++) {
            Long courseId = sysTestEntityVos.get(i).getCourseId();
            Long num = sysTestEntityVos.get(i).getNum();
            Long topicId = sysTestEntityVos.get(i).getTopicId();
            List<Long> longs = sysSubjectDao.queryIds(courseId, topicId);
            for (int k = 0; k < num; k++) {
                if (longs != null && longs.size() > 0) {
                    SysTitleEntity sysTitleEntity = new SysTitleEntity();
                    sysTitleEntity.setTestId(testId);
                    long num2 = MakeNum.addSomeNumber(longs.toArray());
                    sysTitleEntity.setSubjectId(String.valueOf(num2));
                    sysTitleEntity.setOrders(one);
                    sysTitleEntity.setAdmin(String.valueOf(ShiroUtils.getUserId()));
                    sysTitleEntity.setCreateTime(new Date());
                    sysTitleEntity.setTopicId(topicId);
                    sysTitleService.save(sysTitleEntity);
                    Iterator<Long> iterator = longs.iterator();
                    while (iterator.hasNext()) {
                        if (num2 == iterator.next()) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:systest:save")
    public R save(@RequestBody SysTestEntity sysTest) {
        if (sysTest.getTypet() != null && "随机组卷".equals(sysTest.getTypet())) {
            List<SysTestsubsEntity> sysTestEntityVos = sysTest.getSysTestEntityVos();
            if (sysTestEntityVos == null || sysTestEntityVos.size() == 0) {
                return R.error("题目不能为空!");
            }
            Set set = new HashSet();
            for (SysTestsubsEntity sysTestsubsEntity : sysTestEntityVos) {
                set.add(sysTestsubsEntity.getCourseId() + "-" + sysTestsubsEntity.getTopicId());
            }
            if (set.size() < sysTestEntityVos.size()) {
                return R.error("防止出现题目重复,不能出现相同目录的题库!");
            }
        }
        List<Long> testTypeList = sysTest.getTestTypeList();
        StringBuilder stringBuilder = new StringBuilder();
        if (testTypeList != null) {
            for (int i = 0; i < testTypeList.size(); i++) {
                stringBuilder.append(testTypeList.get(i));
                if (i != testTypeList.size() - 1) {
                    stringBuilder.append(",");
                }
            }
        }
        sysTest.setTestType(stringBuilder.toString());


        List<Long> adminsList = sysTest.getAdminsList();
        StringBuilder stringBuilder1 = new StringBuilder();
        if (adminsList != null) {
            for (int i = 0; i < adminsList.size(); i++) {
                stringBuilder1.append(adminsList.get(i));
                if (i != adminsList.size() - 1) {
                    stringBuilder1.append(",");
                }
            }
        }
        sysTest.setAdminId(stringBuilder1.toString());

        String admin = String.valueOf(ShiroUtils.getUserId());
        sysTest.setAdmin(admin);
        sysTest.setCreateTime(DateUtil.getDate());

        String uuid = UUIDBuild.getUUID();
        sysTest.setUuid(uuid);

        List<Long> deptIdList = sysTest.getDeptIdList();
        StringBuffer stringBuffer = new StringBuffer();
        for (Long deptId : deptIdList) {
            stringBuffer.append(deptId + ",");
        }
        sysTest.setDeptId(stringBuffer.toString());

        sysTestService.save(sysTest);

        if (sysTest.getTypet() != null && "随机组卷".equals(sysTest.getTypet())) {
            List<SysTestsubsEntity> sysTestEntityVos = sysTest.getSysTestEntityVos();
            SysTestEntity uuid1 = sysTestDao.selectOne(new QueryWrapper<SysTestEntity>().eq("uuid", uuid));
            Long testId = uuid1.getTestId();
            for (SysTestsubsEntity sysTestEntityVo : sysTestEntityVos) {
                sysTestEntityVo.setCreateTime(DateUtil.getDate());
                sysTestEntityVo.setTestId(testId);
                sysTestEntityVo.setAdmin(admin);
                sysTestsubsService.save(sysTestEntityVo);
            }

        }


        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:systest:update")
    public R update(@RequestBody SysTestEntity sysTest) {
        ValidatorUtils.validateEntity(sysTest);

        List<Long> testTypeList = sysTest.getTestTypeList();
        StringBuilder stringBuilder = new StringBuilder();
        if (testTypeList != null) {
            for (int i = 0; i < testTypeList.size(); i++) {
                stringBuilder.append(testTypeList.get(i));
                if (i != testTypeList.size() - 1) {
                    stringBuilder.append(",");
                }
            }
        }
        sysTest.setTestType(stringBuilder.toString());


        List<Long> adminsList = sysTest.getAdminsList();
        StringBuilder stringBuilder1 = new StringBuilder();
        if (adminsList != null) {
            for (int i = 0; i < adminsList.size(); i++) {
                stringBuilder1.append(adminsList.get(i));
                if (i != adminsList.size() - 1) {
                    stringBuilder1.append(",");
                }
            }
        }
        sysTest.setAdminId(stringBuilder1.toString());
        List<Long> deptIdList = sysTest.getDeptIdList();
        StringBuffer stringBuffer = new StringBuffer();
        for (Long deptId : deptIdList) {
            stringBuffer.append(deptId + ",");
        }
        sysTest.setDeptId(stringBuffer.toString());

        sysTestService.updateById(sysTest);

        if (sysTest.getTypet() != null && "随机组卷".equals(sysTest.getTypet())) {
            Long testId1 = sysTest.getTestId();
            sysTestsubsDao.delete(new QueryWrapper<SysTestsubsEntity>().eq("test_id", testId1));
            List<SysTestsubsEntity> sysTestEntityVos = sysTest.getSysTestEntityVos();
            for (SysTestsubsEntity sysTestEntityVo : sysTestEntityVos) {
                sysTestEntityVo.setCreateTime(DateUtil.getDate());
                sysTestEntityVo.setTestId(testId1);
                sysTestEntityVo.setAdmin(String.valueOf(getUserId()));
                sysTestsubsService.save(sysTestEntityVo);
            }
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:systest:delete")
    public R delete(@RequestBody Long[] testIds) {
        sysTestService.removeByIds(Arrays.asList(testIds));
        for (Long id : testIds) {
            sysTitleDao.delete(new QueryWrapper<SysTitleEntity>().eq("test_id", id));
        }
        return R.ok();
    }



    public static int byteToInteger(Byte b) {
        return 0xff & b;
    }


}
