package io.renren.modules.mytest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.mytest.dao.SysMytestDao;
import io.renren.modules.mytest.entity.SysMytestEntity;
import io.renren.modules.mytest.service.SysMytestService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.test.dao.SysTestDao;
import io.renren.modules.test.entity.SysTestEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("sysMytestService")
public class SysMytestServiceImpl extends ServiceImpl<SysMytestDao, SysMytestEntity> implements SysMytestService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysTestDao sysTestDao;
    @Autowired
    private SysMytestDao sysMytestDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long deptId = ShiroUtils.getUserEntity().getDeptId();
        List<SysMytestEntity> sysMytestEntities = sysMytestDao.getInfos(null, (String) params.get("testName"));
        List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(deptId);
        subDeptIdList.add(0L);
        Iterator<SysMytestEntity> iterator = sysMytestEntities.iterator();
        while (iterator.hasNext()) {
            SysMytestEntity sysMytestEntity = iterator.next();
            SysUserEntity sysUserEntity2 = sysUserDao.selectById(sysMytestEntity.getUserId());
            if (sysUserEntity2 == null) {
                iterator.remove();
                continue;
            }
            SysUserEntity sysUserEntity = sysUserDao.selectById(sysMytestEntity.getAdminId());
            if (sysUserEntity != null && !subDeptIdList.contains(sysUserEntity.getDeptId()) && deptId != sysUserEntity.getDeptId()) {
                iterator.remove();
                continue;
            }

            DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String price1 = sysMytestEntity.getPrice1();
            if (!StringUtils.isBlank(price1)) {
                String p = decimalFormat.format(Float.parseFloat(price1));//format 返回的是字符串
                sysMytestEntity.setPrice1(p);
            }
        }

        IPage<SysMytestEntity> page = new Page<>();
        long total = sysMytestEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysMytestEntity> subList = null;
        if (total < toIndex) {
            subList = sysMytestEntities.subList(index, (int) (total));
        } else {
            subList = sysMytestEntities.subList(index, toIndex);
        }
        page.setRecords(subList);
        page.setTotal(total);
        page.setPages(total / size);
        page.setCurrent(current);
        page.setSize(size);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage1(Map<String, Object> params) {
        Long mytestId = Long.parseLong((String) params.get("mytestId"));
        SysMytestEntity sysMytestEntity1 = sysMytestDao.selectById(mytestId);
        String userName = (String) params.get("userName");
        Long deptId = (params.get("deptId") != null && !"".equals(params.get("deptId")) ? Long.parseLong((String) params.get("deptId")) : -1);
        Long deptId2 = ShiroUtils.getUserEntity().getDeptId();
        List<Long> subDeptIdList2 = sysDeptService.getSubDeptIdList(deptId2);
        subDeptIdList2.add(0L);

        QueryWrapper<SysMytestEntity> sysMytestEntityQueryWrapper = new QueryWrapper<>();
        if (userName != null && !"".equals(userName)) {
            sysMytestEntityQueryWrapper.eq("user_name", userName);
        }
        sysMytestEntityQueryWrapper.eq("test_id", sysMytestEntity1.getTestId())
                .eq("orders", sysMytestEntity1.getOrders()).orderByDesc("date");

        List<SysMytestEntity> sysMytestEntities = sysMytestDao.selectList(sysMytestEntityQueryWrapper);
        Iterator<SysMytestEntity> iterator = sysMytestEntities.iterator();
        while (iterator.hasNext()) {
            SysMytestEntity sysMytestEntity = iterator.next();
            SysUserEntity sysUserEntity2 = sysUserDao.selectById(sysMytestEntity.getUserId());
            if (sysUserEntity2 == null) {
                iterator.remove();
                continue;
            }
            SysUserEntity sysUserEntity = sysUserDao.selectById(sysMytestEntity.getAdminId());
            if (sysUserEntity != null && !subDeptIdList2.contains(sysUserEntity.getDeptId()) && deptId2 != sysUserEntity.getDeptId()) {
                iterator.remove();
                continue;
            }

            if (deptId != -1) {
                List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(deptId);
                Long deptId1 = sysUserEntity2.getDeptId();
                if (!subDeptIdList.contains(deptId1) && deptId1 != deptId) {
                    iterator.remove();
                    continue;
                }
            }
        }

        IPage<SysMytestEntity> page = new Page<>();
        long total = sysMytestEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysMytestEntity> subList = null;
        if (total < toIndex) {
            subList = sysMytestEntities.subList(index, (int) (total));
        } else {
            subList = sysMytestEntities.subList(index, toIndex);
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
        String testName = (String) params.get("testName");
        String type = (String) params.get("type");
        String inprice = (String) params.get("inprice");
        IPage<SysMytestEntity> page = null;
        Long userId = ShiroUtils.getUserId();
        if (userId == 1) {
            if ((testName != null && !"".equals(testName)) && (inprice != null && !"".equals(inprice))) {
                page = this.page(
                        new Query<SysMytestEntity>().getPage(params),
                        new QueryWrapper<SysMytestEntity>()
                                .eq("test_name", testName).eq("type", type).eq("inprice", inprice).orderByDesc("date")
                );
            } else if (testName != null && !"".equals(testName)) {
                page = this.page(
                        new Query<SysMytestEntity>().getPage(params),
                        new QueryWrapper<SysMytestEntity>().eq("type", type).eq("test_name", testName).orderByDesc("date")
                );
            } else if (inprice != null && !"".equals(inprice)) {
                page = this.page(
                        new Query<SysMytestEntity>().getPage(params),
                        new QueryWrapper<SysMytestEntity>().eq("type", type).eq("inprice", inprice).orderByDesc("date")
                );
            } else {
                page = this.page(
                        new Query<SysMytestEntity>().getPage(params),
                        new QueryWrapper<SysMytestEntity>().eq("type", type).orderByDesc("date")
                );
            }
        } else {
            if ((testName != null && !"".equals(testName)) && (inprice != null && !"".equals(inprice))) {
                page = this.page(
                        new Query<SysMytestEntity>().getPage(params),
                        new QueryWrapper<SysMytestEntity>()
                                .eq("test_name", testName).eq("user_id", userId).eq("type", type).eq("inprice", inprice).orderByDesc("date")
                );
            } else if (testName != null && !"".equals(testName)) {
                page = this.page(
                        new Query<SysMytestEntity>().getPage(params),
                        new QueryWrapper<SysMytestEntity>().eq("user_id", userId).eq("type", type).eq("test_name", testName).orderByDesc("date")
                );
            } else if (inprice != null && !"".equals(inprice)) {
                page = this.page(
                        new Query<SysMytestEntity>().getPage(params),
                        new QueryWrapper<SysMytestEntity>().eq("user_id", userId).eq("type", type).eq("inprice", inprice).orderByDesc("date")
                );
            } else {
                page = this.page(
                        new Query<SysMytestEntity>().getPage(params),
                        new QueryWrapper<SysMytestEntity>().eq("user_id", userId).eq("type", type).orderByDesc("date")
                );
            }
        }
        Iterator<SysMytestEntity> iterator = page.getRecords().iterator();
        while (iterator.hasNext()) {
            SysMytestEntity sysMytestEntity = iterator.next();
            SysTestEntity sysTestEntity = sysTestDao.selectById(sysMytestEntity.getTestId());
            String testType = sysTestEntity.getTestType();
            if (testType == null || !testType.contains("2") || "".equals(testType)) {
                sysMytestEntity.setPrice("不可见");
            }
        }

        return new PageUtils(page);
    }

}
