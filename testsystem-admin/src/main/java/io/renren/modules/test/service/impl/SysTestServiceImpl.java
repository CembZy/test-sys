package io.renren.modules.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.DateUtil;
import io.renren.common.utils.PageUtils;
import io.renren.modules.answer.dao.SysAnswerDao;
import io.renren.modules.answer.entity.SysAnswerEntity;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.test.dao.SysTestDao;
import io.renren.modules.test.entity.SysTestEntity;
import io.renren.modules.test.service.SysTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service("sysTestService")
public class SysTestServiceImpl extends ServiceImpl<SysTestDao, SysTestEntity> implements SysTestService {
    @Resource
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysAnswerDao sysAnswerDao;
    @Autowired
    private SysTestDao sysTestDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        String type = (String) params.get("type");
        String deptId2 = (params.get("deptId") != null && !"".equals(params.get("deptId"))) ? (String) params.get("deptId") : "-1";
        QueryWrapper<SysTestEntity> sysTestEntityQueryWrapper = new QueryWrapper<>();
        if ((type != null && !"".equals(type)) && (name != null && !"".equals(name))) {
            sysTestEntityQueryWrapper.eq("type", type)
                    .eq("name", name).orderByAsc("start_time");
        } else if (type != null && !"".equals(type)) {
            sysTestEntityQueryWrapper.eq("type", type).orderByAsc("start_time");
        } else if (name != null && !"".equals(name)) {
            sysTestEntityQueryWrapper
                    .eq("name", name).orderByAsc("start_time");
        } else {
            sysTestEntityQueryWrapper.orderByAsc("start_time");
        }

        List<SysTestEntity> sysTestEntities = sysTestDao.selectList(sysTestEntityQueryWrapper);
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList = new ArrayList<>();
        subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
        subDeptIdList.addAll(subDeptIdList1);
        subDeptIdList.add(0L);
        Iterator<SysTestEntity> iterator = sysTestEntities.iterator();
        while (iterator.hasNext()) {
            SysTestEntity sysTestEntity = iterator.next();
            SysUserEntity sysUserEntity = sysUserDao.selectById(sysTestEntity.getAdmin());
            if (sysUserEntity != null && !subDeptIdList.contains(sysUserEntity.getDeptId()) && deptId != sysUserEntity.getDeptId()) {
                iterator.remove();
                continue;
            }

            if (!deptId2.equals("-1")) {
                if (sysTestEntity.getDeptId().indexOf(deptId2) == -1) {
                    iterator.remove();
                    continue;
                }
            }

            String[] split = (sysTestEntity.getTestType() != null && !"".equals(sysTestEntity.getTestType())) ? sysTestEntity.getTestType().split(",") : null;
            if (split != null) {
                List<Long> list = new ArrayList<>();
                for (String str : split) {
                    if (!"".equals(str)) {
                        list.add(Long.parseLong(str));
                    }
                }
                sysTestEntity.setTestTypeList(list);
            }


            String[] strings = (sysTestEntity.getAdminId() != null && !"".equals(sysTestEntity.getAdminId())) ? sysTestEntity.getAdminId().split(",") : null;
            if (strings != null) {
                List<Long> list = new ArrayList<>();
                for (String str : strings) {
                    if (!"".equals(str)) {
                        list.add(Long.parseLong(str));
                    }
                }
                sysTestEntity.setAdminsList(list);
            }


            SysUserEntity sysUserEntity1 = sysUserDao.selectById(Long.parseLong(sysTestEntity.getAdmin()));
            if (sysUserEntity1 != null) {
                sysTestEntity.setAdmin(sysUserEntity1.getName());
            }
        }

        IPage<SysTestEntity> page = new Page<>();
        long total = sysTestEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysTestEntity> subList = null;
        if (total < toIndex) {
            subList = sysTestEntities.subList(index, (int) (total));
        } else {
            subList = sysTestEntities.subList(index, toIndex);
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
        String name = (String) params.get("name");
        String type = (String) params.get("type");
        QueryWrapper<SysTestEntity> sysTestEntityQueryWrapper = new QueryWrapper<>();
        if (name != null && !"".equals(name)) {
            sysTestEntityQueryWrapper.eq("type", type)
                    .eq("name", name).orderByAsc("start_time");
        } else {
            sysTestEntityQueryWrapper.eq("type", type).orderByAsc("start_time");
        }

        List<SysTestEntity> sysTestEntities = sysTestDao.selectList(sysTestEntityQueryWrapper);
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList = new ArrayList<>();
        subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
        subDeptIdList.addAll(subDeptIdList1);
        subDeptIdList.add(0L);
        Iterator<SysTestEntity> iterator = sysTestEntities.iterator();
        while (iterator.hasNext()) {
            SysTestEntity sysTestEntity = iterator.next();
            String[] split = sysTestEntity.getDeptId().split(",");
            boolean flag = false;
            if (split != null) {
                for (String s : split) {
                    if (!"".equals(s)) {
                        long deptId1 = Long.parseLong(s);
                        if (subDeptIdList.contains(deptId1)) {
                            flag = true;
                        }
                    }
                }
            }

            if (!flag) {
                iterator.remove();
                continue;
            }


            Date endTime = sysTestEntity.getEndTime();
            if (!DateUtil.parseYears(endTime)) {
                iterator.remove();
                continue;
            }

            List<SysAnswerEntity> sysAnswerEntities = sysAnswerDao.selectList(new QueryWrapper<SysAnswerEntity>().eq("user_id", ShiroUtils.getUserId())
                    .eq("test_id", sysTestEntity.getTestId()));
            if (sysAnswerEntities != null && sysAnswerEntities.size() > 0) {
                String testType = sysTestEntity.getTestType();
                if (testType != null && !testType.contains("1")) {
                    iterator.remove();
                    continue;
                }
            }

            Date startTime = sysTestEntity.getStartTime();
            if (DateUtil.parseYears(startTime)) {
                sysTestEntity.setStatus(1);
            } else {
                sysTestEntity.setStatus(0);
            }
        }


        IPage<SysTestEntity> page = new Page<>();
        long total = sysTestEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysTestEntity> subList = null;
        if (total < toIndex) {
            subList = sysTestEntities.subList(index, (int) (total));
        } else {
            subList = sysTestEntities.subList(index, toIndex);
        }
        page.setRecords(subList);
        page.setTotal(total);
        page.setPages(total / size);
        page.setCurrent(current);
        page.setSize(size);
        return new PageUtils(page);
    }

}
