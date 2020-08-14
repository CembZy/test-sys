package io.renren.modules.jungle.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-20 11:54:53
 */
@Data
@TableName("sys_jungle")
public class SysJungleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long jungleId;
	/**
	 * 题目id
	 */
	private Long subjectId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 选项
	 */
	private String name;
	/**
	 * 创建人
	 */
	private String admin;
	/**
	 * 
	 */
	private Date createTime;

}
