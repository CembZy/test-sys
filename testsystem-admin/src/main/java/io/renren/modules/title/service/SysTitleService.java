package io.renren.modules.title.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.title.entity.SysTitleEntity;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * 试题表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-25 11:03:33
 */
public interface SysTitleService extends IService<SysTitleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public List<IdentityHashMap<String, Long>> getCpurse(SysUserEntity sysUserEntity);

    public IdentityHashMap<String, Long> getTopic(Long id, SysUserEntity sysUserEntity);

    public IdentityHashMap<String, Long> getSubjects(Long topicId,Long courseId, SysUserEntity sysUserEntity);
}

