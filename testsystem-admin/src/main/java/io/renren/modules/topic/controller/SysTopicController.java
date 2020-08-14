package io.renren.modules.topic.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.subject.dao.SysSubjectDao;
import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.renren.modules.topic.entity.SysTopicEntity;
import io.renren.modules.topic.service.SysTopicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 题型表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 */

@RestController
@RequestMapping("sys/systopic")
public class SysTopicController {
    @Autowired
    private SysTopicService sysTopicService;
    @Autowired
    private SysSubjectDao sysSubjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:systopic:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysTopicService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{topicId}")
    @RequiresPermissions("sys:systopic:info")
    public R info(@PathVariable("topicId") Long topicId) {
        SysTopicEntity sysTopic = sysTopicService.getById(topicId);

        return R.ok().put("sysTopic", sysTopic);
    }

    public static void main(String[] args) {
        System.out.println((byte) Integer.parseInt(hexToDec2("a5")));
        System.out.println((byte) 0xA5);
    }

    public static String hexToDec2(String hex) {
        BigInteger data = new BigInteger(hex, 16);
        return data.toString(10);
    }


    /**
     * 十进制数据转换为十六进制字符串数
     *
     * @param dec
     * @return
     */
    public static String decToHex(String dec) {
        int data = Integer.parseInt(dec, 10);
        return Integer.toString(data, 16);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:systopic:save")
    public R save(@RequestBody SysTopicEntity sysTopic) {
        sysTopic.setAdmin(String.valueOf(ShiroUtils.getUserId()));
        sysTopic.setCreateTime(new Date());
        sysTopicService.save(sysTopic);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:systopic:update")
    public R update(@RequestBody SysTopicEntity sysTopic) {
        ValidatorUtils.validateEntity(sysTopic);
        sysTopicService.updateById(sysTopic);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:systopic:delete")
    public R delete(@RequestBody Long[] topicIds) {
        if (topicIds != null) {
            for (Long id : topicIds) {
                List<SysSubjectEntity> sysTestEntities = sysSubjectService
                        .selectList(new QueryWrapper<SysSubjectEntity>().eq("topic_id", id));
                if (sysTestEntities != null && sysTestEntities.size() > 0) {
                    return R.error("有题目使用到此题型，因此不能删除，应先删除题目!");
                }
            }
        }
        sysTopicService.removeByIds(Arrays.asList(topicIds));
        return R.ok();
    }

}
