/** 
 * Copyright: Copyright (c)2015
 * Company: ����������Ϣ���޹�˾(jxhtxx.com) 
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
			System.err.println("��ѯnews�쳣!" + e);
		}
		return null;
	}

	public Tab_news getNews(Map<String, Object> map) {
		try {
			return tabNewsDAO.getNews(map);
		} catch (Exception e) {
			System.err.println("��ѯnews�쳣!" + e);
		}
		return null;
	}
}
