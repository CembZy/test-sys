package io.renren.modules.subject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.*;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.answer.dao.SysAnswerDao;
import io.renren.modules.answer.entity.SysAnswerEntity;
import io.renren.modules.course.entity.SysCourseEntity;
import io.renren.modules.course.service.SysCourseService;
import io.renren.modules.jungle.dao.SysJungleDao;
import io.renren.modules.jungle.entity.SysJungleEntity;
import io.renren.modules.jungle.service.SysJungleService;
import io.renren.modules.subject.dao.SysSubjectDao;
import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.subject.service.SysSubjectService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.test.dao.SysTestDao;
import io.renren.modules.test.entity.SysTestEntity;
import io.renren.modules.title.dao.SysTitleDao;
import io.renren.modules.title.entity.SysTitleEntity;
import io.renren.modules.topic.dao.SysTopicDao;
import io.renren.modules.topic.entity.SysTopicEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:15:54
 */
@RestController
@RequestMapping("sys/syssubject")
public class SysSubjectController extends AbstractController {
    @Autowired
    private SysSubjectService sysSubjectService;
    @Autowired
    private SysCourseService sysCourseService;
    @Autowired
    private SysTopicDao sysTopicDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysJungleService sysJungleService;
    @Autowired
    private SysJungleDao sysJungleDao;
    @Autowired
    private SysSubjectDao sysSubjectDao;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysTestDao sysTestDao;
    @Autowired
    private SysTitleDao sysTitleDao;
    @Autowired
    private SysAnswerDao sysAnswerDao;

