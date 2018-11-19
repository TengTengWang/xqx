package com.xqx.zuul.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xqx.base.exception.ErrorCode;
import com.xqx.base.exception.ServiceException;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.zuul.service.ITokenService;

/**
 * 定义请求过滤规则。
 * 请求参数中必须包含accessToken，否则过滤，不进行路由。
 */

@Component
public class AccessFilter extends BaseFilter {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
	private ITokenService tokenService;

    /**
     * 过滤器类型，它决定过滤器在请求的哪个生命周期中执行。
     * pre：可以在请求被路由之前调用
     * route：在路由请求时候被调用
     * post：在route和error过滤器之后被调用
     * error：处理请求时发生错误时被调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序，越小的值越优先处理。
     * 当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行。
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public boolean shouldFilter() {
        String requestPath = RequestContext.getCurrentContext().getRequest().getRequestURL().toString();
        return !requestPath.contains("/login") && !requestPath.contains("/refresh")&& !requestPath.contains("/xqx-job-admin");
    }

    /**
     * 过滤器的具体逻辑。
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        logger.info("AccessFilter PRE 0");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        logger.info("Send {} reqeust to {}", request.getMethod(), request.getRequestURL().toString());
        // 设置编码格式
        HttpServletResponse response = requestContext.getResponse();
        response.setContentType("text/json;charset=UTF-8");
        // 获取指定参数
        String accessToken = request.getHeader("Authorization");

        if(Strings.isNullOrEmpty(accessToken)){
            getRequsetContext(requestContext, 401,
                    ResponseMessage.fail(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "Authorization不能为空"));
        } else {
        	try {
        		// 校验token
        		tokenService.verifyToken(accessToken);
        		logger.info("Authorization校验成功");
        	}catch(ServiceException e) {
        		getRequsetContext(requestContext, 401,
                        ResponseMessage.fail(e.getErrorCode().getCode(), e.getErrMsg()));
        	}
        }
        return null;
    }

}

