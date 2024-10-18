package com.ssmlearn.tasks.generator.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 任务种类
 * @TableName TaskList_type
 */
@TableName(value ="type")
@Data
public class Kind implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String douser;

    private Long plateId;
    @TableField(exist = false)
    private Plate plate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}