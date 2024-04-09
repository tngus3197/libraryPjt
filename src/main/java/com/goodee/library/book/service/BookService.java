package com.goodee.library.book.service;

import java.util.HashMap;
import java.util.List;
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
		// 1. 비어있는 map
		// 2. dao 결과에 따라서 map 처리
		LOGGER.info("도서 등록 결과 처리");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 정보 등록 중 오류 발생");
		
		if(dao.creatBook(dto) > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "도서 정보 등록 성공");
		}
		return map;
	}

	public int selectBookCount(String b_name) {
		LOGGER.info("전체 도서 갯수 조회");
		int result = 0;
		result = dao.selectBookCount(b_name);
		return result;
	}

	public List<BookDto> selectBookList(BookDto dto) {
		LOGGER.info("전체 도서 목록 조회");
		return dao.selectBookList(dto);
	}

	public List<BookDto> selectBookListToday() {
		LOGGER.info("오늘 도서 목록 조회");
		return dao.selectBookListToday();
	}

	
	
	
}
