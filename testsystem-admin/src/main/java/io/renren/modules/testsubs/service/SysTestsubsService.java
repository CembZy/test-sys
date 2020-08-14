package io.renren.modules.testsubs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.testsubs.entity.SysTestsubsEntity;

import java.util.Map;

/**
 * 试卷随机试题表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-31 11:52:00
 */
public interface SysTestsubsService extends IService<SysTestsubsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

