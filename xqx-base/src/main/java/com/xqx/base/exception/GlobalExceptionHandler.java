package com.xqx.base.exception;

import javax.servlet.http.HttpServletRequest;

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
	@ExceptionHandler(value = Throwable.class)
	@ResponseBody
	public ResponseMessage<String> defaultErrorHandler(Throwable e, HttpServletRequest req) {
		log.debug("全局捕获", e);
		Transaction t = Cat.newTransaction("Exception", e.getClass().getSimpleName());
		t.setStatus(e);
		t.complete();
		return ResponseMessage.fail(ErrorCode.UNKNOWN_ERROR.getCode(), e.getMessage());
	}

	/**
	 * 捕获运行时异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = RuntimeException.class)
	@ResponseBody
	public ResponseMessage<String> runtimeExceptionHandler(RuntimeException e, HttpServletRequest req) {
		log.debug("全局捕获", e);
		Transaction t = Cat.newTransaction(e.getClass().getSimpleName(), ErrorCode.UNKNOWN_ERROR.getCode()+"");
		t.setStatus(e);
		t.complete();
		return ResponseMessage.fail(ErrorCode.UNKNOWN_ERROR.getCode(), e.getMessage());
	}

	/**
	 * 捕获自定义异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = BaseException.class)
	@ResponseBody
	public ResponseMessage<String> baseExceptionHandler(BaseException e, HttpServletRequest req) {
		// TODO debug 开关
		Transaction t = Cat.newTransaction("Exception", e.getClass().getSimpleName());
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
	public ResponseMessage<String> daoExceptionHandler(DaoException e, HttpServletRequest req) {
		Transaction t = Cat.newTransaction("Exception", e.getClass().getSimpleName());
		t.setStatus(e);
		t.complete();
		return ResponseMessage.fail(ErrorCode.DAO_ERROR.getCode(), ErrorCode.DAO_ERROR.getDescription());
	}

}
