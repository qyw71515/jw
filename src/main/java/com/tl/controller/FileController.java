
package com.tl.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tl.model.TabFile;
import com.tl.service.ITabFileService;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class FileController {
	@Resource
	private ITabFileService tabFileService;

	@RequestMapping(value = "/fileUpload", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String fileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {

		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = req.getFile("file");

		String filepath = "C:\\file\\" + multipartFile.getOriginalFilename();
		File desFile = new File(filepath);
		if (!desFile.getParentFile().exists()) {
			desFile.mkdirs();
		}
		multipartFile.transferTo(desFile);

		// 生成缩略图
		Thumbnails.of(new File("C:\\file\\" + multipartFile.getOriginalFilename())).size(320, 320)
				.toFile(new File("C:\\file\\small\\" + multipartFile.getOriginalFilename()));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid", String.valueOf(UUID.randomUUID()));
		map.put("filename", multipartFile.getOriginalFilename());
		map.put("filepath", "C:\\file\\" + multipartFile.getOriginalFilename());
		map.put("smallfilepath", "C:\\file\\small" + multipartFile.getOriginalFilename());
		map.put("filesize", desFile.length() / 1000 + "kb");
		tabFileService.insert(map);

		JSONObject json = new JSONObject();
		json.put("code", 0);
		return json.toString();
	}

	@RequestMapping(value = "/getImgList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getImgList(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		List<TabFile> fileList = tabFileService.getFile(map);

		JSONObject json = new JSONObject();
		JSONArray jsonarr = new JSONArray();
		for (TabFile file : fileList) {
			JSONObject j = new JSONObject();
			String filelength = file.getFilesize();
			String filename = file.getFilename();
			j.put("filename1", filename);
			if (filename.length() > 15) {
				filename = "..." + filename.substring(filename.length() - 10, filename.length());
			}
			j.put("filename", filename);
			j.put("filesize", filelength);
			j.put("fileid", file.getUuid());

			System.out.println("文件:" + filelength);
			System.out.println("文件:" + filename);
			jsonarr.add(j);
		}
		json.put("datas", jsonarr);
		return json.toString();

	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/showPic")
	public ResponseEntity<byte[]> showPic(HttpServletRequest request, HttpServletResponse response) {

		try {
			String filename = (String) request.getParameter("filename");
			File file = new File("C:\\file\\small\\" + filename);

			FileImageInputStream input = null;
			input = new FileImageInputStream(file);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			byte[] data = output.toByteArray();

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/downPic")
	public ResponseEntity<byte[]> downPic(HttpServletRequest request, HttpServletResponse response) {

		try {
			String filename = (String) request.getParameter("filename");
			File file = new File("C:\\file\\" + filename);

			FileImageInputStream input = null;
			input = new FileImageInputStream(file);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			byte[] data = output.toByteArray();

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/downFile")
	public ResponseEntity<byte[]> downFile(HttpServletRequest request, HttpServletResponse response) {
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			String filename = (String) request.getParameter("filename");
			File file = new File(filename);
			String fileName = file.getName();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
			String agent = (String) request.getHeader("USER-AGENT"); // 判断浏览器类型

			if (agent != null && agent.indexOf("Fireforx") != -1) {

				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");

			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}

			response.reset();
			response.setCharacterEncoding("utf-8");
			if (ext == "docx") {
				response.setContentType("application/msword"); // word格式
			} else if (ext == "pdf") {
				response.setContentType("application/pdf"); // word格式
			}
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

			bis = new BufferedInputStream(new FileInputStream(file));
			byte[] b = new byte[bis.available() + 1000];
			int i = 0;
			os = response.getOutputStream(); // 直接下载导出
			while ((i = bis.read(b)) != -1) {
				os.write(b, 0, i);
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	@RequestMapping(value = "/getFileList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getFileList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File("C:\\document");
		JSONObject json = new JSONObject();
		JSONArray jsonarr = new JSONArray();
		if (file.exists()) {
			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				System.out.println("文件夹是空的!");
			} else {
				for (File file2 : files) {
					if (!file2.isDirectory()) {
						JSONObject j = new JSONObject();
						String filelength = String.valueOf(file2.length() / 1024);
						String filename = file2.getAbsolutePath();
						String type = "";
						if (filename.endsWith(".doc")) {
							type = "doc";
						} else if (filename.endsWith(".docx")) {
							type = "docx";
						} else if (filename.endsWith(".xls")) {
							type = "xls";
						} else if (filename.endsWith(".xlsx")) {
							type = "xlsx";
						} else if (filename.endsWith(".ppt")) {
							type = "ppt";
						} else if (filename.endsWith(".pptx")) {
							type = "pptx";
						} else if (filename.endsWith(".pdf")) {
							type = "pdf";
						}

						if (!"".equals(type)) {
							j.put("filefullname", filename);
							System.err.println(filename);
							filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.length());
							if (filename.length() > 15) {
								filename = "..." + filename.substring(filename.length() - 10, filename.length());
							}
							j.put("filename", filename);
							j.put("filesize", filelength + "kb");
							j.put("type", type);

							System.out.println("文件:" + filelength);
							System.out.println("文件:" + filename);
							jsonarr.add(j);
						}

					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
		json.put("datas", jsonarr);
		return json.toString();
	}

}
