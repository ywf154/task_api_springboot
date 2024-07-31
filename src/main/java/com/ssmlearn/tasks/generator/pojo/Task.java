package com.ssmlearn.tasks.generator.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName TaskList_task
 */
@TableName(value ="TaskList_task")
@Data
public class Task implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String wxNoticeFrom;
    private String name;
    private String toWho;
    private String wxNoticeTo;
    private Date endTime = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
    private Boolean status = false;
    private Date finishTime;
    private Date addTime = new Date(System.currentTimeMillis());
    private Long typeId;

    @TableField(exist = false)
    private Kind kind;
    @TableField(exist = false)
    private Plate plate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}