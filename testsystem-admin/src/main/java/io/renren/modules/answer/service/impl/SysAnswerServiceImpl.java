package io.renren.modules.answer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.answer.dao.SysAnswerDao;
import io.renren.modules.answer.entity.SysAnswerEntity;
import io.renren.modules.answer.service.SysAnswerService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("sysAnswerService")
public class SysAnswerServiceImpl extends ServiceImpl<SysAnswerDao, SysAnswerEntity> implements SysAnswerService {

    @Autowired
    private SysAnswerDao sysAnswerDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysAnswerEntity> page = this.page(
                new Query<SysAnswerEntity>().getPage(params),
                new QueryWrapper<SysAnswerEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage2(Map<String, Object> params) {
        List<SysAnswerEntity> sysAnswerEntities = sysAnswerDao.getCuoti(ShiroUtils.getUserId(), (String) params.get("topicName")
                , (String) params.get("subjectName"));
        IPage<SysAnswerEntity> page = new Page<>();
        long total = sysAnswerEntities.size();
        long size = Long.parseLong((String) params.get("limit"));
        long current = Long.parseLong((String) params.get("page"));
        int toIndex = (int) (current * size);
        int index = (int) ((current - 1) * size);
        List<SysAnswerEntity> subList = null;
        if (total < toIndex) {
            subList = sysAnswerEntities.subList(index, (int) (total));
        } else {
            subList = sysAnswerEntities.subList(index, toIndex);
        }
        page.setRecords(subList);
        page.setTotal(total);
        page.setPages(total / size);
        page.setCurrent(current);
        page.setSize(size);
        return new PageUtils(page);

    }

}
