package io.renren.modules.answer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 做题答案表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-03 09:21:31
 */
@Data
@TableName("sys_answer")
public class SysAnswerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long answerId;
    /**
     * 答题人id
     */
    private Long userId;
    private Long price;
    /**
     * 试卷id
     */
    private String testId;
    /**
     * 试题id
     */
    private String subjectId;
    /**
     * 答案内容
     */
    private String content;
    private String uuid;
    /**
     * 创建时间
     */
    private Date createTime;
    private Long orders;

    private Integer falg;
    private Integer shanchu;

    @TableField(exist = false)
    private Integer num;
    @TableField(exist = false)
    private String name3;
    @TableField(exist = false)
    private String name4;

    @TableField(exist = false)
    private int type;
}
