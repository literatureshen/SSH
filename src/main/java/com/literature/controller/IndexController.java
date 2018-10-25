package com.literature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.literature.domain.Dept;
import com.literature.domain.Emp;
import com.literature.service.DeptService;
import com.literature.service.EmpService;

@Controller
@RequestMapping(value="/")
public class IndexController {

	@Autowired
	private DeptService deptService;
	@Autowired
	private EmpService empService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public void index() {
		Dept dept=new Dept();
		dept.setName("IT");
		deptService.save(dept);
		Emp emp=new Emp();
		emp.setName("Alice");
		emp.setDept(dept);
		empService.save(emp);
	}
}
