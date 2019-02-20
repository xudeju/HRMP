package com.company.hrm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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

@WebServlet(name="EmpFindByIdServlet",urlPatterns = {"/EmpFindByIdServlet"})
public class EmpFindByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int empno = Integer.parseInt(request.getParameter("empno"));
		IEmpService empservice = (IEmpService) SpringIOC.getCtx().getBean("empService");
		Emp emp = null;
		ResResult<List<Emp>> result = null;
		if (empservice.findById(empno) != null) {
			emp = empservice.findById(empno);
			List<Emp> emps = Arrays.asList(emp);
			result = ResResult.success("find by id success", emps);
		}else {
			result = ResResult.error(404, "no such data");
		}
		String jsonResult = new ObjectMapper().writeValueAsString(result);
		PrintWriter out = response.getWriter();
		out.println(jsonResult);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
