/** 
 * Copyright: Copyright (c)2015
 * Company: 江西航天信息有限公司(jxhtxx.com) 
 */
package com.tl.dao;

import java.util.List;
import java.util.Map;

import com.tl.model.TabVideo;


public interface ITabVideoDAO {

	/**   
	 * @Title: getVideo   
	 * @Description: 
	 * @param: @param map
	 * @param: @return      
	 * @return: List<TabVideo>      
	 * @throws   
	 */
	List<TabVideo> getVideo(Map<String, Object> map);

}
