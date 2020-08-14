package io.renren.modules.title.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.course.dao.SysCourseDao;
import io.renren.modules.course.service.SysCourseService;
import io.renren.modules.subject.dao.SysSubjectDao;
import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.test.dao.SysTestDao;
import io.renren.modules.test.entity.SysTestEntity;
import io.renren.modules.title.dao.SysTitleDao;
import io.renren.modules.title.entity.SysTitleEntity;
import io.renren.modules.title.service.SysTitleService;
import io.renren.modules.topic.dao.SysTopicDao;
import io.renren.modules.topic.entity.SysTopicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service("sysTitleService")
public class SysTitleServiceImpl extends ServiceImpl<SysTitleDao, SysTitleEntity> implements SysTitleService {

    @Autowired
    private SysTopicDao sysTopicDao;
    @Autowired
    private SysCourseService sysCourseService;
    @Autowired
    private SysCourseDao sysCourseDao;
    @Resource
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysSubjectDao sysSubjectDao;

    @Autowired
    private SysTestDao sysTestDao;

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysTitleDao sysTitleDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String name = (String) params.get("name");
        String deptId2 = (params.get("deptId") != null && !"".equals(params.get("deptId")) ? (String) params.get("deptId") : "-1");

        List<SysTitleEntity> sysTitleEntities = sysTitleDao.selectList(new QueryWrapper<SysTitleEntity>().orderByAsc("orders"
                , "create_time"));
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList = new ArrayList<>();
        subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
        subDeptIdList.addAll(subDeptIdList1);
        subDeptIdList.add(0L);
        Iterator<SysTitleEntity> iterator = sysTitleEntities.iterator();
        while (iterator.hasNext()) {
            SysTitleEntity sysTitleEntity = iterator.next();
            sysTitleEntity.setTestName(sysTestDao.selectById(sysTitleEntity.getTestId()).getName());
            SysSubjectEntity sysSubjectEntity = sysSubjectDao.selectById(sysTitleEntity.getSubjectId());
            if (sysSubjectEntity == null) {
                iterator.remove();
                continue;
            }
            String subjectName = sysSubjectEntity.getName();

            SysTestEntity sysTestEntity = sysTestDao.selectById(sysTitleEntity.getTestId());
            if (sysTestEntity == null) {
                iterator.remove();
                continue;
            }

            if (name != null && !"".equals(name)) {
                if (!sysTestEntity.getName().equals(name)) {
                    iterator.remove();
                    continue;
                }
            }
            if (!deptId2.equals("-1")) {
                if (sysTestEntity.getDeptId().indexOf(deptId2) == -1) {
                    iterator.remove();
                    continue;
                }
            }

            sysTitleEntity.setSubjectName(subjectName);

            SysUserEntity sysUserEntity = sysUserDao.selectById(sysTitleEntity.getAdmin());
            if (!subDeptIdList.contains(sysUserEntity.getDeptId()) && deptId != sysUserEntity.getDeptId()) {
                iterator.remove();
                continue;
            }
            SysUserEntity sysUserEntity1 = sysUserDao.selectById(Long.parseLong(sysTitleEntity.getAdmin()));
            sysTitleEntity.setAdmin(sysUserEntity1.getName());
        }

        IPage<SysTitleEntity> page = new Page<>();
        long total = sysTitleEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysTitleEntity> subList = null;
        if (total < toIndex) {
            subList = sysTitleEntities.subList(index, (int) (total));
        } else {
            subList = sysTitleEntities.subList(index, toIndex);
        }
        page.setRecords(subList);
        page.setTotal(total);
        page.setPages(total / size);
        page.setCurrent(current);
        page.setSize(size);
        return new PageUtils(page);
    }

    @Override
    public List<IdentityHashMap<String, Long>> getCpurse(SysUserEntity sysUserEntity) {
        List<SysSubjectEntity> sysSubjectEntities = sysSubjectDao.selectList(null);
        Long deptId = sysUserEntity.getDeptId();
        List<Long> longs1 = sysDeptService.getSubDeptIdList(deptId);
        List<IdentityHashMap<String, Long>> list = new ArrayList<>();
        if (sysSubjectEntities != null) {
            for (SysSubjectEntity sysSubjectEntity : sysSubjectEntities) {
                Long deptId1 = sysUserDao.selectById(Long.parseLong(sysSubjectEntity.getAdmin())).getDeptId();
                if (longs1 != null) {
                    if (longs1.contains(deptId1) || deptId1 == deptId) {
                        Long courseId = sysSubjectEntity.getCourseId();
                        List<Long> longs = sysCourseService.queryParentCourseIdList(courseId);
                        if (longs.size() == 0) {
                            continue;
                        }
                        int size = longs.size();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = size - 1; i >= 0; i--) {
                            stringBuilder.append(sysCourseDao.selectById(longs.get(i)).getName());
                            if (i != 0) {
                                stringBuilder.append("-");
                            }
                        }
                        IdentityHashMap<String, Long> map = new IdentityHashMap<>();
                        map.put(stringBuilder.toString(), sysSubjectEntity.getSubjectId());
                        list.add(map);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public IdentityHashMap<String, Long> getTopic(Long id, SysUserEntity sysUserEntity) {
        IdentityHashMap<String, Long> map = new IdentityHashMap<>();
        List<SysSubjectEntity> sysSubjectEntities = sysSubjectDao.selectList(new QueryWrapper<SysSubjectEntity>().eq("course_id",
                id));
        Long deptId = sysUserEntity.getDeptId();
        List<Long> longs1 = sysDeptService.getSubDeptIdList(deptId);
        if (sysSubjectEntities != null) {
            for (SysSubjectEntity sysSubjectEntity : sysSubjectEntities) {
                Long deptId1 = sysUserDao.selectById(Long.parseLong(sysSubjectEntity.getAdmin())).getDeptId();
                if (longs1 != null) {
                    if (longs1.contains(deptId1) || deptId1 == deptId) {
                        Long topicId = sysSubjectEntity.getTopicId();
                        SysTopicEntity sysTopicEntity = sysTopicDao.selectById(topicId);
                        map.put(sysTopicEntity.getName2(), sysTopicEntity.getTopicId());
                    }
                }
            }
        }
        return map;
    }

    @Override
    public IdentityHashMap<String, Long> getSubjects(Long topicId, Long courseId, SysUserEntity sysUserEntity) {
        List<SysSubjectEntity> sysSubjectEntities = sysSubjectDao.selectList(new QueryWrapper<SysSubjectEntity>().eq("topic_id",
                topicId).eq("course_id", courseId));
        Long deptId = sysUserEntity.getDeptId();
        IdentityHashMap<String, Long> map = new IdentityHashMap<>();
        List<Long> longs1 = sysDeptService.getSubDeptIdList(deptId);
        if (sysSubjectEntities != null) {
            for (SysSubjectEntity sysSubjectEntity : sysSubjectEntities) {
                Long deptId1 = sysUserDao.selectById(Long.parseLong(sysSubjectEntity.getAdmin())).getDeptId();
                if (longs1 != null) {
                    if (longs1.contains(deptId1) || deptId1 == deptId) {
                        map.put(sysSubjectEntity.getName(), sysSubjectEntity.getSubjectId());
                    }
                }
            }
        }
        return map;
    }


}
