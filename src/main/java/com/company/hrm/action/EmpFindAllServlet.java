package com.company.hrm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.hrm.common.DaoConst;
import com.company.hrm.common.ResResult;
import com.company.hrm.common.SpringIOC;
import com.company.hrm.dao.pojo.Emp;
import com.company.hrm.service.iService.IEmpService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name="EmpFindAllServlet",urlPatterns = {"/EmpFindAllServlet"})
public class EmpFindAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ResResult<List<Emp>> result = null;
		//业务逻辑判断:如果session中username不为空,说明用户是通过登录界面访问的
		if (session.getAttribute("username") != null) {
			//通过spring框架调取服务对象,执行相应服务
			IEmpService empservice = (IEmpService) SpringIOC.getCtx().getBean("empService");
			List<Emp> empList = new ArrayList<Emp>();
			empList = empservice.findAll();
			//通过服务执行结果来封装ResResult
			if (!empList.isEmpty() && empList.size() > 0) {
				result = ResResult.success("find all success", empList);
			}else {
				result = ResResult.error(404, "no data");
			}
		}else {
			result = ResResult.error(301, "have not login");
		}
		//将ResResult通过jakson转为json字符串,返回给前端
		PrintWriter out = response.getWriter();
		String jsonResult = new ObjectMapper().writeValueAsString(result);
		out.println(jsonResult);
		out.flush();
		out.close();
	}

}
