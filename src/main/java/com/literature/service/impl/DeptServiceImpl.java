package com.literature.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.literature.dao.DeptDao;
import com.literature.domain.Dept;
import com.literature.service.DeptService;

@Service
@Transactional
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptDao deptDao;

	@Override
	public void save(Dept dept) {
		deptDao.save(dept);
	}
}
