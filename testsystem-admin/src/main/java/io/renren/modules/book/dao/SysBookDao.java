package io.renren.modules.book.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.book.entity.SysBookEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电子书籍表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:23:53
 */
@Mapper
public interface SysBookDao extends BaseMapper<SysBookEntity> {
	
}
