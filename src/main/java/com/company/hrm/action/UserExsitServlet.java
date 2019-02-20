package com.company.hrm.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.hrm.common.DaoConst;
import com.company.hrm.common.ResResult;
import com.company.hrm.common.SpringIOC;
import com.company.hrm.service.iService.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class UserExsitServlet
 */
@WebServlet(name="UserExsitServlet",urlPatterns = {"/UserExsitServlet"})
public class UserExsitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserExsitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		IUserService userService =  (IUserService) SpringIOC.getCtx().getBean("userService");
		boolean flag = userService.isExist(username);
		
		ResResult result = flag?ResResult.success():ResResult.error(404, "no such user");
		
		String resultJson = new ObjectMapper().writeValueAsString(result);
		PrintWriter out =response.getWriter();
		out.write(resultJson);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
