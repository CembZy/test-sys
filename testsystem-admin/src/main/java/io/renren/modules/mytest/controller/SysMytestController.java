package io.renren.modules.mytest.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.ExportExcel;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.answer.dao.SysAnswerDao;
import io.renren.modules.answer.entity.SysAnswerEntity;
import io.renren.modules.jungle.dao.SysJungleDao;
import io.renren.modules.jungle.entity.SysJungleEntity;
import io.renren.modules.mytest.dao.SysMytestDao;
import io.renren.modules.mytest.entity.SysMytestEntity;
import io.renren.modules.mytest.service.SysMytestService;
import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.test.dao.SysTestDao;
import io.renren.modules.test.entity.SysTestEntity;
import io.renren.modules.title.dao.SysTitleDao;
import io.renren.modules.title.entity.SysTitleEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;


/**
 * 学生考试结果表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-03 09:23:55
 */
@RestController
@RequestMapping("sys/sysmytest")
public class SysMytestController {
    @Autowired
    private SysMytestService sysMytestService;
    @Autowired
    private SysTitleDao sysTitleDao;
    @Autowired
    private SysTestDao sysTestDao;
    @Autowired
    private SysJungleDao sysJungleDao;
    @Autowired
    private SysAnswerDao sysAnswerDao;
    @Autowired
    private SysMytestDao sysMytestDao;


