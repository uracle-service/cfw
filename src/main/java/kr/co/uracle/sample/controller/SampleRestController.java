package kr.co.uracle.sample.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.uracle.sample.mapper.SampleMapper;


@Controller
@RequestMapping("/api")
public class SampleRestController {

	@Autowired
	SampleMapper mapper;
	
	@GetMapping("/test1")
	@ResponseBody
	public HashMap<String, Integer> test1() {
		
		HashMap<String,Integer> data = new HashMap<>();
		data.put("a", 1);
		data.put("b", 2);
		data.put("c", 3);
		
		return data;
	}
	
	@GetMapping("/test2")
	@ResponseBody
	public HashMap<String, Integer> test2() {
		
		HashMap<String,Integer> data = new HashMap<>();
		
		System.out.println(data.get("1").toString());

		return data;
	}
	
	@GetMapping("/dbChk")
	@ResponseBody
	public HashMap<String, String> test3() {
		
		
		String rslt = mapper.connectChk();
		
		HashMap<String,String> data = new HashMap<>();
		data.put("DB", rslt);
		

		return data;
	}
	
	
}
