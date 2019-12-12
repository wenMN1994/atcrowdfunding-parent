package com.dragon.atcrowdfunding.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 
 * <p>Title: AccessDenidHandlerImpl</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年12月12日
 */
@Component
public class AccessDenidHandlerImpl implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		request.setAttribute("msg", accessDeniedException.getMessage());
		// 必须区别对待ajax和普通请求
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			// ajax请求
			String msg = "403";
			response.getWriter().write(msg);
		} else {
			// 普通请求
			request.getRequestDispatcher("/WEB-INF/jsp/error/403.jsp").forward(request, response);
		}
	}

}
