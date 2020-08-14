package io.renren.modules.mytest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.answer.entity.SysAnswerEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 学生考试结果表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-03 09:23:55
 */
@Data
@TableName("sys_mytest")
public class SysMytestEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long mytestId;
    /**
     * 答题人id
     */
    private Long userId;
    private Long orders;
    /**
     * 试卷id
     */
    private String testId;
    /**
     * 评卷人id
     */
    private String adminId;
    /**
     * 试题得分
     */
    private String price;
    /**
     * 是否通过
     */
    private String inprice;
    /**
     * 是否缺席
     */
    private String kuangkao;
    private String type;

    //答题人
    private String userName;
    private String testName;
    private Integer pingjuan;
    private Date date;

    @TableField(exist = false)
    private String adminName;

    @TableField(exist = false)
    private List<SysAnswerEntity> answerEntities;

    //平均分
    @TableField(exist = false)
    private String price1;

    //参考人数
    @TableField(exist = false)
    private String num1;
}
