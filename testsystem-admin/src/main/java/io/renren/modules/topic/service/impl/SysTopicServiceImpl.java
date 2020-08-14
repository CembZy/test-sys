package io.renren.modules.topic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.topic.dao.SysTopicDao;
import io.renren.modules.topic.entity.SysTopicEntity;
import io.renren.modules.topic.service.SysTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("sysTopicService")
public class SysTopicServiceImpl extends ServiceImpl<SysTopicDao, SysTopicEntity> implements SysTopicService {
    @Autowired
    private SysUserDao sysUserService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysTopicDao sysTopicDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String name = (String) params.get("name");
        String type = (String) params.get("type");
        QueryWrapper<SysTopicEntity> queryWrapper = new QueryWrapper<>();
        if ((type != null && !"".equals(type)) && (name != null && !"".equals(name))) {
            queryWrapper.eq("type", type)
                    .eq("name2", name);
        } else if (type != null && !"".equals(type)) {
            queryWrapper.eq("type", type);
        } else if (name != null && !"".equals(name)) {
            queryWrapper
                    .eq("name2", name);
        }
        queryWrapper.orderByDesc("create_time");
        List<SysTopicEntity> sysTopicEntities = sysTopicDao.selectList(queryWrapper);
        Iterator<SysTopicEntity> iterator = sysTopicEntities.iterator();
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList = new ArrayList<>();
        subDeptIdList = sysDeptService.queryParentDetpIdList(deptId);
        List<Long> subDeptIdList1 = sysDeptService.getSubDeptIdList(deptId);
        subDeptIdList.addAll(subDeptIdList1);
        subDeptIdList.add(0L);
        while (iterator.hasNext()) {
            SysTopicEntity sysTopicEntity = iterator.next();

            SysUserEntity sysUserEntity = sysUserService.selectById(sysTopicEntity.getAdmin());
            if (!subDeptIdList.contains(sysUserEntity.getDeptId()) && deptId != sysUserEntity.getDeptId()) {
                iterator.remove();
            }
            SysUserEntity sysUserEntity1 = sysUserService.selectById(Long.parseLong(sysTopicEntity.getAdmin()));
            sysTopicEntity.setAdmin(sysUserEntity1.getName());
        }

        IPage<SysTopicEntity> page = new Page<>();
        long total = sysTopicEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysTopicEntity> subList = null;
        if (total < toIndex) {
            subList = sysTopicEntities.subList(index, (int) (total));
        } else {
            subList = sysTopicEntities.subList(index, toIndex);
        }
        page.setRecords(subList);
        page.setTotal(total);
        page.setPages(total / size);
        page.setCurrent(current);
        page.setSize(size);


        return new PageUtils(page);
    }
}
