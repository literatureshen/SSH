package com.literature.dao.impl;

import org.springframework.stereotype.Repository;

import com.literature.common.dao.SimpleHibernateDaoImpl;
import com.literature.dao.DeptDao;
import com.literature.domain.Dept;
@Repository
public class DeptDaoImpl extends SimpleHibernateDaoImpl<Dept, Long> implements DeptDao{

}
