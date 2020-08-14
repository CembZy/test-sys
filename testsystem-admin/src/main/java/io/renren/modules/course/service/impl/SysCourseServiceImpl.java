package io.renren.modules.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.course.dao.SysCourseDao;
import io.renren.modules.course.entity.SysCourseEntity;
import io.renren.modules.course.service.SysCourseService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("sysCourseService")
public class SysCourseServiceImpl extends ServiceImpl<SysCourseDao, SysCourseEntity> implements SysCourseService {

    @Autowired
    private SysCourseDao sysCourseDao;
    @Resource
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysCourseEntity> page = this.page(
                new Query<SysCourseEntity>().getPage(params),
                new QueryWrapper<SysCourseEntity>()
        );
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList = new ArrayList<>();
        subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        subDeptIdList.addAll(sysDeptService.getSubDeptIdList(deptId));
        subDeptIdList.add(0L);
        Iterator<SysCourseEntity> iterator = page.getRecords().iterator();
        refresh(iterator, deptId, subDeptIdList);
        return new PageUtils(page);
    }


    @Override
    public List<SysCourseEntity> queryList(Map<String, Object> params) {
        List<SysCourseEntity> sysCourseEntityList = baseMapper.queryList(params);
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList = new ArrayList<>();
        subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
        subDeptIdList.addAll(subDeptIdList1);
        subDeptIdList.add(0L);
        Iterator<SysCourseEntity> iterator = sysCourseEntityList.iterator();
        refresh(iterator, deptId, subDeptIdList);
        return sysCourseEntityList;
    }


    private void refresh(Iterator<SysCourseEntity> iterator, Long deptId, List<Long> subDeptIdList) {
        while (iterator.hasNext()) {
            SysCourseEntity sysCourseEntity = iterator.next();
            SysUserEntity sysUserEntity = sysUserDao.selectById(sysCourseEntity.getAdmin());
            if (sysUserEntity != null && !subDeptIdList.contains(sysUserEntity.getDeptId()) && deptId != sysUserEntity.getDeptId()) {
                iterator.remove();
            }

            SysUserEntity sysUserEntity1 = sysUserDao.selectById(Long.parseLong(sysCourseEntity.getAdmin()));
            if (sysUserEntity1 != null) {
                sysCourseEntity.setAdmin(sysUserEntity1.getName());
            }
        }
    }


    @Override
    public List<Long> queryCourseIdList(Long parentId) {
        return baseMapper.queryCourseIdList(parentId);
    }

    @Override
    public List<Long> getSubCourseIdList(Long courseId) {
        //部门及子部门ID列表
        List<Long> courseIdList = new ArrayList<>();

        //获取子部门ID
        List<Long> subIdList = queryCourseIdList(courseId);
        getCourseTreeList(subIdList, courseIdList);

        return courseIdList;
    }

    /**
     * 递归
     */
    private void getCourseTreeList(List<Long> subIdList, List<Long> courseIdList) {
        for (Long courseId : subIdList) {
            List<Long> list = queryCourseIdList(courseId);
            if (list.size() > 0) {
                getCourseTreeList(list, courseIdList);
            }
            courseIdList.add(courseId);
        }
    }


    @Override
    public List<Long> queryParentCourseIdList(Long chiledId) {
        ArrayList<Long> parentIds = new ArrayList<>();
        getCourseTreeList(chiledId, parentIds);
        return parentIds;
    }

    private void getCourseTreeList(Long chiledId, List<Long> parentIds) {
        SysCourseEntity sysCourseEntity = sysCourseDao.selectById(chiledId);
        if (sysCourseEntity != null) {
            Long parentId = sysCourseEntity.getParentId();
            parentIds.add(chiledId);
            if (sysCourseEntity.getParentId() != 0) {
                getCourseTreeList(parentId, parentIds);
            }
        }
    }


}
