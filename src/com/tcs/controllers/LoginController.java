package com.tcs.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/")
	public ModelAndView start()
	{
		ModelAndView model = new ModelAndView("redirect:/hello");
		return model;
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView admin(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("Session in admin : " + request.getSession(false));
		ModelAndView model = new ModelAndView("admin");
		model.addObject("title", "Spring Security - Remember Me");
		model.addObject("message", "This page is only for the Admin guys !!");
		return model;
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ModelAndView hello(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("Session in hello : " + request.getSession(false));
		ModelAndView model = new ModelAndView("hello");
		model.addObject("title", "Spring Security - Remember Me");
		model.addObject("message", "This page is for both the Users and the Admins !!");
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout)
	{
		System.out.println("Session 1 in /login : " + request.getSession(false));
		System.out.println("Error : " + error);
		System.out.println("Logout : " + logout);
		
		ModelAndView model = new ModelAndView("login");
		HttpSession s = request.getSession(false);
		if(s != null)
		{
			Object loginUpdate = s.getAttribute("loginUpdate");
			if(loginUpdate != null)
			{
				model.addObject("loginUpdate", true);
				model.addObject("targetUrl", "/update");
			}
		}
		
		if(error != null)
		{
			model.addObject("error", "Invalid Username and Password !!");
			HttpSession session = request.getSession(false);
			System.out.println("Session 2 in /login : " + request.getSession(false));
			if(session != null)
			{
				String targetUrl = getRememberMeTargetUrlFromSession(request);
				/*
				 * This getRememberMeTargetUrlFromSession(request) method is used so that it can be known whether the 
				 * invalid credentials is during the session management cookie session or during the remember-me cookie session.
				 */
				if(targetUrl.equals(""))
				{
					
				}
				else
				{
					model.addObject("targetUrl", targetUrl);
					model.addObject("loginUpdate", true);
				}
			}
			
		}
		if(logout != null)
		{
			model.addObject("logout", "You have logged out successfully !!");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("Session in update : " + request.getSession(false));
		ModelAndView model = null;
		
		if(isRememberMeAuthenticated())
		{
			setRememberMeTargetUrlToSession(request);
			model = new ModelAndView("redirect:/login");
			request.getSession(false).setAttribute("loginUpdate", true);
		}
		else
		{
			model = new ModelAndView("update");
		}
		return model;
	}
	
	private void setRememberMeTargetUrlToSession(HttpServletRequest request)
	{
		System.out.println("Session in set : " + request.getSession(false));
		HttpSession session = request.getSession(false);
		if(session != null)
		{
			session.setAttribute("targetUrl", "/update");
		}
	}
	
	private String getRememberMeTargetUrlFromSession(HttpServletRequest request)
	{
		System.out.println("Session in get : " + request.getSession(false));
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if(session != null)
		{
			Object t = session.getAttribute("targetUrl");
			if(t == null)
			{
				targetUrl = "";
			}
			else
			{
				targetUrl = t.toString();
			}
		}
		return targetUrl;
	}
	
	private boolean isRememberMeAuthenticated()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null)
		{
			return false;
		}
		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView error(HttpServletRequest request, HttpServletResponse response)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String username = user.getUsername();
		ModelAndView model = new ModelAndView("403");
		model.addObject("title", "HTTP Status - 403");
		model.addObject("message", "You are not authorized to access this page !!");
		model.addObject("username", username);
		return model;
	}
	
	@RequestMapping(value = "/maxAttempts", method = RequestMethod.GET)
	public ModelAndView maxAttempts(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("maxAttempts");
		model.addObject("message", "One user is already logged in with these credentials !!");
		return model;
	}

}
