package io.renren.modules.subject.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.jungle.entity.SysJungleEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:15:54
 */
@Data
@TableName("sys_subject")
public class SysSubjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long subjectId;
    /**
     * 科目表id
     */
    private Long courseId;
    /**
     * 题型id
     */
    private Long topicId;
    /**
     * 题目难度
     */
    private String difficulty;
    private String file;
    //解析
    private String parse;
    private String uuid;
    private String name;
    /**
     * 选项数量
     */
    private Integer num;
    /**
     * 题目分数
     */
    private Integer price;
    /**
     * 题目内容
     */
    private String content;
    private String answert;
    private String answertt;
    /**
     * 创建人
     */
    private String admin;
    /**
     * 答案
     */
    private String answer;
    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String topicName;
    @TableField(exist = false)
    private String courseNameT;
    @TableField(exist = false)
    private Map<String, Long> topics;

    @TableField(exist = false)
    private List<Long> courseIdList;

    @TableField(exist = false)
    private List<SysJungleEntity> jugleList;

    @TableField(exist = false)
    private String answers;

    @TableField(exist = false)
    private Long answerId;

    @TableField(exist = false)
    private List<String> answertts;

    @TableField(exist = false)
    private Long pingfun;

    @TableField(exist = false)
    private Long num2;

    @TableField(exist = false)
    private Integer orders;
}
