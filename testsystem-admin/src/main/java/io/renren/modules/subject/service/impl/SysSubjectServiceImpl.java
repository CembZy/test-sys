package io.renren.modules.subject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.course.dao.SysCourseDao;
import io.renren.modules.course.entity.SysCourseEntity;
import io.renren.modules.course.service.SysCourseService;
import io.renren.modules.subject.dao.SysSubjectDao;
import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.subject.service.SysSubjectService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.topic.dao.SysTopicDao;
import io.renren.modules.topic.entity.SysTopicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("sysSubjectService")
public class SysSubjectServiceImpl extends ServiceImpl<SysSubjectDao, SysSubjectEntity> implements SysSubjectService {

    @Autowired
    private SysTopicDao sysTopicDao;
    @Autowired
    private SysCourseService sysCourseService;
    @Autowired
    private SysCourseDao sysCourseDao;
    @Resource
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysSubjectDao sysSubjectDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String topicName = (String) params.get("topicName");
        String courseId2 = (String) params.get("courseId");
        List<SysSubjectEntity> sysSubjectEntities = sysSubjectDao.selectList(new QueryWrapper<SysSubjectEntity>());
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList = new ArrayList<>();
        subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
        subDeptIdList.addAll(subDeptIdList1);
        subDeptIdList.add(0L);
        Iterator<SysSubjectEntity> iterator = sysSubjectEntities.iterator();
        while (iterator.hasNext()) {
            SysSubjectEntity sysSubjectEntity = iterator.next();
            Long topicId = sysSubjectEntity.getTopicId();
            SysTopicEntity sysTopicEntity = sysTopicDao.selectById(topicId);
            if (sysTopicEntity != null) {
                String name1 = sysTopicEntity.getName2();
                if (topicName != null && !"".equals(topicName) && !topicName.equals(name1)) {
                    iterator.remove();
                    continue;
                }
                sysSubjectEntity.setTopicName(name1);
            }


            Long courseId = sysSubjectEntity.getCourseId();
            List<Long> longs = sysCourseService.queryParentCourseIdList(courseId);
            if (longs.size() == 0) {
                continue;
            }

            if ((courseId2 != null && !"".equals(courseId2))) {
                Long courseId3 = Long.parseLong(courseId2);
                if (!(longs.contains(courseId3))) {
                    iterator.remove();
                    continue;
                }
            }
            int size = longs.size();
            SysCourseEntity sysCourseEntity = sysCourseDao.selectById(longs.get(size - 1));
            String name = sysCourseEntity.getName();
            sysSubjectEntity.setCourseName(name);


            StringBuilder stringBuilder = new StringBuilder();
            for (int i = size - 1; i >= 0; i--) {
                stringBuilder.append(sysCourseDao.selectById(longs.get(i)).getName());
                if (i != 0) {
                    stringBuilder.append("-");
                }
            }
            String courseNameT = stringBuilder.toString();
            sysSubjectEntity.setCourseNameT(courseNameT);


            SysUserEntity sysUserEntity = sysUserDao.selectById(sysSubjectEntity.getAdmin());
            if (ShiroUtils.getUserId() != 1 && !subDeptIdList.contains(sysUserEntity.getDeptId()) && deptId != sysUserEntity.getDeptId()) {
                iterator.remove();
                continue;
            }

            SysUserEntity sysUserEntity1 = sysUserDao.selectById(Long.parseLong(sysCourseEntity.getAdmin()));
            sysCourseEntity.setAdmin(sysUserEntity1.getName());
        }

        IPage<SysSubjectEntity> page = new Page<>();
        long total = sysSubjectEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysSubjectEntity> subList = null;
        if (total < toIndex) {
            subList = sysSubjectEntities.subList(index, (int) (total));
        } else {
            subList = sysSubjectEntities.subList(index, toIndex);
        }
        page.setRecords(subList);
        page.setTotal(total);
        page.setPages(total / size);
        page.setCurrent(current);
        page.setSize(size);

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage2(Map<String, Object> params) {

        String topicName = (String) params.get("topicName");
        String difficulty = (String) params.get("difficulty");
        Long courseId2 = (params.get("courseId") != null && !"".equals(params.get("courseId"))) ? Long.parseLong((String) params.get("courseId")) : -1;
        List<SysSubjectEntity> sysSubjectEntities = sysSubjectDao.selectList(new QueryWrapper<SysSubjectEntity>().groupBy("course_id", "topic_id", "difficulty"));
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList = new ArrayList<>();
        subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
        subDeptIdList.addAll(subDeptIdList1);
        subDeptIdList.add(0L);
        Iterator<SysSubjectEntity> iterator = sysSubjectEntities.iterator();
        while (iterator.hasNext()) {
            SysSubjectEntity sysSubjectEntity = iterator.next();
            Long topicId = sysSubjectEntity.getTopicId();
            SysTopicEntity sysTopicEntity = sysTopicDao.selectById(topicId);
            String name1 = sysTopicEntity.getName2();
            if (topicName != null && !"".equals(topicName) && !topicName.equals(name1)) {
                iterator.remove();
                continue;
            }

            if (difficulty != null && !"".equals(difficulty) && !sysSubjectEntity.getDifficulty().equals(difficulty)) {
                iterator.remove();
                continue;
            }
            sysSubjectEntity.setTopicName(name1);

            Long courseId = sysSubjectEntity.getCourseId();
            List<Long> longs = sysCourseService.queryParentCourseIdList(courseId);
            if (longs.size() == 0) {
                continue;
            }

            if (courseId2 != -1) {
                if (!(longs.contains(courseId2))) {
                    iterator.remove();
                    continue;
                }
            }
            SysUserEntity sysUserEntity = sysUserDao.selectById(sysSubjectEntity.getAdmin());
            if (ShiroUtils.getUserId() != 1 && !subDeptIdList.contains(sysUserEntity.getDeptId()) && deptId != sysUserEntity.getDeptId()) {
                iterator.remove();
                continue;
            }

            int size = longs.size();
            SysCourseEntity sysCourseEntity = sysCourseDao.selectById(longs.get(size - 1));
            String name = sysCourseEntity.getName();
            sysSubjectEntity.setCourseName(name);


            StringBuilder stringBuilder = new StringBuilder();
            for (int i = size - 1; i >= 0; i--) {
                stringBuilder.append(sysCourseDao.selectById(longs.get(i)).getName());
                if (i != 0) {
                    stringBuilder.append("-");
                }
            }
            String courseNameT = stringBuilder.toString();
            sysSubjectEntity.setCourseNameT(courseNameT);


            int integer = sysSubjectDao.selectCount(new QueryWrapper<SysSubjectEntity>().eq("course_id", sysSubjectEntity.getCourseId())
                    .eq("topic_id", sysSubjectEntity.getTopicId()).eq("difficulty", sysSubjectEntity.getDifficulty()));
            sysSubjectEntity.setNum2((long) integer);

            SysUserEntity sysUserEntity1 = sysUserDao.selectById(Long.parseLong(sysCourseEntity.getAdmin()));
            sysCourseEntity.setAdmin(sysUserEntity1.getName());
        }

        IPage<SysSubjectEntity> page = new Page<>();
        long total = sysSubjectEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysSubjectEntity> subList = null;
        if (total < toIndex) {
            subList = sysSubjectEntities.subList(index, (int) (total));
        } else {
            subList = sysSubjectEntities.subList(index, toIndex);
        }
        page.setRecords(subList);
        page.setTotal(total);
        page.setPages(total / size);
        page.setCurrent(current);
        page.setSize(size);
        return new PageUtils(page);
    }


}
