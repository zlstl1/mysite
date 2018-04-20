package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String actionName = request.getParameter("a");
		
		if("joinform".equals(actionName)) { // 회원가입 폼
			WebUtil.forword(request, response, "WEB-INF/views/user/joinform.jsp");
		} else if("join".equals(actionName)) { // 회원정보 저장
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo(name,email,password,gender);
			System.out.println(vo);
			
			UserDao userDao = new UserDao();
			userDao.insert(vo);
			
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinsuccess.jsp");
		} else if("loginform".equals(actionName)) {
			WebUtil.forword(request, response, "/WEB-INF/views/user/loginform.jsp");
		} else if("login".equals(actionName)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(email, password);
			
			if(userVo == null) { //로그인 실패
				System.out.println("로그인 실패");
				WebUtil.redirect(request, response, "/mysite/user?a=loginform&result=fail");
			}else { // 로그인 성공
				System.out.println("로그인 성공");
				HttpSession session = request.getSession();
				session.setAttribute("authUser",userVo);
				
				WebUtil.redirect(request, response, "/mysite/main");
			}		
		} else if("logout".equals(actionName)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			WebUtil.redirect(request, response, "/mysite/main");
		} else if("modifyform".equals(actionName)) {
			HttpSession session = request.getSession();
			UserVo userVo = (UserVo) session.getAttribute("authUser");
			UserDao userDao = new UserDao();
			userVo = userDao.getUserInfo(userVo.getNo());
			request.setAttribute("userInfo",userVo);
			
			WebUtil.forword(request, response, "/WEB-INF/views/user/modifyform.jsp");
		} else if("modify".equals(actionName)) {
			String no = request.getParameter("no");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			UserVo userVo = new UserVo(Integer.parseInt(no),name,null,password,gender);
			
			UserDao userDao = new UserDao();
			boolean flag = userDao.userModify(userVo);
			if(flag) {
				HttpSession session = request.getSession();
				UserVo authUser = (UserVo) session.getAttribute("authUser");
				authUser.setName(name);
				session.setAttribute("authUser",authUser);
			}
			
			WebUtil.redirect(request, response, "/mysite/main");
		} else if("list".equals(actionName)) {
		
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/list.jsp");
		} 
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
