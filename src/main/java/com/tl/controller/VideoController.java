package com.tl.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tl.model.TabVideo;
import com.tl.service.ITabVideoService;
import com.tl.utils.HttpsUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class VideoController {
	@Resource
	private ITabVideoService tabVideoService;

	@RequestMapping(value = "/getVideoList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getVideoList(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		List<TabVideo> VideoList = tabVideoService.getVideo(map);

		JSONObject json = new JSONObject();
		JSONArray jsonarr = new JSONArray();
		for (TabVideo Video : VideoList) {
			JSONObject j = new JSONObject();
			String name = Video.getName();
			System.err.println(name.length());
			if (name.length() > 10) {
				name = name.substring(0, 10) + "...";
			}
			j.put("name", name);
			j.put("url", Video.getUrl());

			jsonarr.add(j);
		}
		json.put("datas", jsonarr);
		return json.toString();

	}

	@RequestMapping(value = "/getVideoRealUrl", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getVideoRealUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String vInfourl = "http://vv.video.qq.com/getinfo?vids=VIDEOID&platform=101001&charge=0&otype=json";
			String url1 = request.getParameter("url");
			url1 = url1.substring(url1.lastIndexOf("/") + 1, url1.length());
			url1 = url1.substring(0, url1.lastIndexOf("."));
			vInfourl = vInfourl.replaceAll("VIDEOID", url1);
			System.err.println(vInfourl);

			String retStr = HttpsUtils.doGetStr(vInfourl);
			System.err.println(retStr);
			retStr = retStr.substring(13, retStr.length() - 1);
			System.err.println(retStr);
			JSONObject json = JSONObject.fromObject(retStr);
			JSONObject vl = json.getJSONObject("vl");
			JSONArray vi = vl.getJSONArray("vi");
			JSONObject vi0 = vi.getJSONObject(0);
			String fn = vi0.getString("fn");
			String fvkey = vi0.getString("fvkey");
			JSONObject ul = vi0.getJSONObject("ul");
			JSONArray ui = ul.getJSONArray("ui");
			JSONObject ui0 = ui.getJSONObject(0);
			String url = ui0.getString("url");

			String videlUrl = url + fn + "?vkey=" + fvkey;
			System.err.println(videlUrl);
			JSONObject retJson = new JSONObject();
			retJson.put("url", videlUrl);
			return retJson.toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
