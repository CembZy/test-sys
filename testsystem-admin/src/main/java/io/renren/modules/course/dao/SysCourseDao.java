package io.renren.modules.course.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.course.entity.SysCourseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 科目表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:21:48
 */
@Mapper
public interface SysCourseDao extends BaseMapper<SysCourseEntity> {
    List<SysCourseEntity> queryList(Map<String, Object> params);

    /**
     * 查询子科目ID列表
     *
     * @param parentId 上级部门ID
     */
    List<Long> queryCourseIdList(Long parentId);


}
