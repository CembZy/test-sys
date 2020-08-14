package io.renren.modules.testsubs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 试卷随机试题表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-31 11:52:00
 */
@Data
@TableName("sys_testsubs")
public class SysTestsubsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long testsubsId;
	/**
	 * 试卷id
	 */
	private Long testId;
	/**
	 * 科目id
	 */
	private Long courseId;
	/**
	 * 题型id
	 */
	private Long topicId;

	private String courseName;
	/**
	 * 题目数量
	 */
	private Long num;
	/**
	 * 创建人
	 */
	private String admin;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
