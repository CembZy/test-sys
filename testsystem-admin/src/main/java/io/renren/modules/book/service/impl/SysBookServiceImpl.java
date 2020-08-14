package io.renren.modules.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.book.dao.SysBookDao;
import io.renren.modules.book.entity.SysBookEntity;
import io.renren.modules.book.service.SysBookService;
import io.renren.modules.course.dao.SysCourseDao;
import io.renren.modules.course.service.SysCourseService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("sysBookService")
public class SysBookServiceImpl extends ServiceImpl<SysBookDao, SysBookEntity> implements SysBookService {
    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysBookDao sysBookDao;
    @Autowired
    private SysCourseService sysCourseService;
    @Autowired
    private SysCourseDao sysCourseDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Long courseId = (params.get("courseId") == null || "".equals(params.get("courseId"))) ? -1 : Long.parseLong((String) params.get("courseId"));

        QueryWrapper<SysBookEntity> queryWrapper = new QueryWrapper<>();
        if ((name != null && !"".equals(name)) && courseId != -1) {
            queryWrapper.eq("name", name).eq("course_id", courseId);
        } else if (name != null && !"".equals(name)) {
            queryWrapper.eq("name", name);
        }
        List<SysBookEntity> sysBookEntities = sysBookDao.selectList(queryWrapper);
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        subDeptIdList.addAll(sysDeptService.getSubDeptIdList(deptId));
        subDeptIdList.add(0L);
        Iterator<SysBookEntity> iterator = sysBookEntities.iterator();
        while (iterator.hasNext()) {
            SysBookEntity next = iterator.next();
            SysUserEntity sysUserEntity = sysUserDao.selectById(next.getAdmin());
            if (sysUserEntity != null && !subDeptIdList.contains(sysUserEntity.getDeptId()) && deptId != sysUserEntity.getDeptId()) {
                iterator.remove();
                continue;
            }

            List<Long> longs = sysCourseService.queryParentCourseIdList(next.getCourseId());
            if (longs.size() == 0) {
                continue;
            }
            if ((courseId != -1)) {
                if (!(longs.contains(courseId))) {
                    iterator.remove();
                    continue;
                }
            }

            int size = longs.size();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = size - 1; i >= 0; i--) {
                stringBuilder.append(sysCourseDao.selectById(longs.get(i)).getName());
                if (i != 0) {
                    stringBuilder.append("-");
                }
            }
            String courseNameT = stringBuilder.toString();
            next.setCourseName(courseNameT);
        }

        IPage<SysBookEntity> page = new Page<>();
        long total = sysBookEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysBookEntity> subList = null;
        if (total < toIndex) {
            subList = sysBookEntities.subList(index, (int) (total));
        } else {
            subList = sysBookEntities.subList(index, toIndex);
        }
        page.setRecords(subList);
        page.setTotal(total);
        page.setPages(total / size);
        page.setCurrent(current);
        page.setSize(size);
        return new PageUtils(page);
    }

}
