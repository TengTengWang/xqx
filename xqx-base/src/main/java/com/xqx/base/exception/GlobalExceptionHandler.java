package com.xqx.base.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.xqx.base.vo.ResponseMessage;

/**
 * 捕获全局异常类
 * <p>
 * 作用：捕获Controller所有抛出异常，并对其进行cat埋点操作，并返回调用方提示信息
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 捕获未知异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseMessage<String> defaultErrorHandler(Exception e) {
		log.debug(e.getMessage());
		Transaction t = Cat.newTransaction(e.getClass().getName(), e.getClass().getSimpleName());
		t.setStatus(e);
		t.complete();
		return ResponseMessage.fail(ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getDescription());
	}

	/**
	 * 捕获自定义异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = BaseException.class)
	@ResponseBody
	public ResponseMessage<String> baseExceptionHandler(BaseException e) {
		log.debug(e.getErrMsg());
		Transaction t = Cat.newTransaction(e.getClass().getName(), e.getClass().getSimpleName());
		t.setStatus(e);
		t.complete();
		return ResponseMessage.fail(e);
	}

	/**
	 * 捕获持久层异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = DaoException.class)
	@ResponseBody
	public ResponseMessage<String> daoExceptionHandler(DaoException e) {
		log.debug(e.getErrMsg());
		Transaction t = Cat.newTransaction(e.getClass().getName(), e.getClass().getSimpleName());
		t.setStatus(e);
		t.complete();
		return ResponseMessage.fail(ErrorCode.DAO_ERROR.getCode(), ErrorCode.DAO_ERROR.getDescription());
	}

}
