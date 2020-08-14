package io.renren.modules.topic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.topic.entity.SysTopicEntity;

import java.util.Map;

/**
 * 题型表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:25:28
 */
public interface SysTopicService extends IService<SysTopicEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

