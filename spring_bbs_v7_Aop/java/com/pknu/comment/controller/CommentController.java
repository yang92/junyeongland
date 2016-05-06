package com.pknu.comment.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pknu.comment.dto.CommentDto;
import com.pknu.comment.service.CommentService;

@Controller
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@RequestMapping("/commentWrite.comment")
	//return 값이 jSON이면 @ResponseBody를 써준다.
	@ResponseBody
	public List<CommentDto> commentWrite(HttpSession session,CommentDto comment, int articleNum){
		comment.setId((String)session.getAttribute("id"));
		System.out.println("[ "+session.getAttribute("id")+" ] did comment on a article [ "+articleNum+" ]");
		return commentService.commentWrite(comment);
	}
	
	@RequestMapping("/commentRead.comment")
	public @ResponseBody List<CommentDto> commentRead(int articleNum, int commentRow){
		return commentService.commentRead(articleNum, commentRow);
	
	}
	
	
	
	
	
	
	
	
	
//------------------MappingJacksonJsonView이용 해서 JSON처리-----------------------
//           @ResponseBody가 없어졌다 그래서 Model
//	@RequestMapping("/commentWrite.comment")
//	public ModelAndView commentWrite(HttpSession session,CommentDto comment){
//		comment.setId((String)session.getAttribute("id"));
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("comment", commentService.commentWrite(comment));
//		//comment-context에 bean 의 ID가 JSON인 BEAN을 찾는다.
//		mav.setViewName("JSON");
//		return mav;
//	}
//	
//
//	
//	@RequestMapping("/commentRead.comment")
//	public ModelAndView commentRead(int articleNum, int commentRow){
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("comment", commentService.commentRead(articleNum, commentRow));
//		//comment-context에 bean 의 ID가 JSON인 BEAN을 찾는다.
//		mav.setViewName("JSON");
//		return mav;
//	}

}
