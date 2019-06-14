/** 
 * Copyright: Copyright (c)2015
 * Company: 江西航天信息有限公司(jxhtxx.com) 
 */
package com.tl.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tl.dao.ITabVideoDAO;
import com.tl.model.TabVideo;

import com.tl.service.ITabVideoService;

@Service
public class TAB_VIDEOServiceImpl implements ITabVideoService {
	@Resource
	private ITabVideoDAO tabVideoDAO;

	public List<TabVideo> getVideo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
			return tabVideoDAO.getVideo(map);
		} catch (Exception e) {
			System.err.println("下载Video异常!" + e);
		}
		return null;
	}
}
