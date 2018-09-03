package com.xqx.oauth.controller;

import com.google.common.base.Strings;
import com.xqx.base.exception.ErrorCode;
import com.xqx.base.exception.ServiceException;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.base.vo.Token;
import com.xqx.oauth.servic.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    TokenService tokenService;


    @RequestMapping(value = "/login")
    public ResponseMessage doLogin(@RequestParam("name")String name, @RequestParam("password")String password){
        logger.info("登陆，用户名：{}",name);
        try{
            Token token = tokenService.createToken(name, password);
            if(token == null){
                return ResponseMessage.fail(ErrorCode.TOKEN_EXCEPTION.getCode(),"创建Token失败");
            }else {
                return ResponseMessage.success(token);
            }
        } catch (ServiceException e) {
            logger.warn("登录失败：ErrorCode = {}, Message = {}",e.getErrorCode().getCode(),e.getErrMsg());
            return ResponseMessage.fail(e.getErrorCode().getCode(),e.getErrMsg());
        }
    }


    @RequestMapping(value = "/verifyToken")
    public ResponseMessage verifyToken(@RequestParam("accessToken")String accessToken){
        logger.info("检验Token，token：{}",accessToken);
        try{
            if(tokenService.verifyToken(accessToken) != null){
                return ResponseMessage.success(true);
            }else{
                return ResponseMessage.fail(ErrorCode.UNKNOWN_ERROR.getCode(),"未知原因失败");
            }
        } catch (ServiceException e) {
            return ResponseMessage.fail(e.getErrorCode().getCode(),e.getErrMsg());
        }
    }

    @RequestMapping(value = "/refreshToken")
    public ResponseMessage refreshToken(@RequestParam("refreshToken")String refreshToken){
        logger.info("更新Token，refreshToken：{}",refreshToken);
        try{

            String context = tokenService.verifyToken(refreshToken);
            if(Strings.isNullOrEmpty( context)){
                return ResponseMessage.success(tokenService.createToken(context));
            }else{
                return ResponseMessage.fail(ErrorCode.UNKNOWN_ERROR.getCode(),"未知原因失败");
            }
        } catch (ServiceException e) {
            return ResponseMessage.fail(e.getErrorCode().getCode(),e.getErrMsg());
        }
    }
}
