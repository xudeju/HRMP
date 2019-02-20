package com.company.hrm.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.hrm.common.DaoConst;
import com.company.hrm.common.ResResult;
import com.company.hrm.common.ServiceConst;
import com.company.hrm.common.SpringIOC;
import com.company.hrm.dao.pojo.User;
import com.company.hrm.service.iService.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name="UserRegistServlet",urlPatterns = {"/UserRegistServlet"})
public class UserRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String userpassword1 = request.getParameter("userpassword1");
		String userpassword2 = request.getParameter("userpassword2");
		IUserService userservice = (IUserService) SpringIOC.getCtx().getBean("userService");
		HttpSession session = request.getSession();
		ResResult result = null;
		if (userservice.isExist(username)) {
			result = ResResult.error(500,"username already exsit");
		}else if (username.isEmpty() || userpassword1.isEmpty() || userpassword1.isEmpty()) {
			result = ResResult.error(500,"empty info");
		}else if (userpassword1.equals(userpassword2)) {
			User user = new User(username,userpassword1,ServiceConst.DEFAULT_PRIORITY);
			String msg = userservice.regist(user);
			if (msg.indexOf(ServiceConst.SUCCESS) != -1) {
				result = ResResult.success("regist success");
			} else {
				result = ResResult.error(500,"regist error");
			}
		}else {
			result = ResResult.error(500,"regist error");
		}
		String jsonResult = new ObjectMapper().writeValueAsString(result);

		PrintWriter out = response.getWriter();
		out.write(jsonResult);
		out.flush();
		out.close();
	}
}
