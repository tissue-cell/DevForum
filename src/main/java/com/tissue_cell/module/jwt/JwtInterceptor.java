package com.tissue_cell.module.jwt;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JwtServiceImpl jwtService;
	@Autowired
	private AuthorizationExtractor authExtractor;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println(">>> interceptor.preHandle 호출");
		System.out.println(">>> "+ request.getHeader(authExtractor.AUTHORIZATION));
//		System.out.println(">>> "+ request.getParameter(authExtractor.AUTHORIZATION));
        String token = authExtractor.extract(request, "Bearer");
//        String token = request.getParameter(authExtractor.AUTHORIZATION);
        
        if (StringUtils.isEmpty(token)) {
            return true;
        }

        if (!jwtService.validateToken(token)) {
        	System.out.println(">>> "+token);
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }

        Map<String,Object> sub = jwtService.getInfo(token);
        request.setAttribute("PAYLOAD", sub);
        return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}