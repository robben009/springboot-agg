package com.robben.agg.base.resp;

import com.robben.agg.base.contants.BbResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 统一返回实体类,默认是正确的返回
 * @param <T>
 */
@Data
@ToString(callSuper = true)
@Schema(description= "返回统一体")
public class ResponseEntityDto<T> implements Serializable {
	private static final long serialVersionUID = -720807478055084231L;

	@Schema(description = "标识请求成功",defaultValue = "1",title = "注解id-title")
	private Integer code;
	@Schema(description = "code!=20000情况下的错误说明")
	private String message;
	@Schema(description = "返回数据")
	private T data;

	public ResponseEntityDto(){
		this.code = BbResultEnum.SUCCESS.getCode();
	}

	public ResponseEntityDto(boolean flag){
		if(flag){
			this.code = BbResultEnum.SUCCESS.getCode();
		}else{
			this.code = BbResultEnum.UNKNOWN_ERROR.getCode();
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
