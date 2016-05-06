package com.pknu.bbs.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pknu.bbs.dto.BBSDto;
import com.pknu.bbs.dto.ContentDto;
import com.pknu.bbs.dto.FileDto;

public interface BBSDao {
	
	//These mothods need for connecting to DB
	
	public int getArticleCount();
	public List<BBSDto> getArticles(HashMap<String, Integer> hm);
	public List<BBSDto> getMasterArticles(HashMap<String, Integer> hm);
	
	public String loginCheck(String id);
	
	
	public void insertArticle(BBSDto article);
	public int getNextArticleNum();
	
	public BBSDto getArticle(int articleNum);
	
	
	
	public void getHit(int articleNum);
	
	public List<FileDto> getFiles(int articleNum);
	
//	public void updateFormArticle(int articleNum);
	
	
	
//	public ContentDto getArticleFileName(int articleNum);
	public void insertFile(FileDto article);
	
	public void deleteArticle(int articleNum);
	public List<String> deleteFile(int articleNum);
	

	public void replyArticle(BBSDto article);
	public void upPos(BBSDto article);
	
	public BBSDto getUpdateArticle(int articleNum);
	public ContentDto getUpdateFileArticle(int articleNum);
	
	public void updateArticle(BBSDto article);
	public void updateAllDelFile(int articleNum);
	public void someDelFile(ArrayList<Integer> fileNumList);
	
	
	public int getCommentCount(int articleNum);
	public String updateSomeDelFile(int fileNum);
	public int getFileListCount(int articleNum);
}
