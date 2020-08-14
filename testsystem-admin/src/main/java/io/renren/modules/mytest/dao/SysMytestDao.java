package io.renren.modules.mytest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.mytest.entity.SysMytestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生考试结果表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-03 09:23:55
 */
@Mapper
public interface SysMytestDao extends BaseMapper<SysMytestEntity> {
    int updat(@Param("mytestId") Long mytestId, @Param("price") Integer price, @Param("pingjuan") int pingjuan
            , @Param("inprice") String inprice);

    List<SysMytestEntity> getInfos(@Param("userId") Long userId, @Param("testName") String testName);
}
