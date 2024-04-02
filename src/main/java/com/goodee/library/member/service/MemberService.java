package com.goodee.library.member.service;

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
		int result = 0;
		if(dao.idDoubleCheck(dto.getM_id())>0) {
			// 회원 가입 불허
			
		}else {
			result = dao.createMember(dto);
			if(result > 0) {
				// 회원 가입 성공
			}else {
				// 회원 가입 실패
			}
		}
		
		// 2. insert 결과를 가지고 map 데이터 재구성
		
	}
}
