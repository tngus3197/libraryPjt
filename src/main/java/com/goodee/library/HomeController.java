package com.goodee.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;

@Controller
public class HomeController {

	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);
	
	@Autowired
	BookService service;
	
	@GetMapping({"","/"})
	public String Home(Model model) {
		LOGGER.info("도서관 관리 시스템 시작");
		// /WEB-INF/views/home.jsp
		
		// 1. 도서 목록 조회
		// 2. List<BookDto>
		// 3. BookService 의존성 주입
		// 4. selectBookListToday();
		// 5. DATE_FORMAT(b_reg_date, '%Y-%m-%d') = CURDATE();
		
		List<BookDto> newBookList = new ArrayList<BookDto>();
		newBookList = service.selectBookListToday();
		model.addAttribute("newBookList", newBookList);
		
		for(BookDto dto : newBookList) {
			LOGGER.info(dto.getB_name());
		}
		
		return "home";
	}
	
}
