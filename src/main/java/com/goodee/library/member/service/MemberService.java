package com.goodee.library.member.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.library.member.dao.MemberDao;
import com.goodee.library.member.dto.MemberDto;

@Service
public class MemberService {
	
	private static final Logger LOGGER = LogManager.getLogger(MemberService.class);
	
	@Autowired
	MemberDao dao;
	
	public Map<String, String> createMember(MemberDto dto) {
		LOGGER.info("회원 가입 결과 처리");
		// 1. dao에게 data insert 요청
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생했습니다");
		
		int result = 0;
		if(dao.idDoubleCheck(dto.getM_id())>0) {
			// 회원 가입 불허
			map.put("res_code", "409");
			map.put("res_msg", "중복된 아이디입니다");
		}else {
			result = dao.createMember(dto);
			if(result > 0) {
				// 회원 가입 성공
				map.put("res_code", "200");
				map.put("res_msg", "회원가입 성공입니다");
			}
		}
		// 2. insert 결과를 가지고 map 데이터 재구성
		return map;
	}

	public Map<String, String> loginMember(MemberDto dto) {
		LOGGER.info("로그인 결과 처리");
		// 1. res_code가 404이고 res_msg가 "오류가 발생하였습니다"인 Map<String, String> 타입인 변수 map 생성
		// 2. dao에게 MemberDto 정보 전달
		// 3. dao가 데이터베이스에서 로그인 성공한 회원 정보 return
		// 4. 로그인 성공한 회원 정보가 null이 아니라면 res_code 200으로
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생하였습니다");
		return map;
	}
	
	public MemberDto SelectMemberOne(MemberDto dto) {
		return dto;
		
	}
}
