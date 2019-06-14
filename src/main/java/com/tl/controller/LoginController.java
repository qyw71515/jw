package com.tl.controller;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tl.constant.Constant;
import com.tl.utils.HttpsUtils;

import net.sf.json.JSONObject;

@Controller
public class LoginController {
	@RequestMapping(value = "/wxLogin", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> wxLogin(Model model, @RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "rawData", required = false) String rawData,
			@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "encrypteData", required = false) String encrypteData,
			@RequestParam(value = "iv", required = false) String iv) {
		System.err.println("Start get SessionKey");

		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("用户非敏感信息" + rawData);
		JSONObject rawDataJson = JSONObject.fromObject(rawData);

		System.out.println("签名" + signature);

		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
		String param = "appid=" + Constant.WX_APP_ID + "&secret=" + Constant.WX_APP_SECRET + "&js_code=" + code
				+ "&grant_type=authorization_code";

		// 发送post请求读取调用微信接口获取openid用户唯一标识
		JSONObject SessionKeyOpenId = JSONObject.fromObject(HttpsUtils.sendPost(requestUrl, param, "utf-8"));
		System.out.println("post请求获取的SessionAndopenId=" + SessionKeyOpenId);

		String openid = SessionKeyOpenId.getString("openid");

		String sessionKey = SessionKeyOpenId.getString("session_key");

		System.out.println("openid=" + openid + ",session_key=" + sessionKey);

		String nickName = rawDataJson.getString("nickName");
		String avatarUrl = rawDataJson.getString("avatarUrl");
		String gender = rawDataJson.getString("gender");
		String city = rawDataJson.getString("city");
		String country = rawDataJson.getString("country");
		String province = rawDataJson.getString("province");

		Calendar c = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = String.valueOf(f.format(c.getTime()));

		String timestamp = String.valueOf(System.currentTimeMillis());

		String uuid = UUID.randomUUID().toString();

		map.put("uuid", uuid);
		map.put("uid", openid);
		map.put("createtimestamp", timestamp);
		map.put("createtime", time);
		map.put("sessionkey", openid);
		map.put("ubalance", 0);
		map.put("skey", uuid);
		map.put("uaddress", country + "," + province + "," + city);
		map.put("uavatar", avatarUrl);
		map.put("ugender", Integer.parseInt(gender));
		map.put("uname", nickName);
		map.put("updatetimestamp", timestamp);
		map.put("updatetime", time);

//		iTAB_WXAPPUSERService.insertOrUpdateWxappuser(map);

		// 把新的sessionKey和oppenid返回给小程序
		map.clear();
		map.put("userID", uuid);
		map.put("openid", openid);

		map.put("result", "0");

		JSONObject userInfo = getUserInfo(encrypteData, sessionKey, iv);
		System.out.println("根据解密算法获取的userInfo=" + userInfo);
		userInfo.put("balance", 0);
		map.put("userInfo", userInfo);

		return map;
	}

	public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
		// 被加密的数据

		byte[] dataByte = Base64.getDecoder().decode(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64.getDecoder().decode(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.getDecoder().decode(iv);
		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				return JSONObject.fromObject(result);
			}
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (InvalidParameterSpecException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (BadPaddingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
