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
			System.err.println("œ¬‘ÿVideo“Ï≥£!" + e);
		}
		return null;
	}
}
