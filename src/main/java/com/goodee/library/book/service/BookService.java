package com.goodee.library.book.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.library.book.dao.BookDao;
import com.goodee.library.book.dto.BookDto;

@Service
public class BookService {

	private static final Logger LOGGER = LogManager.getLogger(BookService.class);

	@Autowired
	BookDao dao;
	
	public Map<String, String> createBook(BookDto dto) {
		LOGGER.info("도서 등록 결과 처리");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 등록 중 오류 발생");
		
		int result = 0;
		result = dao.creatBook(dto);
		if(result > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "도서 등록 성공");
		}
		return map;
	}
	
}
