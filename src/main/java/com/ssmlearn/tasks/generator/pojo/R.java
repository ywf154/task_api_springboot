package com.ssmlearn.tasks.generator.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;
    //操作成功返回数据
    public static <E> R<E> success(E data){
        return new R<>(0,"操作成功",data);
    }
//成功响应结果
    public static R error(String msg){
        return new R(1,msg,null);
    }
    public static R success(){
        return new R(0,"操作成功",null);
    }
}
