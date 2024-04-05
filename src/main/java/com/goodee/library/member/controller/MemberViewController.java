package com.goodee.library.member.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.goodee.library.member.dto.MemberDto;
import com.goodee.library.member.service.MemberService;

@Controller
public class MemberViewController {
	
	private static final Logger LOGGER = LogManager.getLogger(MemberViewController.class);	
	
	@Autowired
	MemberService service;
	
	@GetMapping("/join")
	public String openJoin() {
		LOGGER.info("회원가입 페이지 이동");
		return "member/join";
	}
	
	@GetMapping("/login")
	public String openLogin() {
		LOGGER.info("로그인 페이지 이동");
		return "member/login";
	}
	
	// Restful 상습 면접 질문
	// url에 절대 행동 정보를 넣지 않는다
	// 목록:			/객체(Get)
	// 상세(수정):		/객체/pk(Post, Put)
	// 삭제:			/객체/pk(Delete)
	// 생성:			/객체(Post)
	
	@GetMapping("/member")
	public String selectMemberAll(Model model) {
		LOGGER.info("회원 리스트 페이지 이동");
		
//		1. 회원 목록 정보
		List<MemberDto> resultList = new ArrayList<MemberDto>();
		resultList = service.selectMemberAll();
		
//		2. 회원 목록 정보 전달
//		ModelAndView
//		Model
		model.addAttribute("resultList", resultList);
				
//		3. 화면 이동
		return "member/list";
	}
	
	@GetMapping("/member/{m_no}")
	public String editMemberOne(@PathVariable("m_no") long m_no, HttpSession session) {
		LOGGER.info("회원 수정 화면으로 이동");
		// 세션 정보 만료 여부 확인
		// 1. url 입력 창에 주소로 접근 방지
		// 2. 시간이 지나서 세션 정보가 만료되지는 않았는지 확인해야 한다
//		MemberDto loginedMember = (MemberDto)session.getAttribute("loginedMember");
//		String nextpage = "redirect:/login";
//		if(loginedMember != null) {
//			nextpage = "member/edit";
//		}
//		return nextpage;
		return "member/edit";
	}
	
	
}
