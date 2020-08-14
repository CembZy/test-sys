package io.renren.modules.test.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysTestEntityVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long courseId;
    private Long testsubsId;
    private Long topicId;
    private Long num;
    private String courseName;
}
