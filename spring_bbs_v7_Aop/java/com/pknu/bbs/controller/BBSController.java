package com.pknu.bbs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.pknu.bbs.dto.BBSDto;
import com.pknu.bbs.dto.UpdateDto;
import com.pknu.bbs.service.BBSService;
import com.pknu.bbs.util.LoginStatus;

//Controller 는 요청만 받을려고 Service와 분리 시킴 
@Controller
public class BBSController {

	@Autowired
	BBSService bbsService;
	ModelAndView mav;	
	
	
	@RequestMapping("/fake.bbs")
	public String face(){
		return "fake";
	}
	
	@RequestMapping("/location.bbs")
	public String location(){
		return "location";
	}
	
	@Transactional
	@RequestMapping("/list.bbs")
	//@RequestParam -> pageNum 이 String인데 자동으로 int로 변환 시켜 준다. 
	//(@RequestParam("pageNum") int pageNum) pageNum 변수 명이 같으면 @RequestParam() int pageNum으로 사용 가능
	public ModelAndView list(@RequestParam("pageNum") int pageNum) {
		System.out.println();
		return  bbsService.list(pageNum);
		
	}
	
	@Transactional
	@RequestMapping("/masterList.bbs")
	//@RequestParam -> pageNum 이 String인데 자동으로 int로 변환 시켜 준다. 
	//(@RequestParam("pageNum") int pageNum) pageNum 변수 명이 같으면 @RequestParam() int pageNum으로 사용 가능
	public ModelAndView masterList(@RequestParam("pageNum") int pageNum) {
		System.out.println();
		return  bbsService.masterList(pageNum);
		
	}
	
	//void 가 return될때 RequestMapping이 view가 된다.
	@RequestMapping(value="/writeForm.bbs")
	public String writeForm(HttpSession session) {
		return "writeForm";
	}
	
	@RequestMapping("/loginForm.bbs")
	public String loginForm(){
		return "writeLogin";
	}
	
	@RequestMapping(value="/write.bbs", method=RequestMethod.POST)
	public String write(BBSDto article,HttpSession session,  MultipartHttpServletRequest mRequest) {
	
		//spring이 자동으로 name속성과 같은 애들을 article에 넣는다.
		//id는 session에 있기 때문에 session에서 직접 입력해준다.
		article.setId((String)session.getAttribute("id"));	
		bbsService.insertArticle(article,mRequest);
		System.out.println("[ "+session.getAttribute("id")+" ] wrote a new article. check it out man~");
		System.out.println("[ "+session.getAttribute("id")+" ] wrote a new article. check it out man~");
		System.out.println("[ "+session.getAttribute("id")+" ] wrote a new article. check it out man~");
	return "redirect:/list.bbs?pageNum=1";

	}
	
//	@RequestMapping("/login.bbs")
//	public ModelAndView bbs(String id, String pass, String loginPath, HttpSession session){
////		아이디, 패스워드를 받아서 BBSOracleDao의 LogInCheck()메소드를 이용하여 로그인 체크
//			
//		int result=0;
//		result = bbsService.loginCheck(id, pass);
//		
//		mav = new ModelAndView();
//		mav.addObject("loginStatus", result);
//		
//		return mav;
//		} 
	

	@RequestMapping("/login.bbs")
	public String login(HttpSession session, String id, String pass, String loginPath){
		int result=0;		
		result=bbsService.loginCheck(id,pass);
		
		String view=null;
		if(result==LoginStatus.OK){	
			session.setAttribute("id", id);
			if(loginPath.equals("")){
				view="redirect:/list.bbs?pageNum=1";
			}else{
				view="redirect:/writeForm.bbs";
			}			
		}else if(result==LoginStatus.PASS_FAIL){
			System.out.println("패스워드 틀릿네..");
		}else{
			System.out.println("꺼져");
		}
		
		System.out.println("[ "+id+" ] is now on your website! ");
		return view;
	}
	
	@RequestMapping("/logout.bbs")
	public String logout(HttpServletRequest req){
		req.getSession().setAttribute("id",null);
		String loginPath = req.getParameter("loginPath");
		loginPath = "";
		return "redirect:/list.bbs?pageNum=1";
	}


	
	
	
	
	@RequestMapping(value="/content.bbs")
	public ModelAndView content(int articleNum,
			@ModelAttribute("pageNum") String pageNum, int fileStatus, HttpSession session) {
		System.out.println("[ "+session.getAttribute("id")+" ] is reading a article [ "+articleNum+" ]");
//		pageNum은 @ModelAttribute를 사용 했으므로 뷰까지 값이 전달된다.
		return bbsService.getArticle(articleNum,fileStatus);
	}

	
	
	
	
	

	@RequestMapping(value="/replyForm.bbs")
	/*
	 * String을 쓰는 이유는 @ModelAttribute은 객체에서만 사용할 수 있다.
	 * int는 생성자가 없기 때문에(전부 static 메소드) String으로 받아서 쓴다. {편법} */
	public String replyFormArticle(@ModelAttribute("depth") String depth,
					@ModelAttribute("pos") String pos,
					@ModelAttribute("groupId") String groupId,
					@ModelAttribute("pageNum") String pageNum) {
		
	// @ModelAttribute은 자동으로 ModelAndView에 addObject안해도 심어준다.
	// return값이 ModelAndView일때는 안되니 쓰지말자
		
		
		return "replyForm";
	}
	
	@RequestMapping(value="/reply.bbs")
	public String replyArticle(BBSDto article, MultipartHttpServletRequest mRequest, String pageNum, HttpSession session) {
		article.setId((String)session.getAttribute("id"));
		bbsService.replyArticle(article, mRequest);
		
		return "redirect:/list.bbs?pageNum="+pageNum;
	}

	 
	@RequestMapping(value="/delete.bbs")
	public String deleteArticle(@RequestParam("articleNum") int articleNum, String pageNum){
		bbsService.deleteArticle(articleNum);
		
		return "redirect:/list.bbs?pageNum="+pageNum;
	}
	
	@RequestMapping(value="/update.bbs", method=RequestMethod.GET)
	public ModelAndView getUpdateArticle(@ModelAttribute("articleNum") int articleNum,
			@ModelAttribute("fileStatus") int fileStatus, 
			@ModelAttribute("pageNum") String pageNum){
		mav = new ModelAndView();
		return bbsService.getUpdateArticle(articleNum,fileStatus);
	}
	
	@RequestMapping(value="/update.bbs", method=RequestMethod.POST)
	public ModelAndView updateArticle(BBSDto article,
//			아래와 같이 사용시에는 오류가 남, Integer는 set,get 메소드가 없음
//			@ModelAttribute("fileNumList") ArrayList<Integer> fileNumList,
//			UpdateDto를 만들어서 set,get 메소드를 사용해야지
//			jsp에서 복수개의 name 속성을 가지는 값이 넘오옴 
			UpdateDto updateDto,
			MultipartHttpServletRequest mRequest, 
			@ModelAttribute("pageNum") String pageNum){		
		mav= new ModelAndView();		
		
		return bbsService.updateArticle(mRequest,article,updateDto);		
		
		
	}
	
	
	@RequestMapping(value="/download.bbs")
	@ResponseBody
	public FileSystemResource download(HttpServletResponse res, String originFname, String storedFname){
		return bbsService.download(res, originFname, storedFname);
	}
	
	
}
