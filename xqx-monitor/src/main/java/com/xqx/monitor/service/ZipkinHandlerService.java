package com.xqx.monitor.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xqx.base.util.HttpClientUtils;
import com.xqx.monitor.bean.MonitorConf;

import zipkin2.Span;

@Service
public class ZipkinHandlerService {
	private Logger logger = LoggerFactory.getLogger(ZipkinHandlerService.class);

}
