package com.tl.service;

import java.util.List;
import java.util.Map;

import com.tl.model.TabVideo;

public interface ITabVideoService {

	/**
	 * @Title: getVideo @Description: @param: @param map @param: @return @return:
	 * List<TabVideo> @throws
	 */
	List<TabVideo> getVideo(Map<String, Object> map);

}
