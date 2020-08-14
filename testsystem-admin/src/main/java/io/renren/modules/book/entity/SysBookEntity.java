package io.renren.modules.book.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 电子书籍表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-07 11:23:53
 */
@Data
@TableName("sys_book")
public class SysBookEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long bookId;
	/**
	 * 科目id
	 */
	private Long courseId;
	/**
	 * 浏览次数
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

	private String body;
	private String name;

	@TableField(exist = false)
	private String courseName;

}
