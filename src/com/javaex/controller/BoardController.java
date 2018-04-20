package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String actionName = request.getParameter("a");
		if("modifyform".equals(actionName)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if(authUser==null) {
				authUser = new UserVo();
			}
			String name = request.getParameter("name");
			if(name.equals(authUser.getName())){
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				String no = request.getParameter("no");
				BoardVo vo = new BoardVo();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setNo(Integer.parseInt(no));
				request.setAttribute("vo", vo);
				WebUtil.forword(request, response, "/WEB-INF/views/board/modify.jsp");
			}else {
				WebUtil.redirect(request, response, "/mysite/board");
			}
		}else if("modify".equals(actionName)) {
			String name = request.getParameter("name");
			String content = request.getParameter("content");
			String title = request.getParameter("title");
			String no = request.getParameter("no");
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContent(content);
			vo.setNo(Integer.parseInt(no));
			BoardDao dao = new BoardDao();
			dao.modify(vo);
			WebUtil.redirect(request, response, "/mysite/board");
		}else if("view".equals(actionName)) {
			String no = request.getParameter("no");
			BoardDao dao = new BoardDao();
			BoardVo BoardVo = dao.view(Integer.parseInt(no));
			dao.hitUp(BoardVo.getHit(),Integer.parseInt(no));
			request.setAttribute("BoardVo", BoardVo);
			WebUtil.forword(request, response, "/WEB-INF/views/board/view.jsp");
		}else if("writeform".equals(actionName)) {
			WebUtil.forword(request, response, "/WEB-INF/views/board/write.jsp");
		}else if("write".equals(actionName)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			BoardDao dao = new BoardDao();
			dao.insert(title,content,authUser.getNo());
			
			WebUtil.redirect(request, response, "/mysite/board");
		}else if("delete".equals(actionName)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if(authUser==null) {
				authUser = new UserVo();
			}
			String name = request.getParameter("name");
			if(name.equals(authUser.getName())){
				String no = request.getParameter("no");
				BoardDao dao = new BoardDao();
				dao.delete(Integer.parseInt(no));
				WebUtil.redirect(request, response, "/mysite/board");
			}else {
				WebUtil.redirect(request, response, "/mysite/board");
			}
		}else if("search".equals(actionName)) {
			String kwd = request.getParameter("kwd");
			BoardDao dao = new BoardDao();
			List<BoardVo> list = dao.search(kwd);
			
			request.setAttribute("list", list);
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
			
		}else {
			BoardDao dao = new BoardDao();
			List<BoardVo> list = dao.getList();
			
			request.setAttribute("list", list);
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
