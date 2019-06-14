package com.tl.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

//		if (guolv3(request) || "bingo".equals(guolv2(request.getRequestURL().toString()))) {
//			System.out.println("======访问地址发现非法字符，已拦截======");
//			response.sendRedirect(request.getContextPath() + "/error/error.jsp");
//			return;
//		}
		String myhosts = request.getHeader("host");

		System.out.println("============myhosts=" + myhosts);
//		if (!"localhost:8080".equals(myhosts)&&!"192.168.1.117:8080".equals(myhosts)) {
//			System.out.println("======访问host非法，已拦截======");
//			response.sendRedirect(request.getContextPath() + "/error/error.jsp");
//			
//			return;
//
//		}
		// 过滤请求特殊字符，扫描跨站式漏洞
		Map<String, String[]> parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0) {
			for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String[] values = (String[]) parameters.get(key);
				for (int i = 0; i < values.length; i++) {
					values[i] = guolv(values[i]);
					// System.out.println(values[i]);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public static String guolv(String a) {
		a = a.replaceAll("%22", "");
		a = a.replaceAll("%27", "");
		a = a.replaceAll("%3E", "");
		a = a.replaceAll("%3e", "");
		a = a.replaceAll("%3C", "");
		a = a.replaceAll("%3c", "");
		a = a.replaceAll("&lt;", "");
		a = a.replaceAll("&gt;", "");
		a = a.replaceAll("<", "");
		a = a.replaceAll(">", "");
		a = a.replaceAll("\\<", "");
		a = a.replaceAll("\\>", "");
		a = a.replaceAll("\"", "");
		a = a.replaceAll("'", "");
		a = a.replaceAll("\\+", "");
		a = a.replaceAll("\\(", "");
		a = a.replaceAll("\\)", "");
		a = a.replaceAll(" and ", "");
		a = a.replaceAll(" or ", "");
		a = a.replaceAll(" 1=1 ", "");
		return a;
	}

	public String guolv2(String a) {

		if (a != null && !"".equals(a)) {
			if (a.contains("%22") || a.contains("%3E") || a.contains("%3e") || a.contains("%3C") || a.contains("%3c")
					|| a.contains("<") || a.contains(">") || a.contains("\"") || a.contains("'") || a.contains("+")
					|| /* a.contains("%27") || */
					a.contains(" and ") || a.contains(" or ") || a.contains("1=1") || a.contains("(")
					|| a.contains(")")) {
				return "bingo";
			}
		}
		return a;
	}

	public boolean guolv3(HttpServletRequest request) {
		boolean b = false;
		// 过滤请求特殊字符，扫描跨站式漏洞
		Map<String, String[]> parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0) {
			for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String[] values = (String[]) parameters.get(key);
				for (int i = 0; i < values.length; i++) {
					if (values[i].contains("%22") || values[i].contains("%3E") || values[i].contains("%3e")
							|| values[i].contains("%3C") || values[i].contains("%3c") || values[i].contains("<")
							|| values[i].contains(">") || values[i].contains("\"") || values[i].contains("'")
							|| values[i].contains("+") || values[i].contains("%27") || values[i].contains(" and ")
							|| values[i].contains(" or ") || values[i].contains("1=1") || values[i].contains("(")
							|| values[i].contains(")")) {
						b = true;
						break;
					}
				}
			}
		}
		return b;

	}

}
