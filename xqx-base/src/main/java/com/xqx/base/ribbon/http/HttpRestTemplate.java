package com.xqx.base.ribbon.http;

import com.xqx.base.exception.CallRemoteServiceException;
import com.xqx.base.exception.ErrorCode;
import com.xqx.base.gson.GsonUtil;
import com.xqx.base.vo.ResponseMessage;
import com.xqx.base.vo.Token;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * 远程调用工具类
 * 需在Application入口类型，加入如下内容
 *
 * @Bean
 * @LoadBalanced RestTemplate restTemplate(){
 * return new RestTemplate();
 * }
 */
public class HttpRestTemplate {


    private static final HttpRestTemplate INSTANCE = new HttpRestTemplate();

    private Token token;

    private HttpRestTemplate() {
    }

    public static HttpRestTemplate getInstance() {
        return INSTANCE;
    }

    /**
     * 设置TOKEN
     *
     * @param token 用户的TOKEN
     */
    public void setToken(Token token) {
        //logger.info("设置Token：{}", token);
        this.token = token;
    }

    /**
     * 更新TOKEN
     *
     * @return 是否成功
     */
    public boolean refreshToken(RestTemplate restTemplate) throws CallRemoteServiceException {
        //logger.info("更新Token...refreshToken:{}", this.token.getRefreshToken());
        String url = "http://APOLLO-AUTH0/refreshToken";
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("refreshToken", token.getRefreshToken());
        String body = post(restTemplate, url, paramMap, String.class);
        ResponseMessage responseMessage = GsonUtil.fromJson(body, ResponseMessage.class);
        if (responseMessage.getStatus() != 0) {
            throw new CallRemoteServiceException(ErrorCode.getErrorCode(responseMessage.getStatus()), responseMessage.getMessage());
        }
        try {
            this.token = GsonUtil.fromJson(responseMessage.getData().toString(), Token.class);
            //logger.info("更新Token成功");
            return true;
        } catch (Exception e) {
           // logger.error("更新Token失败，{}", e);
            return false;
        }
    }

    /**
     * Post方式
     *
     * @param restTemplate
     * @param url          请求路径
     * @param paramMap     参数
     * @param clazz        返回类型
     * @param <T>
     * @return
     * @throws CallRemoteServiceException
     */
    public <T> T post(RestTemplate restTemplate, String url, MultiValueMap<String, Object> paramMap, Class<T> clazz) throws CallRemoteServiceException {
        //logger.info("发起Post请求...{}", url);
        // 拼接URL
        String requestUrl = buildUrl(url, paramMap);
        //logger.info("发起Post请求地址...{}", requestUrl);
        T t = execute(restTemplate, requestUrl, HttpMethod.POST, clazz);
        //logger.info("发起Post请求成功，{}", t);
        return t;
    }


    /**
     * Get方式
     *
     * @param restTemplate
     * @param url          请求路径
     * @param paramMap     参数
     * @param clazz        返回类型
     * @param <T>
     * @return
     * @throws CallRemoteServiceException
     */
    public <T> T get(final RestTemplate restTemplate, final String url, final MultiValueMap<String, Object> paramMap, Class<T> clazz) throws CallRemoteServiceException {
        //logger.info("发起Get请求...{}", url);
        // 拼接URL
        String requestUrl = buildUrl(url, paramMap);
        //logger.info("发起Get请求地址...{}", requestUrl);
        T t = execute(restTemplate, requestUrl, HttpMethod.GET, clazz);
        //logger.info("发起Get请求成功，{}", t);
        return t;
    }

    /**
     * 设置Header，将AccessToken加入参数中
     *
     * @return
     */
    private HttpEntity<String> setHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (this.token != null) {
            requestHeaders.add("accessToken", this.token.getAccessToken());
            //logger.info("设置Headers中的AccessToken：{}", this.token.getAccessToken());
        }
        return new HttpEntity<String>(requestHeaders);
    }

    /**
     * @param url
     * @param paramMap
     * @return
     */
    private String buildUrl(String url, MultiValueMap<String, Object> paramMap) {
        if(paramMap == null)
            return url;
        StringBuilder urlTemp = new StringBuilder(url);
        urlTemp.append("?");
        paramMap.entrySet().stream().forEach(entry -> urlTemp.append(entry.getKey() + "=" + entry.getValue().get(0) + "&"));
        int lastIndex = urlTemp.lastIndexOf("&");
        if(lastIndex != -1){
            return urlTemp.deleteCharAt(urlTemp.lastIndexOf("&")).toString();
        }
        return urlTemp.toString();
    }

    /**
     * 执行请求动作
     *
     * @param restTemplate
     * @param url
     * @param httpMethod
     * @param clazz
     * @param <T>
     * @return
     * @throws CallRemoteServiceException
     */
    private <T> T execute(RestTemplate restTemplate, String url, HttpMethod httpMethod, Class<T> clazz)
            throws CallRemoteServiceException {
        // 设置Header
        HttpEntity<String> requestEntity = setHeaders();
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, clazz);
            if (responseEntity.getStatusCodeValue() != 200) {
                throw new CallRemoteServiceException(ErrorCode.HTTP_ERROR, responseEntity.getStatusCodeValue() + " " + responseEntity.toString());
            }
            return responseEntity.getBody();
        }catch (HttpClientErrorException e){
            throw new CallRemoteServiceException(e,ErrorCode.HTTP_ERROR, "调用远程服务异常");
        }catch (HttpServerErrorException e){
            throw new CallRemoteServiceException(e,ErrorCode.HTTP_ERROR, "远程服务异常");
        }
    }
}
