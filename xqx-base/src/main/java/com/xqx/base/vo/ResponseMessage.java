package com.xqx.base.vo;

import java.io.Serializable;

import com.xqx.base.exception.BaseException;
import com.xqx.base.exception.ErrorCode;

/**
 * 请求响应类
 * 
 * @param <T> T:响应体数据类型
 */
public class ResponseMessage<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 状态码，0：成功，非0为异常代码
	 */
	private Integer status;

	/**
	 * 异常信息
	 */
	private String message;

	/**
	 * 响应信息
	 */
	private T data;

	public ResponseMessage() {
	}

	/**
	 * 构造成功消息
	 * 
	 * @param data 返回数据
	 * @param      <T> 返回数据类型
	 * @return 消息
	 */
	public static <T> ResponseMessage<T> success(T data) {
		return new ResponseMessage<T>(0, null, data);
	}

	/**
	 * 构造错误消息
	 * 
	 * @param errorCode    异常状态码
	 * @param errorMessage 异常描述信息
	 * @param              <T> 返回数据类型
	 * @return 消息
	 */
	public static <T> ResponseMessage<T> fail(int errorCode, String errorMessage) {
		return new ResponseMessage<T>(errorCode, errorMessage, null);
	}

	/**
	 * 构造错误消息
	 * 
	 * @param e 自定义异常
	 * @return 消息
	 */
	public static <T> ResponseMessage<T> fail(BaseException e) {
		return new ResponseMessage<T>(e.getErrorCode().getCode(), e.getErrMsg(), null);
	}

	/**
	 * 构造错误消息
	 * 
	 * @param e 自定义异常
	 * @return 消息
	 */
	public static <T> ResponseMessage<T> fail(ErrorCode ec) {
		return new ResponseMessage<T>(ec.getCode(), ec.getDescription(), null);
	}

	/**
	 * 私有构造器
	 * 
	 * @param status  状态码
	 * @param message 状态描述
	 * @param data    数据
	 */
	private ResponseMessage(Integer status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseMessage{" + "status=" + status + ", message='" + message + '\'' + ", data=" + data + '}';
	}
}
