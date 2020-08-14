package io.renren.modules.title.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * 试题表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-25 11:03:33
 */
@Data
@TableName("sys_title")
public class SysTitleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long titleId;
    /**
     * 试卷id
     */
    private Long testId;
    private Long topicId;
    /**
     * 题库试题id
     */
    private String subjectId;
    /**
     * 题目顺序
     */
    private Integer orders;
    /**
     * 创建人
     */
    private String admin;
    /**
     * 创建时间
     */
    private Date createTime;


    @TableField(exist = false)
    private String testName;

    @TableField(exist = false)
    private String subjectName;

    @TableField(exist = false)
    private String type;

    @TableField(exist = false)
    private Long courseId;
    @TableField(exist = false)
    private String courseName;

    @TableField(exist = false)
    private Long deptId;
    @TableField(exist = false)
    private String deptName;
    @TableField(exist = false)
    private IdentityHashMap<Long, String> tests;

    @TableField(exist = false)
    private List<IdentityHashMap<String, Long>> courses;
    @TableField(exist = false)
    private IdentityHashMap<String, Long> topics;
    @TableField(exist = false)
    private IdentityHashMap<String, Long> subjects;

}
