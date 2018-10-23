package com.xqx.base.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.xqx.base.exception.BaseException;
import com.xqx.base.exception.ErrorCode;
import com.xqx.base.vo.ResponseMessage;

@Aspect
@Component
public class CatAopService {

	@Around(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object catTransactionProcesss(ProceedingJoinPoint pjp) {
		try {
			Object result = pjp.proceed();
			return result;
		} catch (BaseException e) {
			Cat.logEvent("BaseException", e.getClass().getName());
			Transaction t = Cat.newTransaction("BaseException", e.getClass().getName());
			t.setStatus(e);
			t.complete();
			System.out.println(e.getMessage());
			return ResponseMessage.fail(e);
		} catch (Throwable e) {
			Cat.logEvent("Exception", e.getClass().getName());
			Transaction t = Cat.newTransaction("Exception", e.getClass().getName());
			t.setStatus(e);
			t.complete();
			System.out.println(e.getMessage());
			return ResponseMessage.fail(ErrorCode.UNKNOWN_ERROR.getCode(), ErrorCode.UNKNOWN_ERROR.getDescription());
		}
	}
}