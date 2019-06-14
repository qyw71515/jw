package com.tl.dao;

import java.util.List;
import java.util.Map;

import com.tl.model.Tab_news;


public interface ITabNewsDAO {

	/**   
	 * @Title: getNewsList   
	 * @Description: 
	 * @param: @param map
	 * @param: @return      
	 * @return: List<Tab_news>      
	 * @throws   
	 */
	List<Tab_news> getNewsListByPage(Map<String, Object> map);

	/**   
	 * @Title: getNews   
	 * @Description: 
	 * @param: @param map
	 * @param: @return      
	 * @return: Tab_news      
	 * @throws   
	 */
	Tab_news getNews(Map<String, Object> map);

}
