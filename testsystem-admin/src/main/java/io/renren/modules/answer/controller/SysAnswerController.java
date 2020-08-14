package io.renren.modules.answer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.DateUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.UUIDBuild;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.answer.dao.SysAnswerDao;
import io.renren.modules.answer.entity.SysAnswerEntity;
import io.renren.modules.answer.service.SysAnswerService;
import io.renren.modules.jungle.dao.SysJungleDao;
import io.renren.modules.jungle.entity.SysJungleEntity;
import io.renren.modules.mytest.entity.SysMytestEntity;
import io.renren.modules.mytest.service.SysMytestService;
import io.renren.modules.subject.dao.SysSubjectDao;
import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.test.dao.SysTestDao;
import io.renren.modules.test.entity.SysTestEntity;
import io.renren.modules.topic.dao.SysTopicDao;
import io.renren.modules.topic.entity.SysTopicEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 做题答案表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-03 09:21:31
 */
@RestController
@RequestMapping("sys/sysanswer")
public class SysAnswerController extends AbstractController {
    @Autowired
    private SysAnswerService sysAnswerService;
    @Autowired
    private SysAnswerDao sysAnswerDao;
    @Autowired
    private SysSubjectDao sysSubjectDao;
    @Autowired
    private SysTopicDao sysTopicDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMytestService sysMytestService;
    @Autowired
    private SysTestDao sysTestDao;
    @Autowired
    private SysJungleDao sysJungleDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysanswer:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysAnswerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{answerId}")
    @RequiresPermissions("sys:sysanswer:info")
    public R info(@PathVariable("answerId") Long answerId) {
        SysAnswerEntity sysAnswer = sysAnswerService.getById(answerId);

        return R.ok().put("sysAnswer", sysAnswer);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysanswer:save")
    public R save(@RequestBody SysAnswerEntity sysAnswerEntity) {
        String content1 = sysAnswerEntity.getContent();
        sysAnswerEntity.setShanchu(0);
        if (sysAnswerEntity.getAnswerId() != null && !"".equals(sysAnswerEntity.getAnswerId())) {
            String content = sysAnswerDao.selectById(sysAnswerEntity.getAnswerId()).getContent();
            if (content != null && content.equals(content1)) {
                return R.ok().put("id", sysAnswerEntity.getAnswerId()).put("typet3", 2);
            } else {
                int typet = (content != null &&
                        !"".equals(content)) ? 1 : 2;
                SysSubjectEntity sysSubjectEntity = sysSubjectDao.selectById(sysAnswerEntity.getSubjectId());
                if (sysSubjectEntity != null) {
                    SysTopicEntity sysTopicEntity = sysTopicDao.selectById(sysSubjectEntity.getTopicId());
                    if (sysTopicEntity != null) {
                        if (sysTopicEntity.getName().contains("单")) {
                            String answertt = sysSubjectEntity.getAnswertt();
                            if (content1 != null && answertt != null && !"".equals(content1) && answertt.contains(content1)) {
                                sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                                sysAnswerEntity.setFalg(1);
                            } else {
                                sysAnswerEntity.setPrice(0L);
                                sysAnswerEntity.setFalg(0);
                            }
                        } else if (sysTopicEntity.getName().contains("多")) {
                            String answertt = sysSubjectEntity.getAnswertt();
                            if (content1 != null && answertt != null && !"".equals(content1) && answertt.equals(trimFirstAndLastChar(content1, ","))) {
                                sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                                sysAnswerEntity.setFalg(1);
                            } else {
                                sysAnswerEntity.setPrice(0L);
                                sysAnswerEntity.setFalg(0);
                            }
                        } else if (sysTopicEntity.getName().contains("判断")) {
                            String answert = sysSubjectEntity.getAnswert();
                            if (content1 != null && answert != null && !"".equals(content1) && answert.equals(trimFirstAndLastChar(content1, ","))) {
                                sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                                sysAnswerEntity.setFalg(1);
                            } else {
                                sysAnswerEntity.setPrice(0L);
                                sysAnswerEntity.setFalg(0);
                            }
                        } else {
                            sysAnswerEntity.setPrice(0L);
                            sysAnswerEntity.setFalg(0);
                        }
                    }
                }
                sysAnswerService.updateById(sysAnswerEntity);
                return R.ok().put("id", sysAnswerEntity.getAnswerId()).put("type", 1).put("typet", typet);
            }
        } else {
            Long userId = getUserId();
            String uuid = UUIDBuild.getUUID();
            sysAnswerEntity.setUuid(uuid);
            sysAnswerEntity.setUserId(userId);
            sysAnswerEntity.setCreateTime(DateUtil.getDate());

            SysSubjectEntity sysSubjectEntity = sysSubjectDao.selectById(sysAnswerEntity.getSubjectId());
            if (sysSubjectEntity != null) {
                SysTopicEntity sysTopicEntity = sysTopicDao.selectById(sysSubjectEntity.getTopicId());
                if (sysTopicEntity != null) {
                    if (sysTopicEntity.getName().contains("单")) {
                        String answertt = sysSubjectEntity.getAnswertt();
                        if (content1 != null && answertt != null && !"".equals(content1) && answertt.contains(content1)) {
                            sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                            sysAnswerEntity.setFalg(1);
                        } else {
                            sysAnswerEntity.setFalg(0);
                            sysAnswerEntity.setPrice(0L);
                        }
                    } else if (sysTopicEntity.getName().contains("多")) {
                        String answertt = sysSubjectEntity.getAnswertt();
                        if (content1 != null && answertt != null && !"".equals(content1) && answertt.equals(trimFirstAndLastChar(content1, ","))) {
                            sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                            sysAnswerEntity.setFalg(1);
                        } else {
                            sysAnswerEntity.setFalg(0);
                            sysAnswerEntity.setPrice(0L);
                        }
                    } else if (sysTopicEntity.getName().contains("判断")) {
                        String answert = sysSubjectEntity.getAnswert();
                        if (content1 != null && answert != null && !"".equals(content1) && answert.equals(trimFirstAndLastChar(content1, ","))) {
                            sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                            sysAnswerEntity.setFalg(1);
                        } else {
                            sysAnswerEntity.setFalg(0);
                            sysAnswerEntity.setPrice(0L);
                        }
                    } else {
                        sysAnswerEntity.setFalg(0);
                        sysAnswerEntity.setPrice(0L);
                    }
                }
            }
            sysAnswerService.save(sysAnswerEntity);
            return R.ok().put("id", sysAnswerDao.selectOne(new QueryWrapper<SysAnswerEntity>().eq("uuid", uuid)).getAnswerId()).put("type", 2)
                    .put("typet3", 1);
        }
    }


    @RequestMapping("/save2")
    @RequiresPermissions("sys:sysanswer:save")
    public R save2(@RequestBody SysAnswerEntity sysAnswerEntity) {
        String content1 = sysAnswerEntity.getContent();
        sysAnswerEntity.setShanchu(0);
        Long userId = getUserId();
        if (sysAnswerEntity.getAnswerId() != null && !"".equals(sysAnswerEntity.getAnswerId())) {
            String content = sysAnswerDao.selectById(sysAnswerEntity.getAnswerId()).getContent();
            if (content == null || !content.equals(content1)) {
                SysSubjectEntity sysSubjectEntity = sysSubjectDao.selectById(sysAnswerEntity.getSubjectId());
                if (sysSubjectEntity != null) {
                    SysTopicEntity sysTopicEntity = sysTopicDao.selectById(sysSubjectEntity.getTopicId());
                    if (sysTopicEntity != null) {
                        if (sysTopicEntity.getName().contains("单")) {
                            String answertt = sysSubjectEntity.getAnswertt();
                            if (content1 != null && answertt != null && !"".equals(content1) && answertt.contains(content1)) {
                                sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                                sysAnswerEntity.setFalg(1);
                            } else {
                                sysAnswerEntity.setPrice(0L);
                                sysAnswerEntity.setFalg(0);
                            }
                        } else if (sysTopicEntity.getName().contains("多")) {
                            String answertt = sysSubjectEntity.getAnswertt();
                            if (content1 != null && answertt != null && !"".equals(content1) && answertt.equals(trimFirstAndLastChar(content1, ","))) {
                                sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                                sysAnswerEntity.setFalg(1);
                            } else {
                                sysAnswerEntity.setPrice(0L);
                                sysAnswerEntity.setFalg(0);
                            }
                        } else if (sysTopicEntity.getName().contains("判断")) {
                            String answert = sysSubjectEntity.getAnswert();
                            if (content1 != null && answert != null && !"".equals(content1) && answert.equals(trimFirstAndLastChar(content1, ","))) {
                                sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                                sysAnswerEntity.setFalg(1);
                            } else {
                                sysAnswerEntity.setPrice(0L);
                                sysAnswerEntity.setFalg(0);
                            }
                        } else {
                            sysAnswerEntity.setPrice(0L);
                            sysAnswerEntity.setFalg(0);
                        }
                    }
                }
                sysAnswerService.updateById(sysAnswerEntity);
            }
        } else {
            String uuid = UUIDBuild.getUUID();
            sysAnswerEntity.setUuid(uuid);
            sysAnswerEntity.setUserId(userId);
            sysAnswerEntity.setCreateTime(DateUtil.getDate());

            SysSubjectEntity sysSubjectEntity = sysSubjectDao.selectById(sysAnswerEntity.getSubjectId());
            if (sysSubjectEntity != null) {
                SysTopicEntity sysTopicEntity = sysTopicDao.selectById(sysSubjectEntity.getTopicId());
                if (sysTopicEntity != null) {
                    if (sysTopicEntity.getName().contains("单")) {
                        String answertt = sysSubjectEntity.getAnswertt();
                        if (content1 != null && answertt != null && !"".equals(content1) && answertt.contains(content1)) {
                            sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                            sysAnswerEntity.setFalg(1);
                        } else {
                            sysAnswerEntity.setPrice(0L);
                            sysAnswerEntity.setFalg(0);
                        }
                    } else if (sysTopicEntity.getName().contains("多")) {
                        String answertt = sysSubjectEntity.getAnswertt();
                        if (content1 != null && answertt != null && "".equals(content1) && answertt.equals(trimFirstAndLastChar(content1, ","))) {
                            sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                            sysAnswerEntity.setFalg(1);
                        } else {
                            sysAnswerEntity.setPrice(0L);
                            sysAnswerEntity.setFalg(0);
                        }
                    } else if (sysTopicEntity.getName().contains("判断")) {
                        String answert = sysSubjectEntity.getAnswert();
                        if (content1 != null && answert != null && !"".equals(content1) && answert.equals(trimFirstAndLastChar(content1, ","))) {
                            sysAnswerEntity.setPrice(Long.valueOf(sysSubjectEntity.getPrice()));
                            sysAnswerEntity.setFalg(1);
                        } else {
                            sysAnswerEntity.setPrice(0L);
                            sysAnswerEntity.setFalg(0);
                        }
                    } else {
                        sysAnswerEntity.setPrice(0L);
                        sysAnswerEntity.setFalg(0);
                    }
                }
            }
            sysAnswerService.save(sysAnswerEntity);
        }
        String testId = sysAnswerEntity.getTestId();
        long orders = sysAnswerEntity.getOrders();
        SysTestEntity sysTestEntity = sysTestDao.selectById(testId);
        SysMytestEntity sysMytestEntity = new SysMytestEntity();
        sysMytestEntity.setTestId(String.valueOf(testId));
        sysMytestEntity.setUserId(getUserId());
        sysMytestEntity.setUserName(getUser().getName());
        sysMytestEntity.setAdminId(sysTestEntity.getAdminId());
        sysMytestEntity.setOrders(orders);
        Long price = sysAnswerDao.getPrice(userId, testId, orders);
        sysMytestEntity.setPrice(String.valueOf(price == null ? 0L : price));
        if (price >= sysTestEntity.getInprice()) {
            sysMytestEntity.setInprice("已通过");
        } else {
            sysMytestEntity.setInprice("未通过");
        }
        sysMytestEntity.setAdminName(sysUserDao.selectById(sysTestEntity.getAdminId()).getName());
        sysMytestEntity.setTestName(sysTestEntity.getName());
        sysMytestEntity.setPingjuan(1);
        sysMytestEntity.setDate(new Date());

        sysMytestEntity.setType(sysTestEntity.getType());
        sysMytestService.save(sysMytestEntity);

        return R.ok();
    }

    @RequestMapping("/cuotis")
    @RequiresPermissions("sys:sysanswer:cuotis")
    public R cuotis(@RequestParam Map<String, Object> params) {
        PageUtils page = sysAnswerService.queryPage2(params);

        return R.ok().put("page", page);
    }


    @RequestMapping("/cuotiInfo/{subjectId}")
    @RequiresPermissions("sys:sysanswer:cuotiInfo")
    public R cuotiInfo(@PathVariable("subjectId") Long subjectId) {
        SysSubjectEntity value = sysSubjectDao.getCuoti(subjectId);
        if (value.getTopicName().contains("单") || value.getTopicName().contains("多")) {
            List<SysJungleEntity> sysJungleEntities = sysJungleDao.selectList(new QueryWrapper<SysJungleEntity>().eq("subject_id", subjectId));
            value.setJugleList(sysJungleEntities);
        }

        return R.ok().put("subject", value);
    }


    /**
     * 去除首尾指定字符
     *
     * @param str     字符串
     * @param element 指定字符
     * @return
     */
    private String trimFirstAndLastChar(String str, String element) {
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do {
            if (str != null && !"".equals(str)) {
                int beginIndex = str.indexOf(element) == 0 ? 1 : 0;
                int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
                str = str.substring(beginIndex, endIndex);
                beginIndexFlag = (str.indexOf(element) == 0);
                endIndexFlag = (str.lastIndexOf(element) + 1 == str.length());
            }
        } while (beginIndexFlag || endIndexFlag);
        return str;
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysanswer:update")
    public R update(@RequestBody SysAnswerEntity sysAnswer) {
        ValidatorUtils.validateEntity(sysAnswer);
        sysAnswerService.updateById(sysAnswer);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysanswer:delete")
    public R delete(@RequestBody Long[] answerIds) {
        sysAnswerService.removeByIds(Arrays.asList(answerIds));

        return R.ok();
    }

    @RequestMapping("/delete3")
    @RequiresPermissions("sys:sysanswer:delete3")
    public R delete3(@RequestBody Long[] subjectIds) {
        if (subjectIds != null) {
            Long userId = getUserId();
            for (Long subjectId : subjectIds) {
                sysAnswerDao.updat3(subjectId, userId);
            }
        }
        return R.ok();
    }

}
