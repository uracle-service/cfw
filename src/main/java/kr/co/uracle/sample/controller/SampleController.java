package kr.co.uracle.sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	@GetMapping("/json")
	@ResponseBody
	public ResponseEntity index (@RequestParam(defaultValue = "World") String name) {
		String msg = "Hello, %s!";
		return ResponseEntity.ok(msg.formatted(name));
	}

	@RequestMapping(value = "/html")
	public String index2 (@RequestParam String error) throws Exception {
		return "home";
	}
}
