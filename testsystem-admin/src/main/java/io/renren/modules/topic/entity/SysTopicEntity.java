package io.renren.modules.topic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题型表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:25:28
 */
@Data
@TableName("sys_topic")
public class SysTopicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long topicId;
	/**
	 * 题型
	 */
	private String type;
	/**
	 * 创建人
	 */
	private String admin;
	/**
	 * 题型名称
	 */
	private String name;
	private String name2;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;

}
