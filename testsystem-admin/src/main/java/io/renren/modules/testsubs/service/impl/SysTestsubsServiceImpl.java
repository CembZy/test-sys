package io.renren.modules.testsubs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.testsubs.dao.SysTestsubsDao;
import io.renren.modules.testsubs.entity.SysTestsubsEntity;
import io.renren.modules.testsubs.service.SysTestsubsService;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("sysTestsubsService")
public class SysTestsubsServiceImpl extends ServiceImpl<SysTestsubsDao, SysTestsubsEntity> implements SysTestsubsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysTestsubsEntity> page = this.page(
                new Query<SysTestsubsEntity>().getPage(params),
                new QueryWrapper<SysTestsubsEntity>()
        );

        return new PageUtils(page);
    }

}
