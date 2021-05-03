package com.spring.main.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.main.dto.AdminDTO;
import com.spring.main.service.AdminService;

@Controller
public class AdminController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired AdminService service;
	
	//이 달의 리뷰왕 리스트 
	@RequestMapping(value = "/ReviewKing", method = RequestMethod.GET)
	public ModelAndView ReviewKing() {
		logger.info("이달의리뷰왕 요청");
		return service.reviewkingList();
	}
	
	//이 달의 리뷰왕 선정
	@RequestMapping(value = "/selectKing", method = RequestMethod.POST)
	public String selectKing(@RequestParam  HashMap<String, Object> tdArr) {
		logger.info("이달의리뷰왕: "+tdArr);	
		
		//id와 cnt 분리
		logger.info(""+tdArr.get("tdArr[0][id]"));
		ArrayList<String> idList = new ArrayList<String>();
		idList.add((String) tdArr.get("tdArr[0][id]"));
		idList.add((String) tdArr.get("tdArr[1][id]"));
		idList.add((String) tdArr.get("tdArr[2][id]"));
		logger.info("idList: "+idList);
		
		ArrayList<String> cntList = new ArrayList<String>();
		cntList.add((String) tdArr.get("tdArr[0][cnt]"));
		cntList.add((String) tdArr.get("tdArr[1][cnt]"));
		cntList.add((String) tdArr.get("tdArr[2][cnt]"));
		logger.info("cntList: "+cntList);
		
		int success = service.selectKing(idList,cntList); 
		logger.info("success: "+success);
		return "redirect:/ReviewKing";
	}
	
	//리뷰왕 선정 이미 했으면 다음 달 까지 버튼 숨김
	@RequestMapping(value = "/hideBtn", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> hideBtn() {
		logger.info("버튼숨김 요청");
		return service.hideBtn();
	}
	
	//리뷰 신고리스트
	@RequestMapping(value = "/ReportList", method = RequestMethod.GET)
	public ModelAndView ReportList() {
		logger.info("신고리스트 요청");
		return service.ReportList();
	}
	
	//블라인드 리스트
	@RequestMapping(value = "/BlindList", method = RequestMethod.GET)
	public ModelAndView BlindList() {
		logger.info("블라인드리스트 요청");
		return service.BlindList();
	}
	
	//신고 상세보기
	@RequestMapping(value = "/reportDetail", method = RequestMethod.GET)
	public String reportDetail(Model model, @RequestParam int idx) {
		logger.info("신고 상세보기 할 idx: "+idx);
		String page = "redirect:/ReviewKing";
		AdminDTO dto = service.reportDetail(idx);
		if(dto != null) {
			page = "adminReport_detail";
			model.addAttribute("detail",dto);
		}
		return page;
	}
	
	//블라인드 사유 페이지 이동
	@RequestMapping(value = "/blindReason", method = RequestMethod.GET)
	public String blindReason(Model model, @RequestParam int reportIdx, @RequestParam int reviewIdx) {
		logger.info("블라인드 사유 입력: "+reportIdx+"/"+reviewIdx);
		AdminDTO dto = service.blindReason(reportIdx,reviewIdx);
		if(dto != null) {
			model.addAttribute("info", dto);
		}
		return "adminReport_blindReason";
	}
	
	//블라인드 사유 입력
	@RequestMapping(value = "/blindReasonTxt", method = RequestMethod.POST)
	public ModelAndView blindReasonTxt(@RequestParam HashMap<String, Object> params) {
		logger.info("블라인드 사유 입력: "+params);
		return service.blindReasonTxt(params);
	}
	
	//신고리스트 무시
	@RequestMapping(value = "/ignore", method = RequestMethod.GET)
	public ModelAndView ignore(@RequestParam int reportIdx, RedirectAttributes attr) {
		logger.info("무시할 신고번호: "+reportIdx);
		return service.ignore(reportIdx,attr);
	}
	
	//블라인드 해제
	@RequestMapping(value = "/blindRemove", method = RequestMethod.GET)
	public ModelAndView blindRemove(@RequestParam int blindIdx, RedirectAttributes attr) {
		logger.info("해제할 블라인드 번호: "+blindIdx);
		return service.blindRemove(blindIdx,attr);
	}
	
}
