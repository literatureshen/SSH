package com.literature.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.literature.dao.EmpDao;
import com.literature.domain.Emp;
import com.literature.service.EmpService;
@Service
@Transactional
public class EmpServiceImpl implements EmpService{

	@Autowired
	private EmpDao empDao;

	@Override
	public void save(Emp emp) {
		empDao.save(emp);
	}
}
