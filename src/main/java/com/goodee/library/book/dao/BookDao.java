package com.goodee.library.book.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goodee.library.book.dto.BookDto;

@Repository
public class BookDao {

	private static final Logger LOGGER = LogManager.getLogger(BookDao.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String namespace = "com.goodee.library.book_mapper.";
	
	public int creatBook(BookDto dto) {
		LOGGER.info("도서 정보 데이터 베이스 추가");
		int result = 0;
		try {
			result = sqlSession.insert(namespace + "createBook",dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
