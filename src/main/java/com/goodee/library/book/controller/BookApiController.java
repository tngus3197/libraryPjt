package com.goodee.library.book.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;
import com.goodee.library.book.util.UploadFileService;

@Controller
public class BookApiController {

	private static final Logger LOGGER = LogManager.getLogger(BookApiController.class);
	
	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	BookService bookService;
	
	// 1. Post방식
	// 2. url은 /book
	// 3. Dto, Multipartfile 객체
	// 4. json(requestBody (X), @requestParam (O))
	
	@ResponseBody
	@PostMapping("/book")
	public Map<String, String> createBook(BookDto dto, @RequestParam("file") MultipartFile file){ // inputtype file의 name값을 작성
		LOGGER.info("도서 등록 기능");
		// UploadFileService에 upload 메소드 구성
		// return 업로드 된 파일명
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 등록 중 오류가 발생했습니다");
		
		String savedFileName = uploadFileService.upload(file);
		if(savedFileName != null) {
			map.put("res_msg", "파일 업로드는 성공");
			dto.setB_thumbnail(savedFileName);
			map = bookService.createBook(dto);
		}else {
			map.put("res_msg", "파일 업로드 중 오류가 발생했습니다");
		}
		return map;
	}
	
	@ResponseBody
	@PostMapping("/book/{b_no}")
	public Map<String, String> bookEdit(BookDto dto, @RequestParam("file") MultipartFile file){
		LOGGER.info("도서 정보 수정 기능");
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 수정 중 에러 발생");
		
		if("".equals(file.getOriginalFilename()) == false) {
			// 1. 파일을 서버에 업로드 (새로운 파일)
			String savedFileName = uploadFileService.upload(file);
			
			if(savedFileName != null) {
				// 2. 기존 파일 삭제
				if(uploadFileService.delete(dto.getB_thumbnail())) {
					// 정상 삭제 상태
					// 3. b_thumbnail 정보 수정
					dto.setB_thumbnail(savedFileName);
				}else {
					map.put("res_msg", "기존 파일 삭제가 실패했습니다");
				}
			}else {
				map.put("res_msg", "파일 업로드 중 오류 발생");
			}
		}
		
		// 4. 도서 정보 수정
		// b_thumbnail 여부에 따라 쿼리가 달라짐
		map = bookService.editBookDetail(dto);
		
		return map;
	}
	
	// 도서 정보 삭제 메소드
	@ResponseBody
	@DeleteMapping("/book/{b_no}")
	public Map<String, String> bookDelete(@PathVariable("b_no") long b_no){
		LOGGER.info("도서 정보 삭제 기능");
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 삭제 중 에러 발생");
		String b_thumbnail = bookService.selectFile(b_no);
		
		// 1. 데이터 베이스에서 삭제
		map = bookService.deleteBook(b_no);

		// 2. 파일도 서버에서 삭제
		if(map.get("res_code").equals("200")) {			
			uploadFileService.delete(b_thumbnail);
		}
					
		// 3. 최종적으로 수행 결과를 프론트에게 전달
		return map;
		
	}
	
	
}
