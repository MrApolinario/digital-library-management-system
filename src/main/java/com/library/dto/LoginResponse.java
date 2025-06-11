package com.library.dto;

public record LoginResponse(
	    String token,
	    Long userId,
	    String email
	) {}