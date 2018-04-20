package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVO;

@WebServlet("/gc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String a = request.getParameter("a");
	
		if("deleteform".equals(a)) {
			String no = request.getParameter("no");
			request.setAttribute("no", no);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
			rd.forward(request, response);
			
		}
		else if("delete".equals(a)) {
			String no = request.getParameter("no");
			String password = request.getParameter("password");

			GuestbookDao dao = new GuestbookDao();		
			List<GuestbookVO> list = dao.getList();
			
			for(GuestbookVO vo : list) {
				if(vo.getNo()==Integer.parseInt(no)) {
					if(vo.getPassword().equals(password)) {
						dao.delete(Integer.parseInt(no));
					}
				}
			}
			
			list = dao.getList();
			request.setAttribute("list", list);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp");
			rd.forward(request, response);
			
		} else if ("add".equals(a)){
			GuestbookVO vo = new GuestbookVO();
			
			vo.setName(request.getParameter("name"));
			vo.setPassword(request.getParameter("password"));
			vo.setContent(request.getParameter("content"));
			
			GuestbookDao dao = new GuestbookDao();
			dao.add(vo);
			List<GuestbookVO> list = dao.getList();
			request.setAttribute("list", list);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp");
			rd.forward(request, response);
		} else {	
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVO> list = dao.getList();
			request.setAttribute("list", list);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp");
			rd.forward(request, response);
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
