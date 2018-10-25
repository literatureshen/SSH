package com.literature.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.literature.common.utils.Reflections;

/**
 * 封装Hibernate原生API的DAO泛型基类.<br>
 * 可在Service层直接使用, 也可以扩展泛型DAO子类使用, 见两个构造函数的注释. 取消了HibernateTemplate,
 * 直接使用Hibernate原生API.
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 * @author Literature
 */
@SuppressWarnings("unchecked")
public class SimpleHibernateDaoImpl<T, PK extends Serializable> implements SimpleHibernateDao<T, PK> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
	 * SimpleHibernateDao<User, Long>
	 */
	public SimpleHibernateDaoImpl() {
		this.entityClass = Reflections.getClassGenricType(getClass());
	}

	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数. 在构造函数中定义对象类型Class.
	 * eg. SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User,
	 * Long>(sessionFactory, User.class);
	 */
	public SimpleHibernateDaoImpl(final SessionFactory sessionFactory, final Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#getSession()
	 */
	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#update(T)
	 */
	@Override
	public void update(final T entity) {
		getSession().update(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#saveOrUpdate(T)
	 */
	@Override
	@Transactional(readOnly = false)
	public void saveOrUpdate(final T entity) {
		getSession().saveOrUpdate(entity);
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#save(T)
	 */
	@Override
	public void save(final T entity) {
		getSession().save(entity);
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#delete(T)
	 */
	@Override
	public void delete(final T entity) {
		getSession().delete(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#delete(PK)
	 */
	@Override
	public void delete(final PK id) {
		delete(find(id));
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#find(PK)
	 */
	@Override
	public T find(final PK id) {
		return (T) getSession().load(entityClass, id);
	}

	/**
	 * load:不会直接访问数据库，只是简单地返回一个由底层封装的一个代理对象。 get:直接访问数据库，返回一个数据库对象。
	 */
	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#get(PK)
	 */
	@Override
	public T get(final PK id) {
		return (T) getSession().get(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#find(java.util.Collection)
	 */
	@Override
	public List<T> find(final Collection<PK> idList) {
		return find(Restrictions.in(getIdName(), idList));
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#findAll()
	 */
	@Override
	public List<T> findAll() {
		return find();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#findAll(java.lang.Boolean)
	 */
	@Override
	public List<T> findAll(Boolean isCache) {
		return find(isCache);
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#findAll(java.lang.String, boolean)
	 */
	@Override
	public List<T> findAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#findBy(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<T> findBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#findUniqueBy(java.lang.String, java.lang.Object)
	 */
	@Override
	public T findUniqueBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#find(java.lang.String, java.lang.Object)
	 */
	@Override
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#findIds(java.lang.String)
	 */
	@Override
	public List<String> findIds(final String hql) {
		return createQuery(hql).list();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#find(java.lang.String, java.util.Map)
	 */
	@Override
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#findUnique(java.lang.String, java.lang.Object)
	 */
	@Override
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#findUnique(java.lang.String, java.util.Map)
	 */
	@Override
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#batchExecute(java.lang.String, java.lang.Object)
	 */
	@Override
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#batchExecute(java.lang.String, java.util.Map)
	 */
	@Override
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param queryString
	 *            查询
	 * @param values
	 */
	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#createQuery(java.lang.String, java.lang.Object)
	 */
	@Override
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				// query.setParameter(String.valueOf(i), values[i]);
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#createQuery(java.lang.String, java.util.Map)
	 */
	@Override
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#createSQLQuery(java.lang.String, java.lang.Object)
	 */
	@Override
	public SQLQuery createSQLQuery(final String queryString, final Object... values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				// sqlQuery.setParameter(String.valueOf(i), values[i]);
				sqlQuery.setParameter(i, values[i]);
			}
		}
		return sqlQuery;
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#createSQLQuery(java.lang.String, java.util.Map)
	 */
	@Override
	public SQLQuery createSQLQuery(final String queryString, final Map<String, ?> values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
		if (values != null) {
			sqlQuery.setProperties(values);
		}
		return sqlQuery;
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#find(org.hibernate.criterion.Criterion)
	 */
	@Override
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#find(java.lang.Boolean, org.hibernate.criterion.Criterion)
	 */
	@Override
	public List<T> find(Boolean isCache, final Criterion... criterions) {
		return createCriteria(isCache, criterions).list();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#findUnique(org.hibernate.criterion.Criterion)
	 */
	@Override
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#createCriteria(org.hibernate.criterion.Criterion)
	 */
	@Override
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#createCriteria(java.lang.Boolean, org.hibernate.criterion.Criterion)
	 */
	@Override
	public Criteria createCriteria(Boolean isCache, final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		criteria.setCacheable(isCache);
		return criteria;
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#initProxyObject(java.lang.Object)
	 */
	@Override
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#flush()
	 */
	@Override
	public void flush() {
		getSession().flush();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#distinct(org.hibernate.Query)
	 */
	@Override
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#distinct(org.hibernate.Criteria)
	 */
	@Override
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#getIdName()
	 */
	@Override
	public String getIdName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/* (non-Javadoc)
	 * @see com.literature.common.dao.SimpleHibernateDao1#isPropertyUnique(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}