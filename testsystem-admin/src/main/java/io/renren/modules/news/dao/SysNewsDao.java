package io.renren.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.news.entity.SysNewsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-26 11:34:21
 */
@Mapper
public interface SysNewsDao extends BaseMapper<SysNewsEntity> {
	
}
