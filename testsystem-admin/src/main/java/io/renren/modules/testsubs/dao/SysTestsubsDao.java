package io.renren.modules.testsubs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.testsubs.entity.SysTestsubsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 试卷随机试题表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-31 11:52:00
 */
@Mapper
public interface SysTestsubsDao extends BaseMapper<SysTestsubsEntity> {
	
}
