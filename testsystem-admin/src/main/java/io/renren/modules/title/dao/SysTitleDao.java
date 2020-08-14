package io.renren.modules.title.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.title.entity.SysTitleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试题表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-25 11:03:33
 */
@Mapper
public interface SysTitleDao extends BaseMapper<SysTitleEntity> {
    List<SysSubjectEntity> getSubjects(@Param("testId") Long testId, @Param("userId") Long userId);

    List<SysSubjectEntity> getSubjects2(@Param("testId") Long testId, @Param("userId") Long userId, @Param("orders") Long orders);

    Long getCount(@Param("testId") Long testId, @Param("topicId") Long topicId, @Param("userId") Long userId);

    Integer getOne(@Param("testId") Long testId, @Param("userId") Long userId);
}
