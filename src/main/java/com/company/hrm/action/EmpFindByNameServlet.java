package com.company.hrm.action;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet(name="EmpFindByNameServlet",urlPatterns = {"/EmpFindByNameServlet"})
public class EmpFindByNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ename = request.getParameter("ename");
		IEmpService empservice = (IEmpService) SpringIOC.getCtx().getBean("empService");
		List<Emp> empList = empservice.findByName(ename);
		HttpSession session = request.getSession();
		ResResult<List<Emp>> result = null;
		if (!empList.isEmpty() && empList.size()>0) {
			result = ResResult.success("find by name success", empList);
		}else {
			result = ResResult.error(404, "no data");
		}
		String jsonResult = new ObjectMapper().writeValueAsString(result);
		PrintWriter out = response.getWriter();
		out.println(jsonResult);
		out.flush();
		out.close();
	}

}
