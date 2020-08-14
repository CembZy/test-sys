package io.renren.modules.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.test.entity.SysTestEntity;

import java.util.Map;

/**
 * 考试表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-25 11:00:46
 */
public interface SysTestService extends IService<SysTestEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage2(Map<String, Object> params);
}

