package com.literature.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

/**
 * Hibernate基础接口
 * @author Literature
 * @date 2018年10月25日
 *
 */
public interface SimpleHibernateDao<T, PK extends Serializable> {

	/**
	 * 取得当前Session.
	 * 
	 * @return Session
	 */
	Session getSession();

	/**
	 * 修改对象
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 保存或修改对象
	 * 
	 * @param entity
	 */
	void saveOrUpdate(T entity);

	/**
	 * 保存新增或修改的对象.
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 删除对象.
	 * 
	 * @param entity
	 *            对象必须是session中的对象或含id属性的transient对象.
	 */
	void delete(T entity);

	/**
	 * 按id删除对象.
	 * 
	 * @param id
	 */
	void delete(PK id);

	/**
	 * 按id获取对象.
	 * 
	 * @param id
	 * @return 对象
	 */
	T find(PK id);

	/**
	 * load:不会直接访问数据库，只是简单地返回一个由底层封装的一个代理对象。 get:直接访问数据库，返回一个数据库对象。
	 */
	/**
	 * 按照id获取对象
	 * 
	 * @param id
	 * @return
	 */
	T get(PK id);

	/**
	 * 按id列表获取对象列表.
	 * 
	 * @param idList
	 * @return 对象集合
	 */
	List<T> find(Collection<PK> idList);

	/**
	 * 获取全部对象.
	 * 
	 * @return 对象集合.
	 */
	List<T> findAll();

	/**
	 * 获取全部对象.
	 * 
	 * @param isCache
	 *            是否缓存
	 * @return 对象集合.
	 */
	List<T> findAll(Boolean isCache);

	/**
	 * 获取全部对象, 支持按属性行序.
	 * 
	 * @param orderByProperty
	 *            排序属性name
	 * @param isAsc
	 *            是否升序排序
	 * @return 查询结果集合
	 */
	List<T> findAll(String orderByProperty, boolean isAsc);

	/**
	 * 按属性查找对象列表, 匹配方式为相等
	 * 
	 * @param propertyName
	 *            属性name
	 * @param value
	 *            属性值
	 * @return 结果集合
	 */
	List<T> findBy(String propertyName, Object value);

	/**
	 * 按属性查找唯一对象, 匹配方式为相等
	 * 
	 * @param propertyName
	 *            属性name
	 * @param value
	 *            属性值
	 * @return 结果对象
	 */
	T findUniqueBy(String propertyName, Object value);

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * @return 结果集合
	 */
	<X> List<X> find(String hql, Object... values);

	/**
	 * 获取主键的集合
	 * 
	 * @param hql
	 * @return
	 */
	List<String> findIds(String hql);

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return 对象集合
	 */
	<X> List<X> find(String hql, Map<String, ?> values);

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param hql
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * @return 对象
	 */
	<X> X findUnique(String hql, Object... values);

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param hql
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return 对象
	 */
	<X> X findUnique(String hql, Map<String, ?> values);

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param hql
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	int batchExecute(String hql, Object... values);

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param hql
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	int batchExecute(String hql, Map<String, ?> values);

	/**
	 * 
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param queryString
	 *            查询
	 * @param values
	 */
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param queryString
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * @return Query
	 */
	Query createQuery(String queryString, Object... values);

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param queryString
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return Query
	 */
	Query createQuery(String queryString, Map<String, ?> values);

	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * 
	 * @param queryString
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * @return SQLQuery
	 */
	SQLQuery createSQLQuery(String queryString, Object... values);

	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * 
	 * @param queryString
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return SQLQuery
	 */
	SQLQuery createSQLQuery(String queryString, Map<String, ?> values);

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 * @return 结果集合
	 */
	List<T> find(Criterion... criterions);

	List<T> find(Boolean isCache, Criterion... criterions);

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 * @return 对象
	 */
	T findUnique(Criterion... criterions);

	/**
	 * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 * @return Criteria
	 */
	Criteria createCriteria(Criterion... criterions);

	Criteria createCriteria(Boolean isCache, Criterion... criterions);

	/**
	 * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化. 如果传入entity,
	 * 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,需执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，
	 * 初始化User的直接属性和延迟加载的Description属性.
	 */
	void initProxyObject(Object proxy);

	/**
	 * Flush当前Session.
	 */
	void flush();

	/**
	 * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 * 
	 * @param query
	 * @return Query
	 */
	Query distinct(Query query);

	/**
	 * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 * 
	 * @param criteria
	 * @return Criteria
	 */
	Criteria distinct(Criteria criteria);

	/**
	 * 取得对象的主键名.
	 * 
	 * @return 对象的主键名
	 */
	String getIdName();

	/**
	 * 判断对象的属性值在数据库内是否唯一. 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 * 
	 * @param propertyName
	 *            属性name
	 * @param newValue
	 *            新值
	 * @param oldValue
	 *            旧值
	 * @return 是否唯一
	 */
	boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue);

}