package com.xqx.zuul.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xqx.zuul.config.ZuulConfig;

@Component
public class DebugsFilter extends BaseFilter {

    private static Logger logger = LoggerFactory.getLogger(DebugsFilter.class);

    @Autowired
    private ZuulConfig config;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -100;
    }

    /**
     * @return
     */
    @Override
    public boolean shouldFilter() {
    	logger.info(config.getDebugOpen()+"\t"+config.getAllDebugRequest()+"\t"+config.getDebugParameter());
		
		if(!config.getDebugOpen()) {
			return false;
		}
		if("true".equals(RequestContext.getCurrentContext().getRequest().getParameter(config.getDebugParameter()))) {
			return true;
		}
		return config.getAllDebugRequest();
    }

    /**
     * 过滤器的具体逻辑。
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        logger.info("DebugsFilter PRE -100 "+config.getDebugParameter());
        RequestContext.getCurrentContext().setDebugRequest(true);
        RequestContext.getCurrentContext().setDebugRouting(true);
        return null;
    }

}

