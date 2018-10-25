package com.literature.dao.impl;

import org.springframework.stereotype.Repository;

import com.literature.common.dao.SimpleHibernateDaoImpl;
import com.literature.dao.EmpDao;
import com.literature.domain.Emp;
@Repository
public class EmpDaoImpl extends SimpleHibernateDaoImpl<Emp, Long> implements EmpDao{

}
