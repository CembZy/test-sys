package io.renren.modules.test.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.testsubs.entity.SysTestsubsEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * 考试表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-25 11:00:46
 */
@Data
@TableName("sys_test")
public class SysTestEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long testId;
    /**
     * 参考部门id
     */
    private String deptId;
    private Long userId;
    private String parentId;
    /**
     * 评卷人id
     */
    private String adminId;
    /**
     * 试题名称
     */
    private String name;
    /**
     * 试题类型
     */
    private String type;
    /**
     * 出题方式
     */
    private String typet;
    private String subjectId;
    /**
     * 答题时间
     */
    private Long times;
    /**
     * 试题总分
     */
    private Integer price;
    /**
     * 通过分数
     */
    private Integer inprice;
    private Integer num;
    /**
     * 考试类型（1：允许多次参加考试，2：允许查看评卷结果）
     */
    private String testType;
    private String uuid;
    /**
     * 间隔时间
     */
    private Long fixTimes;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
    /**
     * 创建人
     */
    private String admin;
    /**
     * 创建时间
     */
    private Date createTime;


    @TableField(exist = false)
    private List<Long> testTypeList;

    @TableField(exist = false)
    private List<Long> adminsList;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private List<IdentityHashMap<String, Long>> courses;
    @TableField(exist = false)
    private IdentityHashMap<String, Long> topics;
    @TableField(exist = false)
    private Long topicId;
    @TableField(exist = false)
    private Long courseId;

    @TableField(exist = false)
    private int flag;

    @TableField(exist = false)
    private Integer status;

    @TableField(exist = false)
    private String courseName;

    @TableField(exist = false)
    private List<Long> courseIdList;

    @TableField(exist = false)
    private List<SysTestsubsEntity> sysTestEntityVos;

    @TableField(exist=false)
    private List<Long> deptIdList;
}
