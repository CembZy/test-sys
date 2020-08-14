package io.renren.modules.topic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.topic.entity.SysTopicEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题型表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:25:28
 */
@Mapper
public interface SysTopicDao extends BaseMapper<SysTopicEntity> {

    List<SysTopicEntity> get(@Param("admin") String admin);

}
