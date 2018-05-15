package com.cn.hnust.dao;

import java.util.List;

import com.cn.hnust.been.Comment;

public interface CommentMapper {

	List<Comment> selectcommentByname(String search);
}
