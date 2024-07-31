package com.ssmlearn.tasks.generator.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName TaskList_plate
 */
@TableName(value ="TaskList_plate")
@Data
public class Plate implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String dutyuser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Kind> kinds;
}