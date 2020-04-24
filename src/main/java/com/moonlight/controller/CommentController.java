package com.moonlight.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moonlight.entity.Comment;

@RestController
public class CommentController {

	@PostMapping("/comment")
	public String postComment(@Valid Comment comment) {
		return "success";
	}
}
