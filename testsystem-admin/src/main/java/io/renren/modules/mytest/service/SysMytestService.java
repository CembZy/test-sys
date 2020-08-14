package io.renren.modules.mytest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.mytest.entity.SysMytestEntity;

import java.util.Map;

/**
 * 学生考试结果表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-03 09:23:55
 */
public interface SysMytestService extends IService<SysMytestEntity> {

    PageUtils queryPage1(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage2(Map<String, Object> params);

}

