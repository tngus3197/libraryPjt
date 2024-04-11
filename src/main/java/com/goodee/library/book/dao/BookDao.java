package com.goodee.library.book.dao;

import java.util.ArrayList;
import java.util.List;

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
	
	private final String namespace = "com.goodee.library.bookMapper.";
	
	public int creatBook(BookDto dto) {
		LOGGER.info("도서 정보 데이터 베이스 추가");
		int result = 0;
		try {
			result = sqlSession.insert(namespace + "createBook", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int selectBookCount(String b_name) {
		LOGGER.info("도서 갯수 조회");
		int result = 0;
		try {
			result = sqlSession.selectOne(namespace + "selectBookCount", b_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<BookDto> selectBookList(BookDto dto) {
		LOGGER.info("전체 도서 목록 조회");
		List<BookDto> resultList = new ArrayList<BookDto>();
		try {
			resultList = sqlSession.selectList(namespace + "selectBookList", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public List<BookDto> selectBookListToday() {
		LOGGER.info("오늘 도서 목록 조회");
		List<BookDto> resultList = new ArrayList<BookDto>();
		try {
			resultList = sqlSession.selectList(namespace + "selectBookListToday");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public BookDto bookDetail(long b_no) {
		LOGGER.info("b_no 기준 도서 정보 조회");
		BookDto dto = new BookDto();
		try {
			dto = sqlSession.selectOne(namespace + "BookDetail", b_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public int editBookDetail(BookDto dto) {
		LOGGER.info("b_no 기준 멤버 업데이트");
		int result = 0;
		try {
			result = sqlSession.update(namespace + "editBookDetail", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int deleteBook(long b_no) {
		LOGGER.info("b_no 기준 도서 삭제");
		int result = 0;
		try {
			result = sqlSession.delete(namespace + "deleteBook",b_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String selectFile(long b_no) {
		LOGGER.info("b_no 기준 파일 조회");
		String result = "";
		try {
			result = sqlSession.selectOne(namespace + "selectFile", b_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
