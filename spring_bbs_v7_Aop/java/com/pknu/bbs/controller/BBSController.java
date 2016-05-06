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

//Controller �� ��û�� �������� Service�� �и� ��Ŵ 
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
	//@RequestParam -> pageNum �� String�ε� �ڵ����� int�� ��ȯ ���� �ش�. 
	//(@RequestParam("pageNum") int pageNum) pageNum ���� ���� ������ @RequestParam() int pageNum���� ��� ����
	public ModelAndView list(@RequestParam("pageNum") int pageNum) {
		System.out.println();
		return  bbsService.list(pageNum);
		
	}
	
	@Transactional
	@RequestMapping("/masterList.bbs")
	//@RequestParam -> pageNum �� String�ε� �ڵ����� int�� ��ȯ ���� �ش�. 
	//(@RequestParam("pageNum") int pageNum) pageNum ���� ���� ������ @RequestParam() int pageNum���� ��� ����
	public ModelAndView masterList(@RequestParam("pageNum") int pageNum) {
		System.out.println();
		return  bbsService.masterList(pageNum);
		
	}
	
	//void �� return�ɶ� RequestMapping�� view�� �ȴ�.
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
	
		//spring�� �ڵ����� name�Ӽ��� ���� �ֵ��� article�� �ִ´�.
		//id�� session�� �ֱ� ������ session���� ���� �Է����ش�.
		article.setId((String)session.getAttribute("id"));	
		bbsService.insertArticle(article,mRequest);
		System.out.println("[ "+session.getAttribute("id")+" ] wrote a new article. check it out man~");
		System.out.println("[ "+session.getAttribute("id")+" ] wrote a new article. check it out man~");
		System.out.println("[ "+session.getAttribute("id")+" ] wrote a new article. check it out man~");
	return "redirect:/list.bbs?pageNum=1";

	}
	
//	@RequestMapping("/login.bbs")
//	public ModelAndView bbs(String id, String pass, String loginPath, HttpSession session){
////		���̵�, �н����带 �޾Ƽ� BBSOracleDao�� LogInCheck()�޼ҵ带 �̿��Ͽ� �α��� üũ
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
			System.out.println("�н����� Ʋ����..");
		}else{
			System.out.println("����");
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
//		pageNum�� @ModelAttribute�� ��� �����Ƿ� ����� ���� ���޵ȴ�.
		return bbsService.getArticle(articleNum,fileStatus);
	}

	
	
	
	
	

	@RequestMapping(value="/replyForm.bbs")
	/*
	 * String�� ���� ������ @ModelAttribute�� ��ü������ ����� �� �ִ�.
	 * int�� �����ڰ� ���� ������(���� static �޼ҵ�) String���� �޾Ƽ� ����. {���} */
	public String replyFormArticle(@ModelAttribute("depth") String depth,
					@ModelAttribute("pos") String pos,
					@ModelAttribute("groupId") String groupId,
					@ModelAttribute("pageNum") String pageNum) {
		
	// @ModelAttribute�� �ڵ����� ModelAndView�� addObject���ص� �ɾ��ش�.
	// return���� ModelAndView�϶��� �ȵǴ� ��������
		
		
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
//			�Ʒ��� ���� ���ÿ��� ������ ��, Integer�� set,get �޼ҵ尡 ����
//			@ModelAttribute("fileNumList") ArrayList<Integer> fileNumList,
//			UpdateDto�� ���� set,get �޼ҵ带 ����ؾ���
//			jsp���� �������� name �Ӽ��� ������ ���� �ѿ��� 
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
