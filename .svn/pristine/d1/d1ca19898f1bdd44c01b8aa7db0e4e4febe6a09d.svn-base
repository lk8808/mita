package com.tr.ibs.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tr.ibs.org.service.IDepposlnkService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test1 {
	
	@Autowired
	private IDepposlnkService depposlnkService;

	@Test
	public void test1() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("depid", 3);
		params.put("page", 1);
		params.put("rows", 5);
		System.out.println(depposlnkService.queryByDepid_ext(params).getRtdata());
	}
}
