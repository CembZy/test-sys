package io.renren.modules.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.course.entity.SysCourseEntity;

import java.util.List;
import java.util.Map;

/**
 * 科目表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:21:48
 */
public interface SysCourseService extends IService<SysCourseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SysCourseEntity> queryList(Map<String, Object> map);


    /**
     * 查询子科目ID列表
     *
     * @param parentId 上级科目ID
     */
    List<Long> queryCourseIdList(Long parentId);


    /**
     * 获取子科目ID，用于数据过滤
     */
    List<Long> getSubCourseIdList(Long deptId);


    /**
     * 查询父科目ID列表
     */
    List<Long> queryParentCourseIdList(Long chiledId);
}

