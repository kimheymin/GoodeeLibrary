package com.spring.main.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.main.dao.LibraryInfoDAO;
import com.spring.main.dto.LibraryInfoDTO;

@Service
public class LibraryInfoService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired LibraryInfoDAO dao;
	
	
	public HashMap<String, Object> list_infoNotice(int pagePerCnt, int page, HashMap<String, Object> params) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String txt = (String) params.get("search");
		
		if(txt != "" || !txt.equals("")) {
			logger.info("검색값 있음");
			int end =page*pagePerCnt;
			int start= end - pagePerCnt+1;
			params.put("start", start);
			params.put("end", end);
			logger.info("값"+params);
			map.put("list", dao.searchList(params));
			int allCnt = dao.searchCount(params);
			logger.info("올카"+allCnt);
			int range=(int) (allCnt%pagePerCnt >0 ?Math.ceil(allCnt/pagePerCnt)+1:Math.ceil(allCnt/pagePerCnt));
			logger.info("렌지"+range);
			map.put("range", range);

		} else{
			logger.info("검색값 없음");
			int end =page*pagePerCnt;
			int start= end - pagePerCnt+1;
			map.put("list", dao.list(start,end));	
			int allCnt = dao.allCount();
			logger.info("올카"+allCnt);
			int range=(int) (allCnt%pagePerCnt >0 ?Math.ceil(allCnt/pagePerCnt)+1:Math.ceil(allCnt/pagePerCnt));
			logger.info(""+range);
			map.put("range", range);
		}
		
		logger.info(""+page);
		return map;
	}
	
	public void detail_infoNotice(Model model, String idx) {
		logger.info("현재위치는 noticeDetail의 service 입니다");

		
		LibraryInfoDTO dto=dao.noticeDetail(idx);
		
		model.addAttribute("dto",dto);	
	}

	public void faq_infoNotice(Model model) {
		ArrayList<LibraryInfoDTO> dto = dao.faq_infoNotice();
		model.addAttribute("dto",dto);
	}

	public ModelAndView faqSearch_infoNotice(HashMap<String, Object> params, RedirectAttributes rAttr) {
		logger.info(""+params);
		
		ArrayList<LibraryInfoDTO> dto = dao.faqSearch_infoNotice(params);
		ModelAndView mav = new ModelAndView();
		 String page ="redirect:/faq";
		 String msg ="찾으시는 자주묻는 질문이 없습니다."; 
		if(dto.size() >0) {
			msg="찾으시는 질문입니다."; 
			page="FAQ";
			mav.addObject("dto",dto);
			mav.addObject("msg",msg);
		}
		rAttr.addFlashAttribute("msg",msg);
		mav.setViewName(page);
		return mav;
		
	}

	public HashMap<String, Object> question_pageing(int page) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;
		
		int end =page*pagePerCnt;
		int start= end - pagePerCnt+1;
		map.put("list", dao.questionAll_List(start, end));
		int allCnt = dao.questionAll_Count();
		int range=(int) (allCnt%pagePerCnt >0 ?Math.ceil(allCnt/pagePerCnt)+1:Math.ceil(allCnt/pagePerCnt));
		map.put("range", range);
		
		return map;
	}

	public HashMap<String, Object> question_bookPageing(int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;
		
		int end =page*pagePerCnt;
		int start= end - pagePerCnt+1;
		int status =2;
		map.put("list", dao.questionList(start,end,status));
		int allCnt = dao.question_bookCount();
		int range=(int) (allCnt%pagePerCnt >0 ?Math.ceil(allCnt/pagePerCnt)+1:Math.ceil(allCnt/pagePerCnt));
		map.put("range", range);
		
		return map;
	}
	
	public HashMap<String, Object> question_roomPageing(int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;
		
		int end =page*pagePerCnt;
		int start= end - pagePerCnt+1;
		int status =1;
		map.put("list", dao.questionList(start,end,status));
		int allCnt = dao.question_roomCount();
		int range=(int) (allCnt%pagePerCnt >0 ?Math.ceil(allCnt/pagePerCnt)+1:Math.ceil(allCnt/pagePerCnt));
		map.put("range", range);
		
		return map;
	}

	public HashMap<String, Object> question_servicePageing(int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;
		
		int end =page*pagePerCnt;
		int start= end - pagePerCnt+1;
		int status =3;
		map.put("list", dao.questionList(start,end,status));
		int allCnt = dao.question_serviceCount();
		int range=(int) (allCnt%pagePerCnt >0 ?Math.ceil(allCnt/pagePerCnt)+1:Math.ceil(allCnt/pagePerCnt));
		map.put("range", range);
		
		return map;
	}

	public HashMap<String, Object> question_guitarPageing(int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;
		
		int end =page*pagePerCnt;
		int start= end - pagePerCnt+1;
		int status =4;
		map.put("list", dao.questionList(start,end,status));
		int allCnt = dao.question_guitarCount();
		int range=(int) (allCnt%pagePerCnt >0 ?Math.ceil(allCnt/pagePerCnt)+1:Math.ceil(allCnt/pagePerCnt));
		map.put("range", range);
		
		return map;
	}

	public ModelAndView questionWriting(HashMap<String, Object> params) {
		ModelAndView mav = new ModelAndView();
		logger.info("params의값"+params);
		/* loginId=, type=Q003, subject=가입인사드립니다/., content=sdfsafsdaf, false=FALSE */
		int success = dao.questionWriting(params);
		String msg="글작성에실패하였씁니다.";
		if(success > 0) {
			msg="글작성에 성공하였씁니다.";
		}
		mav.addObject("msg", msg);
		mav.setViewName("Question");
		return mav;
	}

	public ModelAndView questionDetail(int idx, HttpSession session, RedirectAttributes rAttr) {
		ModelAndView mav = new ModelAndView();
		String loginId = (String) session.getAttribute("loginId");
		 HashMap<String,Object> map=dao.questionDetail(idx);
		 logger.info(""+map);
		 logger.info(""+map.get("SHOWSTATUS"));
		 logger.info(""+map.get("ID"));
		 logger.info(""+loginId);
		 String msg ="";
		 String page ="";
		if(map.get("SHOWSTATUS").equals("TRUE")) {
			msg="전체공개입니다.";
			page="questionDetail";
		}else {
			if(map.get("ID").equals(loginId)) {
				msg="비공개글이지만 작성자이기에 보여집니다.";
				page="questionDetail";
			}else {
				msg="볼수 있는권한이 있지않습니다.";
				page="redirect:/Question";
				rAttr.addFlashAttribute("msg",msg);
			}
		}
		mav.addObject("msg", msg);
		mav.setViewName(page);
		return mav;
	}

	public ModelAndView fileUpload(MultipartFile file, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		File dir = new File("C:/upload/Library");
		if(!dir.exists()) {
			dir.mkdir();
		}
		String fileName = file.getOriginalFilename();
		
		String newFileName = System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));
		
		logger.info("팔넴"+fileName+"밀세컨더한팔넴"+newFileName);
		
		try {
			byte[] bytes = file.getBytes();
			Path filePath = Paths.get("C:/upload/Library/"+newFileName);
			Files.write(filePath, bytes);
			HashMap<String, String> fileList = (HashMap<String, String>) session.getAttribute("fileList");
			
			fileList.put(newFileName, fileName);
			logger.info("파일수"+fileList.size());
			
			session.setAttribute("fileList", fileList);
			logger.info("fl : "+fileList);
			mav.addObject("path","/photo/"+newFileName); //다른사람들서버 설정 알려줘야함..
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mav.setViewName("uploadForm");
		
		return mav;
	}

	public HashMap<String, Object> fileDelete(String fileName, HttpSession session) {

		HashMap<String, Object> map	 = new	HashMap<String, Object>();
		
		File delFile = new File("C:/upload/Library"+fileName);
		logger.info("delete file:"+delFile);
		
		int success= 1;
		
		try {
			if(delFile.exists()) { 
				delFile.delete();  //있다면 삭제
			}else {
				logger.info("이미삭제된 파일 "); 
			}
			HashMap<String, String> fileList = (HashMap<String, String>) session.getAttribute("fileList");
			
			if(fileList.get(fileName) != null) { 
				fileList.remove(fileName);
				logger.info("삭제후남은파일"+fileList.size());
			}
			session.setAttribute("fileList", fileList);  //세션에서지워진것을 다시 set해서 넣어줘야함갱신개념
		} catch (Exception e) {
			success=0;
		} finally {
			map.put("success"	, success);
		}
		
		return map;
	}




}
