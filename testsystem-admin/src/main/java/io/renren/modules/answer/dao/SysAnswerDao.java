package io.renren.modules.answer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.answer.entity.SysAnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 做题答案表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-03 09:21:31
 */
@Mapper
public interface SysAnswerDao extends BaseMapper<SysAnswerEntity> {

    int updat(@Param("answerId") Long answerId, @Param("price") Long price);

    int updat3(@Param("subjectId") Long subjectId,@Param("userId") Long userId);

    Long getPrice(@Param("userId") Long userId, @Param("testId") String testId, @Param("orders") Long orders);

    List<SysAnswerEntity> getCuoti(@Param("userId") Long userId, @Param("topicName") String topicName
            , @Param("subjectName") String subjectName);
}
