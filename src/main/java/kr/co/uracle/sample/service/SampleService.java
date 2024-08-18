package kr.co.uracle.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uracle.sample.mapper.SampleMapper;

@Service
public class SampleService {

	@Autowired
	private SampleMapper mapper;
	
	public String connectChk() {
		
		String rslt = mapper.connectChk();
		
		return rslt;
	}
	
}