    @RequestMapping("/select")
    @RequiresPermissions("sys:syssubject:select")
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
    @RequiresPermissions("sys:syssubject:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysSubjectService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/list2")
    @RequiresPermissions("sys:syssubject:list")
    public R list2(@RequestParam Map<String, Object> params) {
        PageUtils page = sysSubjectService.queryPage2(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/subjects/{courseId}")
    @RequiresPermissions("sys:syssubject:subjects")
    public R subjects(@PathVariable("courseId") Long courseId) {
        List<Long> subCourseIdList = sysCourseService.getSubCourseIdList(courseId);
        if (subCourseIdList != null && subCourseIdList.size() > 0) {
            subCourseIdList.add(courseId);
        } else {
            subCourseIdList = new ArrayList<>();
            subCourseIdList.add(courseId);
        }
        List<SysSubjectEntity> sysSubjectEntities = new ArrayList<>();
        for (Long id : subCourseIdList) {
            List<SysSubjectEntity> sysSubjectEntities1 = sysSubjectDao
                    .selectList(new QueryWrapper<SysSubjectEntity>().eq("course_id", id)
                            .groupBy("topic_id"));
            if (sysSubjectEntities1 != null && sysSubjectEntities1.size() > 0) {
                sysSubjectEntities.addAll(sysSubjectEntities1);
            }
        }
        Map<String, Long> map = new HashMap<>();
        if (sysSubjectEntities != null && sysSubjectEntities.size() > 0) {
            Long deptId = ShiroUtils.getUserEntity().getDeptId();
            List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(deptId);
//            List<Long> subDeptIdList = new ArrayList<>();
//            subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
//            List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
//            subDeptIdList.addAll(subDeptIdList1);
//            subDeptIdList.add(0L);
            for (SysSubjectEntity sysSubjectEntity : sysSubjectEntities) {
                SysUserEntity sysUserEntity = sysUserDao.selectById(sysSubjectEntity.getAdmin());
                if (sysUserEntity != null && (subDeptIdList.contains(sysUserEntity.getDeptId()) || deptId == sysUserEntity.getDeptId())) {
                    SysTopicEntity sysTopicEntity = sysTopicDao.selectById(sysSubjectEntity.getTopicId());
                    if (sysTopicEntity != null) {
                        map.put(sysTopicEntity.getName2(), sysTopicEntity.getTopicId());
                    }
                }
            }
        }
        return R.ok().put("topics", map);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{subjectId}")
    @RequiresPermissions("sys:syssubject:info")
    public R info(@PathVariable("subjectId") Long subjectId) {
        SysSubjectEntity sysSubject = sysSubjectService.getById(subjectId);

        List<SysTopicEntity> sysCourseEntityList = sysTopicDao.selectList(null);
        Iterator<SysTopicEntity> iterator = sysCourseEntityList.iterator();
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
//        List<Long> subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
//        subDeptIdList.add(0L);
        List<Long> subDeptIdList = new ArrayList<>();
        subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
        subDeptIdList.addAll(subDeptIdList1);
        subDeptIdList.add(0L);
        Map<String, Long> map = new IdentityHashMap<>();
        while (iterator.hasNext()) {
            SysTopicEntity sysTopicEntity = iterator.next();
            SysUserEntity sysUserEntity = sysUserDao.selectById(sysTopicEntity.getAdmin());
            if (subDeptIdList.contains(sysUserEntity.getDeptId()) || deptId == sysUserEntity.getDeptId()) {
                map.put(sysTopicEntity.getName2(), sysTopicEntity.getTopicId());
            }
        }
        sysSubject.setTopics(map);

        List<SysJungleEntity> subjectId1 = sysJungleDao.selectList(new QueryWrapper<SysJungleEntity>().eq("subject_id", sysSubject.getSubjectId()));
        sysSubject.setJugleList(subjectId1);

        return R.ok().put("sysSubject", sysSubject);
    }


    @RequestMapping("getTopics")
    @RequiresPermissions("sys:syssubject:getTopics")
    public R getTopics() {
        List<SysTopicEntity> sysCourseEntityList = sysTopicDao.selectList(null);
        Iterator<SysTopicEntity> iterator = sysCourseEntityList.iterator();
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
//        List<Long> subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
//        subDeptIdList.add(0L);
        List<Long> subDeptIdList = new ArrayList<>();
        subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
        subDeptIdList.addAll(subDeptIdList1);
        subDeptIdList.add(0L);
        Map<String, Long> map = new IdentityHashMap<>();
        while (iterator.hasNext()) {
            SysTopicEntity sysTopicEntity = iterator.next();
            SysUserEntity sysUserEntity = sysUserDao.selectById(sysTopicEntity.getAdmin());
            Long deptId1 = sysUserEntity.getDeptId();
            if (subDeptIdList.contains(deptId1) || deptId == deptId1) {
                map.put(sysTopicEntity.getName2() + "-" + sysTopicEntity.getType(), sysTopicEntity.getTopicId());
            }
        }
        return R.ok().put("topics", map);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:syssubject:save")
    public R save(@RequestBody SysSubjectEntity sysSubject) {

        Long courseId = sysSubject.getCourseId();
        if (courseId == null) {
            return R.error("请选择所属科目!");
        }

        String admin = String.valueOf(ShiroUtils.getUserId());
        sysSubject.setAdmin(admin);
        sysSubject.setCreateTime(new Date());
        String uuid = UUIDBuild.getUUID();
        sysSubject.setUuid(uuid);
        if (sysSubject.getAnswertt() != null && !"".equals(sysSubject.getAnswertt())) {
            String str = sysSubject.getAnswertt();
            String substring = str.substring(str.length() - 1, str.length());
            if (",".equals(substring)) {
                sysSubject.setAnswertt(str.substring(0, str.length() - 1));
//                sysSubject.setAnswertt(str.split(substring)[0]);
            }
        }
        sysSubjectDao.insert(sysSubject);

        List<SysJungleEntity> jugleList = sysSubject.getJugleList();
        if (jugleList != null) {
            Long uuid1 = sysSubjectDao.selectOne(new QueryWrapper<SysSubjectEntity>().eq("uuid", uuid)).getSubjectId();
            for (SysJungleEntity sysJungleEntity : jugleList) {
                sysJungleEntity.setAdmin(admin);
                sysJungleEntity.setCreateTime(new Date());
                sysJungleEntity.setSubjectId(uuid1);
                sysJungleService.save(sysJungleEntity);
            }

        }

        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:syssubject:update")
    public R update(@RequestBody SysSubjectEntity sysSubject) {
        Long courseId = sysSubject.getCourseId();
        if (courseId == null) {
            return R.error("请选择所属科目!");
        }
        if (sysSubject.getAnswertt() != null && !"".equals(sysSubject.getAnswertt())) {
            String str = sysSubject.getAnswertt();
            String substring = str.substring(str.length() - 1, str.length());
            if (",".equals(substring)) {
                sysSubject.setAnswertt(str.substring(0, str.length() - 1));
            }
        }
        ValidatorUtils.validateEntity(sysSubject);
        sysSubjectService.updateById(sysSubject);

        SysTopicEntity sysTopicEntity = sysTopicDao.selectById(sysSubject.getTopicId());
        if (sysTopicEntity != null) {
            String topicName = sysTopicEntity.getName();
            if (topicName != null && (topicName.contains("单") || topicName.contains("多"))) {
                Long subjectId = sysSubject.getSubjectId();
                sysJungleDao.delete(new QueryWrapper<SysJungleEntity>().eq("subject_id", subjectId));
                List<SysJungleEntity> jugleList = sysSubject.getJugleList();
                if (jugleList != null) {
                    String admin = String.valueOf(ShiroUtils.getUserId());
                    for (SysJungleEntity sysJungleEntity : jugleList) {
                        sysJungleEntity.setAdmin(admin);
                        sysJungleEntity.setCreateTime(new Date());
                        sysJungleEntity.setSubjectId(subjectId);
                        sysJungleService.save(sysJungleEntity);
                    }

                }

            }
        }


        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:syssubject:delete")
    public R delete(@RequestBody Long[] subjectIds) {
        if (subjectIds != null) {
            for (Long id : subjectIds) {
                List<SysTestEntity> sysTestEntities = sysTestDao.selectList(new QueryWrapper<SysTestEntity>().eq("subject_id", id));
                if (sysTestEntities != null && sysTestEntities.size() > 0) {
                    return R.error("有试卷使用到此题，因此不能删除，应先删除试卷!");
                }
            }

            for (Long id : subjectIds) {
                sysTestDao.delete(new QueryWrapper<SysTestEntity>().eq("subject_id", id));
                sysTitleDao.delete(new QueryWrapper<SysTitleEntity>().eq("subject_id", id));
                sysJungleDao.delete(new QueryWrapper<SysJungleEntity>().eq("subject_id", id));
                sysAnswerDao.delete(new QueryWrapper<SysAnswerEntity>().eq("subject_id", id));
            }
        }

        sysSubjectService.removeByIds(Arrays.asList(subjectIds));
        return R.ok();
    }


    /**
     * 导入
     *
     * @param sysSubject
     * @return
     */
    @RequestMapping("/input")
    @RequiresPermissions("sys:syssubject:input")
    public R input(@RequestBody SysSubjectEntity sysSubject) {
        sysSubject.setAdmin(String.valueOf(ShiroUtils.getUserId()));
        sysSubject.setCreateTime(new Date());
        String key = sysSubject.getUuid();
        List<SysSubjectEntity> cache = CacheUtil.getTestsCache(key);
        for (SysSubjectEntity sysSubjectEntity : cache) {
            String topicName = sysSubjectEntity.getTopicName();
            if (topicName.contains("单") || topicName.contains("多") || topicName.contains("操作") || topicName.contains("判断")) {
                return R.error("请导入正确的题型，只能导入填空题，写作题和问答题!");
            }

            List<SysTopicEntity> sysTopicEntities = sysTopicDao.selectList(new QueryWrapper<SysTopicEntity>().like("type", topicName).eq("status", 1));
            if (sysTopicEntities != null) {
                for (SysTopicEntity sysTopicEntity : sysTopicEntities) {
                    long chiledId = Long.parseLong(sysTopicEntity.getAdmin());
//                    List<Long> longs = sysDeptService.queryParentDetpIdList(chiledId);
                    List<Long> longs = new ArrayList<>();
                    longs = sysDeptService.queryParentDetpIdList(chiledId);
                    List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(chiledId);
                    longs.addAll(subDeptIdList1);
                    longs.add(0L);
                    if (longs != null) {
                        if (longs.contains(getUserId())) {
                            sysSubject.setTopicId(sysTopicEntity.getTopicId());
                            break;
                        } else {
                            return R.error("没有此类型的题型，请在题库中添加次题型!");
                        }
                    }
                }
            } else {
                return R.error("没有此类型的题型，请在题库中添加次题型!");
            }

            sysSubject.setTopicName(topicName);
            sysSubject.setDifficulty(sysSubjectEntity.getDifficulty());
            sysSubject.setParse(parse(sysSubjectEntity.getParse()));
            sysSubject.setContent(parse(sysSubjectEntity.getContent()));
            sysSubject.setAnswer(parse(sysSubjectEntity.getAnswer()));
            sysSubject.setParse(parse(sysSubjectEntity.getParse()));

            sysSubjectService.save(sysSubject);
        }
        CacheUtil.removeTestsCache(key);
        return R.ok();
    }


    private String parse(String str) {
        return str == null ? "" : str;
    }


    /**
     * 导出
     *
     * @return
     */
    @RequestMapping("/output")
    @RequiresPermissions("sys:syssubject:output")
    public void output(@RequestParam("subjectIds") Long[] subjectIds, HttpServletResponse response) {
        List<SysSubjectEntity> sysSubjectEntities = new ArrayList<>();
        for (Long id : subjectIds) {
            SysSubjectEntity sysSubjectEntity = sysSubjectDao.selectById(id);
            if (sysSubjectEntity == null) {
                continue;
            }
            SysTopicEntity sysTopicEntity = sysTopicDao.selectById(sysSubjectEntity.getTopicId());
            if (sysTopicEntity == null) {
                continue;
            }
            sysSubjectEntity.setTopicName(sysTopicEntity.getName2());
            sysSubjectEntities.add(sysSubjectEntity);
        }

        // 定义表的标题
        String title = "题库";
        //定义表的列名
        String[] rowsName = new String[]{"题型名称", "题型难度", "试题分数", "试题内容", "试题解析"};
        //定义表的内容
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        int length = 5;
        for (int i = 0; i < sysSubjectEntities.size(); i++) {
            SysSubjectEntity per = sysSubjectEntities.get(i);
            objs = new Object[length];
            objs[0] = per.getTopicName() == null ? "" : getStr(per.getTopicName());
            objs[1] = per.getDifficulty() == null ? "" : per.getDifficulty();
            objs[2] = per.getPrice() == null ? "" : per.getPrice();
            objs[3] = per.getContent() == null ? "" : getStr(per.getContent());
//            objs[4] = per.getAnswer() == null ? "" : getStr(per.getAnswer());
            objs[4] = per.getParse() == null ? "" : getStr(per.getParse());
            dataList.add(objs);
        }
        // 创建ExportExcel对象
        ExportExcel ex = new ExportExcel(title, rowsName, dataList);
        // 输出Excel文件
        OutputStream output = null;
        try {
            output = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("题库表.xls", "utf-8"));
            response.setContentType("application/vnd.ms-excel");
            ex.export(output);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String getStr(String str) {
        return str.replaceAll("<[.[^<]]*>", "");
    }

}
