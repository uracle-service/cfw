package kr.co.uracle.sample.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uracle.framework.exception.CommonException;


@RestController
@RequestMapping("/api")
public class SampleRestController {

	@GetMapping("/test1")
	public HashMap<String, Integer> test1() {
		
		HashMap<String,Integer> data = new HashMap<>();
		data.put("a", 1);
		data.put("b", 2);
		data.put("c", 3);
		
		return data;
	}
	
	@GetMapping("/test2")
	public HashMap<String, Integer> test2() {
		
		HashMap<String,Integer> data = new HashMap<>();
		
		System.out.println(data.get("1").toString());

		return data;
	}
	
	
}
