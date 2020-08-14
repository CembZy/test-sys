package io.renren.modules.jungle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.jungle.entity.SysJungleEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-20 11:54:53
 */
public interface SysJungleService extends IService<SysJungleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

