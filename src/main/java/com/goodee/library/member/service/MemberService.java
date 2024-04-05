package com.goodee.library.member.service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

	public Map<String, String> loginMember(MemberDto dto, HttpSession session) {
		LOGGER.info("로그인 결과 처리");
		// 1. res_code가 404이고 res_msg가 "오류가 발생하였습니다"인 Map<String, String> 타입인 변수 map 생성
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생하였습니다");
		
		// 2. dao에게 MemberDto 정보 전달
		MemberDto loginedMember = dao.selectMemberOne(dto);
		
		if (loginedMember != null) {
			
			if("Y".equals(loginedMember.getM_flag())) { // 지정할 수 있는 값을 앞에 넣어야 한다 (NullPointerException 예방)
				// 로그인 성공 시점
				session.setAttribute("loginedMember", loginedMember);
				session.setMaxInactiveInterval(60*30);
				
				map.put("res_code", "200");
				map.put("res_msg", loginedMember.getM_name()+"님 환영합니다");
				
			}else {
				map.put("res_code", "409");
				map.put("res_msg", "이미 탈퇴한 회원입니다");
			}
		}
		// 3. dao가 데이터베이스에서 로그인 성공한 회원 정보 return
		// 4. 로그인 성공한 회원 정보가 null이 아니라면 res_code 200으로
		return map;
	}

	public List<MemberDto> selectMemberAll() {
		LOGGER.info("회원 목록 조회 요청");
		// dao에게 List<MemberDto>에게 정보 전달 받아와서
		List<MemberDto> resultList = new ArrayList<MemberDto>();
		resultList = dao.selectMemberAll();
		// controller에게 return 해주기
		return resultList;
	}

	// updateMember 메소드
	// 1. map 구성(res_code, res_msg)
	// -> 상황에 따라 다르게 구성(200, 404)
	// 2. dao에게 회원 정보 수정 요청
	// -> where 절 잊지 말자!!!
	// 3. session 셋팅 다시
	// -> m_no 기준으로 회원정보 조회 MemberDto
	// -> loginMember 메소드 참고
	
	public Map<String, String> updateMember(MemberDto dto, HttpSession session) {
		LOGGER.info("회원 정보 수정 요청");
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "회원 정보 수정 중 오류 발생"); // 실패, 성공 사용하지 않는 추세 -> 오류 발생
		
		if (dao.updateMember(dto) > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "회원 정보가 수정되었습니다");
			
			// m_no를 기준으로 회원 정보를 조회
			MemberDto updatedMember = dao.selectMemberByNO(dto.getM_no());
			session.setAttribute("loginedMember", updatedMember);
			session.setMaxInactiveInterval(60*30);
		}
		return map;
	}

	// 1. deleteMember 메소드 구성
	// 2. return Map -> 초기값 설정 후 결과에 따라서 변화
	// 3. dao에게 회원 정보 업데이트 요청
	// 4. tbl_member m_flag 컬럼 생성(기본값 Y, 탈퇴 요청 시 N)
	
	public Map<String, String> deleteMember(long m_no, HttpSession session) {
		LOGGER.info("회원 정보 삭제 요청");
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "회원 삭제 중 오류 발생");
		
		if (dao.deleteMember(m_no) > 0) {
			map.put("res_code", "200");
			map.put("res_msg", "회원 정보가 삭제되었습니다");
			
			session.invalidate();
		}
		return map;
	}
	
}
