package kr.co.uracle.sample.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.uracle.framework.annotations.Decryption;
import kr.co.uracle.framework.exception.CommonException;
import kr.co.uracle.framework.utils.cryptography.AESUtil;

@Controller
public class SampleController {

	@GetMapping("/json")
	@ResponseBody
	public HashMap<String, String> index (@RequestParam(defaultValue = "World") String name) {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", name);
		data.put("aaa", "aaaData");
		return data;
	}

	@RequestMapping(value = "/html")
	public String index2 (@RequestParam String[] name, @RequestParam String error) throws Exception {
		String s = "";
		String answer = "";
		String[] datas = s.split(" ");

		int[] intDatas = Arrays.stream(datas).mapToInt(Integer::parseInt).sorted().toArray();
		answer = intDatas[0] + " " + intDatas[intDatas.length];

		if( !error.isEmpty() ){
			throw new CommonException("ERROR_CODE_01", "강제 익셉션 테스트");
		}
		else{
			return "home";
		}
	}

	@RequestMapping(value = "/home")
	public String home() {
		return "home";
	}


	@RequestMapping(value = "/dechtml")
	@ResponseBody
	public Map<String, String> index22 (@RequestParam @Decryption(type = AESUtil.class, algorithm = "AES/CBC/PKCS5Padding", key = "aql201koer0123axz1sdk30z0-d1sssz", iv = "wke221sxzxclqowa") String name) throws Exception {
		/*
		  default:
				algorithm: AES/CBC/PKCS5Padding
				key: aql201koer0123axz1sdk30z0-d1sssz
				iv: wke221sxzxclqowa
			  test:
				algorithm: AES/GCM/NoPadding
				key: aql201koer0123axz1sdk30z0-d1sssz
				iv: wke221sxzxclqowa

		 */
		String msg = "Hello, %s!";
		Map<String, String> data = new HashMap<>();
		data.put("aaa", msg.formatted(name));
		return data;
	}
}
