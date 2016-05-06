package com.pknu.comment.dao;

import java.util.HashMap;
import java.util.List;

import com.pknu.comment.dto.CommentDto;

public interface CommentDao {
	public void commentWrite(CommentDto comment);
	public List<CommentDto> commentRead(HashMap<String, Integer> hm);
}
