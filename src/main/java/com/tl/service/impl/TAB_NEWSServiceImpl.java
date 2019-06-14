/** 
 * Copyright: Copyright (c)2015
 * Company: 江西航天信息有限公司(jxhtxx.com) 
 */
package com.tl.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tl.dao.ITabNewsDAO;
import com.tl.model.Tab_news;
import com.tl.service.ITAB_NewsService;

@Service
public class TAB_NEWSServiceImpl implements ITAB_NewsService {
	@Resource
	private ITabNewsDAO tabNewsDAO;

	public List<Tab_news> getNewsList(Map<String, Object> map) {
		try {
			return tabNewsDAO.getNewsListByPage(map);
		} catch (Exception e) {
			System.err.println("查询news异常!" + e);
		}
		return null;
	}

	public Tab_news getNews(Map<String, Object> map) {
		try {
			return tabNewsDAO.getNews(map);
		} catch (Exception e) {
			System.err.println("查询news异常!" + e);
		}
		return null;
	}
}
