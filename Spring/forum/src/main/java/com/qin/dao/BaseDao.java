package com.qin.dao;

import static org.mockito.Matchers.matches;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;



public class BaseDao<T> {

	private Class<T> entityClass;
	@Autowired
	private HibernateTemplate hibernateTemplate;

	// 通过反射获取子类确定的泛类
	public BaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

	// 根据ID加载PO实例
	public T load(Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	// 根据id获取PO实例
	public T get(Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	// 获取PO的所有对象
	public List<T> loadALL() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	// 保存PO
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	// 删除PO
	public void remove(T entity) {
		getHibernateTemplate().delete(entity);
	}

	// 更改PO
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	// 执行HQL查询
	public List find(String hql) {
		return getHibernateTemplate().find(hql);
	}

	// 执行带参数deHQL查询
	public List find(String hql, Object... params) {
		return getHibernateTemplate().find(hql, params);
	}

	// 对延迟加载的实体PO进行初始化
	public void initialize(Object entity) {
		this.initialize(entity);
	}
	
	//分页查询函数，使用HQL
	public Page pageQuery(String hql,int pageNo,int pageSize,Object... objects){
		Assert.hasText(hql);
		Assert.isTrue(pageNo>=1, "pageNo should start from 1");
		//Count 查询
		String countQueryString = "select count(*) "+removeSelect(removeOrders(hql));
		List countList = this.getHibernateTemplate().find(countQueryString,objects);
		long totalCount = (Long) countList.get(0);
		if(totalCount<1)
			return new Page();
		//实际查询返回页面对象
		int startIndex = Page.getStartOfPage(pageNo,pageSize);
		Query query = createQuery(hql, objects);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex,totalCount,pageSize,list);
		
	}
	
	//创建Query对象
	public Query createQuery(String hql,Object...objects){
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for (int i =0;i<objects.length;i++){
			query.setParameter(i, objects[i]);
		}
		return query;
	}
	
	//去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	private static String removeSelect(String hql){
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1,"hql : " + hql + "must has a keyword ‘from'");
	
		return hql.substring(beginPos);
	}
	
	//去掉HQL的orderby字句,
	private static String removeOrders(String hql){
		Assert.hasText(hql);
		Pattern pattern = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(hql);
		StringBuffer stringBuffer = new StringBuffer();
		while(matcher.find()){
			matcher.appendReplacement(stringBuffer, "");
		}
		matcher.appendTail(stringBuffer);
		return stringBuffer.toString();
	}
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public Session getSession(){
		return this.hibernateTemplate.getSessionFactory().getCurrentSession();
	}
	
	
}
