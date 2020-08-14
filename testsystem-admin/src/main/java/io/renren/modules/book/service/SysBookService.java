package io.renren.modules.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.book.entity.SysBookEntity;

import java.util.Map;

/**
 * 电子书籍表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:23:53
 */
public interface SysBookService extends IService<SysBookEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

