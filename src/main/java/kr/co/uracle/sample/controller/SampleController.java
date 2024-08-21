package kr.co.uracle.sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.uracle.framework.annotations.Decryption;
import kr.co.uracle.framework.exception.CommonException;
import kr.co.uracle.framework.utils.cryptography.DefaultAesUtil;
import kr.co.uracle.framework.utils.cryptography.TestAesUtil;

@Controller
public class SampleController {

	@GetMapping("/json")
	@ResponseBody
	public ResponseEntity index (@RequestParam(defaultValue = "World") String name) {
		String msg = "Hello, %s!";
		return ResponseEntity.ok(msg.formatted(name));
	}

	@RequestMapping(value = "/html")
	public String index2 (@RequestParam String[] name, @RequestParam String error) throws Exception {
		if( !error.isEmpty() ){
			throw new CommonException("ERROR_CODE_01", "강제 익셉션 테스트");
		}
		else{
			return "home";
		}
	}


	@RequestMapping(value = "/dechtml")
	@ResponseBody
	public ResponseEntity index22 (@RequestParam @Decryption(type = TestAesUtil.class) String name) throws Exception {
		String msg = "Hello, %s!";
		return ResponseEntity.ok(msg.formatted(name));
	}
}
