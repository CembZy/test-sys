package io.renren.modules.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.test.entity.SysTestEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 考试表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-25 11:00:46
 */
@Mapper
public interface SysTestDao extends BaseMapper<SysTestEntity> {
	
}