    @RequestMapping("/titles2/{mytestId}")
    @RequiresPermissions("sys:sysmytest:mytestId")
    public R titles2(@PathVariable("mytestId") Long mytestId) {
        SysMytestEntity sysMytestEntity = sysMytestDao.selectById(mytestId);
        SysTestEntity sysTestEntity = sysTestDao.selectById(sysMytestEntity.getTestId());
        Long testId = sysTestEntity.getTestId();

        Long userId = sysMytestEntity.getUserId();
        Long orders = sysMytestEntity.getOrders();

        List<SysSubjectEntity> subjects = sysTitleDao.getSubjects2(testId, userId, orders);
        Map<String, List<SysSubjectEntity>> map = new HashMap<>();
        Map<Long, List<SysJungleEntity>> jungles = new HashMap<>();
        Set<Long> set = new HashSet();
        Map<Long, String> map1 = new HashMap<>();
        if (subjects != null) {
            Iterator<SysSubjectEntity> iterator = subjects.iterator();
            while (iterator.hasNext()) {
                SysSubjectEntity next = iterator.next();
                String topicName = next.getTopicName();
                Long topicId = next.getTopicId();
                set.add(topicId);
                map1.put(topicId, topicName);

                List<SysAnswerEntity> sysAnswerEntities = sysAnswerDao.selectList(new QueryWrapper<SysAnswerEntity>().
                        eq("user_id", userId).eq("test_id", testId).eq("subject_id", next.getSubjectId()).eq("orders", orders)
                        .isNotNull("content"));

                if (sysAnswerEntities == null || sysAnswerEntities.size() == 0) {
                    continue;
                }

                SysAnswerEntity sysAnswerEntity = sysAnswerEntities.get(0);
                next.setPingfun(sysAnswerEntity.getPrice());
                String content = sysAnswerEntity.getContent() != null ? sysAnswerEntity.getContent() : "";
                next.setAnswers(content);
                next.setAnswerId(sysAnswerEntity.getAnswerId());
                List<SysSubjectEntity> sysSubjectEntities = map.get(topicName);
                if (sysSubjectEntities != null) {
                    sysSubjectEntities.add(next);
                    map.put(topicName, sysSubjectEntities);
                } else {
                    sysSubjectEntities = new ArrayList<>();
                    sysSubjectEntities.add(next);
                    map.put(topicName, sysSubjectEntities);
                }
            }
        }
        for (Map.Entry<String, List<SysSubjectEntity>> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.contains("单") || key.contains("多")) {
                List<SysSubjectEntity> value = entry.getValue();
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
//            Long count = sysTitleDao.getCount(testId, str, ShiroUtils.getUserId());
            String key = map1.get(str);
            List<SysSubjectEntity> sysSubjectEntities = map.get(key);
            Long num = 0L;
            if (sysSubjectEntities != null && sysSubjectEntities.size() > 0) {
                for (int i = 0; i < sysSubjectEntities.size(); i++) {
                    num += sysSubjectEntities.get(i).getPrice();
                }
            }
            counts.put(key, num);
        }
        return R.ok().put("counts", counts).put("subjects", map).put("jungles", jungles).put("price", sysMytestEntity.getPrice())
                .put("test", sysTestEntity).put("size", subjects != null ? subjects.size() : 0);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysmytest:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysMytestService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/list3")
    @RequiresPermissions("sys:sysmytest:list")
    public R list3(@RequestParam Map<String, Object> params) {
        PageUtils page = sysMytestService.queryPage1(params);

        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/list2")
    @RequiresPermissions("sys:sysmytest:list")
    public R list2(@RequestParam Map<String, Object> params) {
        PageUtils page = sysMytestService.queryPage2(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{mytestId}")
    @RequiresPermissions("sys:sysmytest:info")
    public R info(@PathVariable("mytestId") Long mytestId) {
        SysMytestEntity sysMytest = sysMytestService.getById(mytestId);

        return R.ok().put("sysMytest", sysMytest);
    }

    @RequestMapping("/save")
    @RequiresPermissions("sys:sysmytest:save")
    public R save(@RequestBody SysMytestEntity sysMytest) {

        List<SysAnswerEntity> answerEntities = sysMytest.getAnswerEntities();
        if (answerEntities == null || answerEntities.size() == 0) {
            return R.ok();
        }

        int num = 0;
        for (SysAnswerEntity sysAnswerEntity : answerEntities) {
            Long price = sysAnswerEntity.getPrice();
            num += price;
            sysAnswerDao.updat(sysAnswerEntity.getAnswerId(), price);
        }
        String testId = sysMytest.getTestId();
        Integer price = sysTestDao.selectById(testId).getInprice();
        if (num >= price) {
            sysMytestDao.updat(sysMytest.getMytestId(), num, 1, "已通过");
        } else {
            sysMytestDao.updat(sysMytest.getMytestId(), num, 1, "未通过");
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysmytest:update")
    public R update(@RequestBody SysMytestEntity sysMytest) {
        ValidatorUtils.validateEntity(sysMytest);
        sysMytestService.updateById(sysMytest);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysmytest:delete")
    public R delete(@RequestBody Long[] mytestIds) {
        sysMytestService.removeByIds(Arrays.asList(mytestIds));


        return R.ok();
    }

    @RequestMapping("/delete1")
    @RequiresPermissions("sys:sysmytest:delete")
    public R delete1(@RequestBody Long[] mytestIds) {

        if (mytestIds != null) {
            for (Long id : mytestIds) {
                SysMytestEntity sysMytestEntity = sysMytestDao.selectById(id);
                sysMytestDao.delete(new QueryWrapper<SysMytestEntity>().eq("test_id", sysMytestEntity.getTestId())
                        .eq("orders", sysMytestEntity.getOrders()));

                sysAnswerDao.delete(new QueryWrapper<SysAnswerEntity>().eq("test_id", sysMytestEntity.getTestId())
                        .eq("orders", sysMytestEntity.getOrders()));

                sysTitleDao.delete(new QueryWrapper<SysTitleEntity>().eq("test_id", sysMytestEntity.getTestId())
                        .eq("orders", sysMytestEntity.getOrders()));
            }
        }
        return R.ok();
    }


    /**
     * 导出用户
     *
     * @return
     */
    @SysLog("导出用户")
    @RequestMapping("/outport")
    @RequiresPermissions("sys:sysmytest:delete")
    public void outport(@RequestParam("mytestIds") String mytestIds, HttpServletResponse response) throws Exception {

        List<SysMytestEntity> sysMytestEntities = new ArrayList<>();
        String[] split = mytestIds.split(",");
        for (String id : split) {
            if (!"".equals(id)) {
                SysMytestEntity sysMytestEntity = sysMytestDao.selectById(Long.parseLong(id));
                sysMytestEntities.add(sysMytestEntity);
            }
        }

        // 定义表的标题
        String title = "成绩表";
        //定义表的列名
        String[] rowsName = new String[]{"答题人", "试卷名称", "试卷类型", "试卷得分", "是否通过",
                "交卷时间"};
        //定义表的内容
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        int length = 6;
        for (int i = 0; i < sysMytestEntities.size(); i++) {
            SysMytestEntity per = sysMytestEntities.get(i);
            objs = new Object[length];
            objs[0] = per.getUserName() == null ? "" : per.getUserName();
            objs[1] = per.getTestName() == null ? "" : per.getTestName();
            objs[2] = per.getType() == null ? "" : per.getType();
            objs[3] = per.getPrice() == null ? "" : per.getPrice();
            objs[4] = per.getInprice() == null ? "" : per.getInprice();
            objs[5] = per.getDate() == null ? "" : per.getDate();
            dataList.add(objs);
        }

        // 创建ExportExcel对象
        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        // 输出Excel文件
        OutputStream output = null;

        try {
            output = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("成绩表.xls", "utf-8"));
            response.setContentType("application/vnd.ms-excel");
            ex.export(output);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
