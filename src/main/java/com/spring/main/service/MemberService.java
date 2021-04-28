package com.spring.main.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.spring.main.dao.MemberDAO;
import com.spring.main.dto.MemberDTO;

@Service
public class MemberService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired MemberDAO dao;
	String page="";
	String msg="";
	
	public int join(HashMap<String, String> params) {
		String plain = params.get("userPw");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encrypt = encoder.encode(plain);
		logger.info(plain+"=>"+encrypt);
		params.put("userPw", encrypt);	
		
		return dao.join(params);
	}

	public String memOverlay(String id) {
		String str ="";
		int idcheck = dao.memOverlay(id);
		if(idcheck==1) {
			str = "no";
		}else {
			str = "yes";
		}
		return str;
	}
	
	public boolean login(HashMap<String, String> params) {
		String encrypt_pass = dao.login(params.get("id"));
		String pw = params.get("pw");
		logger.info(pw+"=="+encrypt_pass);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(pw, encrypt_pass);
	}
	
//------------계정찾기관련 영역----------------------------------------
	
	public ModelAndView findId(String name, int phone,HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		String id = dao.findId(name, phone);
		page = "memFindId";
		msg = "해당 정보와 일치하는 아이디는 없습니다.";
		if(id!=null) {
			page="memReId";
			session.setAttribute("name", name);
			session.setAttribute("findId", id);
		}
		mav.addObject("msg", msg);
		mav.setViewName(page);
		return mav;
	}

	public ModelAndView findPw(HashMap<String, String> params,HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		String id = dao.findPw(params);
		page = "memFindPw";
		msg = "입력한 값을 다시 확인해주세요.";
		if(id!=null) {
			page="memNewPw";
			session.setAttribute("findId", id);
		}
		mav.addObject("msg", msg);
		mav.setViewName(page);
		return mav;
	}

	public ModelAndView newPw(String newPw,HttpSession session) {
		
		logger.info("새로바꿀 비밀번호:"+newPw);	
		String id = (String) session.getAttribute("findId");
		logger.info("해당 id:"+id);
		MemberDTO dto = new MemberDTO();
		dto.setId(id); //dto에 해당 id를 넣는다.

		logger.info("변경전 비밀번호:"+dao.login(id)); // 1. 현재 비밀번호 확인
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		ModelAndView mav = new ModelAndView();
		
		page = "memNewPw";
		if(dao.login(id)!=null) { //2.비밀번호가 null이 아니면 
			String encrypt = encoder.encode(newPw);
			dto.setPw(encrypt); //새로운 비밀번호를 dto에 담는다(암호화된)
			dao.newPw(dto); //담은 비밀번호를 dao에 다시 담는다
			page = "memLogin";
			msg= "비밀번호가 재설정되었습니다. 로그인 해주시기 바랍니다.";
		}	
		logger.info("변경 후 비밀번호:"+dto.getPw());
		mav.addObject("msg", msg);
		mav.setViewName(page);
		session.removeAttribute("findId"); //id 확인 후 세션값 지움 
		return mav; 
	}

	//------------마이라이브러리 내 영역----------------------------------------	

	public String mylib_mem(String pw) {
		
		return null;
	}
	
	public MemberDTO myLib_UpdateForm(HttpSession session) {
		String id = (String) session.getAttribute("loginId");
		logger.info("수정할 회원 id:"+id);
		return dao.myLib_UpdateForm(id);
	}
	
	public ModelAndView memUpdate(@ModelAttribute MemberDTO dto,HttpSession session) {
		logger.info("수정할 params:"+dto.getName()+"/"+dto.getPhone()+"/"+dto.getPhone());

		ModelAndView mav = new ModelAndView();
		int success = dao.memUpdate(dto);
		page = "redirect:/myLib_UpdateForm";
		msg = "회원정보 수정에 실패하였습니다.";
		
		if(success>0) {
			dto.getId();
			msg = "회원정보를 수정하였습니다.";
			page = "myLib_UpdateForm";
		}
		logger.info("수정성공여부:"+success);
		mav.addObject("msg", msg);
		mav.setViewName(page);
		return mav;
	}
	
	
	public ModelAndView memWithdraw(HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		logger.info("탈퇴할 회원 id:"+loginId);
		
		ModelAndView mav = new ModelAndView();
		int success = dao.memWithdraw(loginId);
		
		page = "redirect:/myLib_Update";
		if(success>0) {
			page="main";
			msg = "회원탈퇴시 회원님의 모든 정보가 사라지며 복구 할 수 없습니다.\r\n" + 
					"그래도 탈퇴하시겠습니까?\r\n";
		}
		logger.info("탈퇴성공여부:"+success);
		mav.addObject("msg", msg);
		mav.setViewName(page);
		session.removeAttribute("loginId");
		return mav;
	}




	



}
