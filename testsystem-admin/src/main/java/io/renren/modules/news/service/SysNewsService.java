package io.renren.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.news.entity.SysNewsEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-26 11:34:21
 */
public interface SysNewsService extends IService<SysNewsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

