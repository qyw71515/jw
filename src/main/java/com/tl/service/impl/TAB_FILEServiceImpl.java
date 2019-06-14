/** 
 * Copyright: Copyright (c)2015
 * Company: 江西航天信息有限公司(jxhtxx.com) 
 */
package com.tl.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tl.dao.ITabFileDAO;
import com.tl.model.TabFile;

import com.tl.service.ITabFileService;

@Service
public class TAB_FILEServiceImpl implements ITabFileService {
	@Resource
	private ITabFileDAO tabFileDAO;

	public void insert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
			tabFileDAO.insert(map);
		} catch (Exception e) {
			System.err.println("上传file异常!" + e);
		}
	}

	public List<TabFile> getFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
			return tabFileDAO.getFile(map);
		} catch (Exception e) {
			System.err.println("上传file异常!" + e);
		}
		return null;
	}
}
