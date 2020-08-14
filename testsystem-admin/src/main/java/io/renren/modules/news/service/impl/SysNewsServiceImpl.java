package io.renren.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.news.dao.SysNewsDao;
import io.renren.modules.news.entity.SysNewsEntity;
import io.renren.modules.news.service.SysNewsService;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service("sysNewsService")
public class SysNewsServiceImpl extends ServiceImpl<SysNewsDao, SysNewsEntity> implements SysNewsService {


    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysDeptDao sysDeptDao;

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private SysNewsDao sysNewsDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        int type = ShiroUtils.getUserEntity().getType();
        List<Long> subDeptIdList = new ArrayList<>();
        List<Long> subDeptIdList2 = null;
        if (type == 1) {
            subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
            List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
            subDeptIdList.addAll(subDeptIdList1);
            if (deptId != 0L) {
            }
            subDeptIdList.add(0L);
        } else {
            subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
            subDeptIdList.add(0L);
            subDeptIdList2 = new ArrayList<>();
            subDeptIdList2.addAll(subDeptIdList);
        }

        String title = (String) params.get("title");
        QueryWrapper<SysNewsEntity> queryWrapper = new QueryWrapper<>();
        if (title != null && !"".equals(title)) {
            queryWrapper.eq("title", title);
        }
        queryWrapper.orderByDesc("create_time");
        List<SysNewsEntity> sysNewsEntities = sysNewsDao.selectList(queryWrapper);
        Iterator<SysNewsEntity> iterator = sysNewsEntities.iterator();
        while (iterator.hasNext()) {
            SysNewsEntity next = iterator.next();
            String[] deptIdT = next.getDeptId().split(",");
            List<Long> strsToList2 = new ArrayList<>();
            for (String string : deptIdT) {
                strsToList2.add(Long.parseLong(string));
            }
            if (type == 1) {
                SysUserEntity sysUserEntity = sysUserDao.selectById(next.getAdmin());
                if (sysUserEntity != null && !subDeptIdList.contains(sysUserEntity.getDeptId()) && deptId != sysUserEntity.getDeptId()) {
                    iterator.remove();
                    continue;
                }

            } else {
                if (subDeptIdList == null) {
                    iterator.remove();
                    continue;
                }
                subDeptIdList.retainAll(strsToList2);
                if (subDeptIdList.size() == 0) {
                    iterator.remove();
                    subDeptIdList.addAll(subDeptIdList2);
                    continue;
                }
                subDeptIdList.addAll(subDeptIdList2);
            }
        }
        IPage<SysNewsEntity> page = new Page<>();
        long total = sysNewsEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysNewsEntity> subList = null;
        if (total < toIndex) {
            subList = sysNewsEntities.subList(index, (int) (total));
        } else {
            subList = sysNewsEntities.subList(index, toIndex);
        }
        page.setRecords(subList);
        page.setTotal(total);
        page.setPages(total / size);
        page.setCurrent(current);
        page.setSize(size);

        return new PageUtils(page);
    }

}
