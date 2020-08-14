package io.renren.modules.course.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 科目表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:21:48
 */
@Data
@TableName("sys_course")
public class SysCourseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long courseId;
    /**
     * 上级科目id
     */
    private Long parentId;
    private Long deptId;
    /**
     * 题型类型
     */
    private String type;
    /**
     * 题型名称
     */
    private String name;
    /**
     * 状态
     */
    private int status;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 是否删除  -1：已删除  0：正常
     */
    @TableLogic
    private Integer delFlag;
    /**
     * 创建人
     */
    private String admin;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上级科目名称
     */
    @TableField(exist = false)
    private String parentName;
    @TableField(exist = false)
    private String deptName;
    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;
    @TableField(exist = false)
    private List<?> list;

}
