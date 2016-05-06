package com.pknu.bbs.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.pknu.bbs.dto.BBSDto;
import com.pknu.bbs.dto.UpdateDto;

public interface BBSService {
	public ModelAndView list(int pageNum);
	public ModelAndView masterList(int pageNum);
	public int loginCheck(String id, String pass);
	public void insertArticle(BBSDto article, MultipartHttpServletRequest mRequest);
	public ModelAndView getArticle(int articleNum, int fileStatus);
	public void deleteArticle(int articleNum);
	public void replyArticle(BBSDto article, MultipartHttpServletRequest mRequest);
	
	public ModelAndView getUpdateArticle(int articleNum, int fileStatus);
	
	public ModelAndView updateArticle(MultipartHttpServletRequest mRequest,BBSDto article, UpdateDto updateDto);
//	public void updateFileArticle(ContentDto article, MultipartFile fname);
//	public void updateFormArticle(ContentDto article);
//	public ModelAndView replyForm(int depth, int pos, int groupId, int pageNum);
	
	public FileSystemResource download(HttpServletResponse res, String originFname, String storedFname);
}
