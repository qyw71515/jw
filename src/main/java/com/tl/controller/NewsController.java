package com.tl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tl.model.Tab_news;
import com.tl.service.ITAB_NewsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class NewsController {

	@Resource
	private ITAB_NewsService iTAB_NewsService;

	@RequestMapping(value = "/getNewsList", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getNewsList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", 0);
		List<Tab_news> newsList = iTAB_NewsService.getNewsList(map);
		JSONObject data = new JSONObject();
		JSONArray datas = new JSONArray();
		for (Tab_news news : newsList) {
			JSONObject json = new JSONObject();
			json.put("newsId", news.getId());
			json.put("imgurl", news.getImgurl());
			json.put("newsTitle", news.getTitle());
			json.put("newsTime", news.getTime());
			datas.add(json);
		}
		data.put("datas", datas);

		return data.toString();

	}

	@RequestMapping(value = "/getNews", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getNews(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", request.getParameter("id"));
		Tab_news news = iTAB_NewsService.getNews(map);

		JSONObject json = new JSONObject();
		json.put("newsId", news.getId());
		json.put("imgurl", news.getImgurl());
		json.put("newsTitle", news.getTitle());
		json.put("newsTime", news.getTime());
		json.put("newsContent", news.getContent());

		return json.toString();

	}

}
