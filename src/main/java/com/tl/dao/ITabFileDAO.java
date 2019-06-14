/** 
 * Copyright: Copyright (c)2015
 * Company: 江西航天信息有限公司(jxhtxx.com) 
 */
package com.tl.dao;

import java.util.List;
import java.util.Map;

import com.tl.model.TabFile;


public interface ITabFileDAO {

	/**   
	 * @Title: insert   
	 * @Description: 
	 * @param: @param map      
	 * @return: void      
	 * @throws   
	 */
	void insert(Map<String, Object> map);

	/**   
	 * @Title: getFile   
	 * @Description: 
	 * @param: @param map
	 * @param: @return      
	 * @return: List<TabFile>      
	 * @throws   
	 */
	List<TabFile> getFile(Map<String, Object> map);

}
