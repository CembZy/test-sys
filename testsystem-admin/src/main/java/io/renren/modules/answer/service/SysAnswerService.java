package io.renren.modules.answer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.answer.entity.SysAnswerEntity;

import java.util.Map;

/**
 * 做题答案表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-03 09:21:31
 */
public interface SysAnswerService extends IService<SysAnswerEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage2(Map<String, Object> params);
}

