package io.renren.modules.jungle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.jungle.dao.SysJungleDao;
import io.renren.modules.jungle.entity.SysJungleEntity;
import io.renren.modules.jungle.service.SysJungleService;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("sysJungleService")
public class SysJungleServiceImpl extends ServiceImpl<SysJungleDao, SysJungleEntity> implements SysJungleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysJungleEntity> page = this.page(
                new Query<SysJungleEntity>().getPage(params),
                new QueryWrapper<SysJungleEntity>()
        );

        return new PageUtils(page);
    }

}
