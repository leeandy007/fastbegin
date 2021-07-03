package com.andy.fast.bean;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<Result> implements Serializable {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回数据
     */
    private Result data;

    /**
     * 返回提示信息
     */
    private String message;

}
