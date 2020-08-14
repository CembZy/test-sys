package io.renren.modules.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.subject.entity.SysSubjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:15:54
 */
@Mapper
public interface SysSubjectDao extends BaseMapper<SysSubjectEntity> {

    List<Long> queryIds(@Param("courseId") Long courseId, @Param("topicId") Long topicId);

    Long queryInfos();

    SysSubjectEntity getCuoti(Long subjectId);

}
