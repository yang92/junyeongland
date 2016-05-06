package com.pknu.comment.service;

import java.util.List;

import com.pknu.comment.dto.CommentDto;

public interface CommentService {
	public List<CommentDto> commentWrite(CommentDto comment);
	public List<CommentDto> commentRead(int articleNum, int commentRow);
}
