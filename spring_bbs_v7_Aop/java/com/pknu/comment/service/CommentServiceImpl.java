package com.pknu.comment.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pknu.comment.dao.CommentDao;
import com.pknu.comment.dto.CommentDto;

@Service
public class CommentServiceImpl implements CommentService{
	
	//CommentDao¸¦ DI¹Þ°í~
	@Autowired
	CommentDao commentDao;
	
	
	@Override
	public List<CommentDto> commentWrite(CommentDto comment) {
		commentDao.commentWrite(comment);
		return commentRead(comment.getArticleNum(), 10);
	}
	

	@Override
	public List<CommentDto> commentRead(int articleNum, int commentRow) {
		HashMap<String, Integer> hm = new HashMap<>();
		hm.put("articleNum", articleNum);
		hm.put("commentRow", commentRow);
		return commentDao.commentRead(hm);
	}

}
