package com.robben.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 统一返回实体类,默认是正确的返回
 * @param <T>
 */
@Data
@ToString(callSuper = true)
@ApiModel(description= "返回统一体")
public class ResponseEntityDto<T> implements Serializable {
	private static final long serialVersionUID = -720807478055084231L;

	@ApiModelProperty(value = "20000 标识请求成功")
	private Integer code;
	@ApiModelProperty(value = "code!=20000情况下的错误说明")
	private String message;
	@ApiModelProperty(value = "返回数据")
	private T data;

	public ResponseEntityDto(){
		this.code = ResultEnum.SUCCESS.getCode();
	}

	public ResponseEntityDto(boolean flag){
		if(flag){
			this.code = ResultEnum.SUCCESS.getCode();
		}else{
			this.code = ResultEnum.UNKNOWN_ERROR.getCode();
		}
	}

	public ResponseEntityDto(Integer code){
		this.code = code;
	}

	public ResponseEntityDto(Integer code, String message){
		this.code = code;
		this.message = message;
	}

}
