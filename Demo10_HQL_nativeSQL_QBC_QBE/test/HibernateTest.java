import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.ekunt.Util.HibernateUtil;
import com.ekunt.entity.Dept;
import com.ekunt.entity.Emp;

/**
 * nativeSQL > HQL > EJBQL(JPQL) > QBC(Query By Criteria) > QBE(Query By Example)
 * @author E-Kunt
 *
 */
public class HibernateTest {
	
	/**
	 * �������
	 */
	@Test
	public void saveTest(){
		
		Session session = HibernateUtil.getSession();
		Dept d1 = new Dept("����","��������");
		Dept d2 = new Dept("�ӹ�","����ӹ�");
		Dept d3 = new Dept("����","��������");
		Dept d4 = new Dept("����","��������");
		
		Emp e1 = new Emp("����",new Date(),2000);
		e1.setDept(d1);
		Emp e2 = new Emp("����",new Date(),3000);
		e2.setDept(d1);
		Emp e3 = new Emp("����",new Date(),4000);
		e3.setDept(d3);
		Emp e4 = new Emp("����",new Date(),5000);
		e4.setDept(d4);
		Emp e5 = new Emp("����",new Date(),6000);
		e5.setDept(d2);
		
		session.beginTransaction();
		session.save(d1);
		session.save(d2);
		session.save(d3);
		session.save(d4);
		session.save(e1);
		session.save(e2);
		session.save(e3);
		session.save(e4);
		session.save(e5);
		session.getTransaction().commit();
		
		HibernateUtil.closeSession(session);
	}
	
	
	/**
	 * ����ʵ���ѯ
	 * ע��ʵ�������Ҫ���޲ι�������
	 */
	@Test
	public void queryTest(){
		
		Session session = HibernateUtil.getSession();
		//����auto-import����Ĭ���Զ�����������ʿ���ʡ�԰���
		String hql = "from Dept";
		Query query = session.createQuery(hql);
		List<Dept> ds = query.list();
		for(Dept d : ds) {
			System.out.println(d);
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ��������ϵ��ʵ���ѯ
	 * ע��ʵ�������Ҫ���޲ι�������
	 */
	@Test
	public void many2oneTest(){
		
		Session session = HibernateUtil.getSession();
		String hql = "from Emp";
		Query query = session.createQuery(hql);
		List<Emp> emps = query.list();
		for(Emp e : emps) {
			System.out.println(e);
			//����lazyʱ���������δִ��ǰ�����Զ�����emp����Ӧ��dept��
			System.out.println(e.getDept());
			System.out.println(" ");
		}
		HibernateUtil.closeSession(session);
	}
	
	
	/**
	 * ͨ��Object[]���ز�ѯ�����
	 * select�������ʱ��Ĭ�Ϸ���List<Object[]>,Object[i]��ʾ��i�����Ե�ֵ��
	 * selectһ������ʱ��Ĭ�Ϸ���List<Object>,Object��ʾ�����������ԡ�
	 */
	@Test
	public void selectReturnObjectArrayTest(){
		
		Session session = HibernateUtil.getSession();
		String hql = "select e.ename, e.sal from Emp as e";
		Query query = session.createQuery(hql);
		List<Object[]> objas = query.list();
		for(Object[] obja : objas) {
			System.out.println("ename:" + obja[0] + " | " + "sal:" + obja[1]);
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ͨ��Object���ز�ѯ�����
	 * select�������ʱ��Ĭ�Ϸ���List<Object[]>,Object[i]��ʾ��i�����Ե�ֵ��
	 * selectһ������ʱ��Ĭ�Ϸ���List<Object>,Object��ʾ�����������ԡ�
	 */
	@Test
	public void selectReturnObjectTest(){
		
		Session session = HibernateUtil.getSession();
		String hql = "select e.ename from Emp  e";
		Query query = session.createQuery(hql);
		List<Object> objs = query.list();
		for(Object obj : objs) {
			System.out.println("ename:" + obj);
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ʹ��"new list()",ͨ��List���ز�ѯ�����
	 * 
	 */
	@Test
	public void selectReturnListTest(){
		
		Session session = HibernateUtil.getSession();
		String hql = "select new list(e.ename,e.sal) from Emp  e";
		Query query = session.createQuery(hql);
		List<List> lists = query.list();
		for(List list : lists) {
			System.out.print("ename:" + list.get(0));
			System.out.println(" | sal:" + list.get(1));
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ʹ��"new map()",ͨ��Map���ز�ѯ�����
	 * ���ñ���ʱ��keyֵΪ�ֶ�����ֵ����Ϊ�ַ�������
	 * ʹ�ñ���ʱ��keyֵΪ�ֶα����������ô��ֶε�����ֵ
	 * 
	 * �Ƽ�ʹ�ñ�������
	 */
	@Test
	public void selectReturnMapTest(){
		
		Session session = HibernateUtil.getSession();
		String hql = "select new map(e.ename,e.sal as sal,e.hiredate) from Emp  e";
		Query query = session.createQuery(hql);
		List<Map> maps = query.list();
		for(Map map : maps) {
			System.out.print("ename:" + map.get("0"));
			System.out.print(" | sal:" + map.get("sal"));
			System.out.println(" | hiredate:" + map.get("2"));
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ʹ��"new ���췽��()",ͨ���Զ����෵�ز�ѯ�����
	 * 
	 * ע�⣬һ��ҪԤ�ȸ��ݲ�ѯ���ֶΣ����庬��Ӧ������˳��ҲҪ��Ӧ���Ĺ��췽����
	 */
	@Test
	public void selectReturnSelfTest(){
		
		Session session = HibernateUtil.getSession();
		String hql = "select new Emp(e.ename,e.hiredate,e.sal) from Emp e";
		Query query = session.createQuery(hql);
		List<Emp> emps = query.list();
		for(Emp emp : emps) {
			System.out.println(emp);
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * distinct ������ѯ����ظ���
	 */
	@Test
	public void distinctTest(){
		
		Session session = HibernateUtil.getSession();
		String hql = "select distinct e.dept from Emp e";
		Query query = session.createQuery(hql);
		List<Object> objs = query.list();
		for(Object obj : objs) {
			System.out.println("dname:" + ((Dept)obj).getDname());
		}
		HibernateUtil.closeSession(session);
	}
	
	@Test
	public void whereTest() {
		Session session = HibernateUtil.getSession();
		//String hql = "from Emp e where e.sal = 2000";
		//String hql = "from Emp e where e.sal = null and e.ename <> null";
		//String hql = "from Emp e where e.sal is null and e.ename is not null";
		//String hql = "from Emp e where e.empno in (1,3)";
		//String hql = "from Emp e where e.empno not in (1,3)";
		//String hql = "from Emp e where e.empno between 1 and 3";
		//String hql = "from Emp e where e.empno not between 1 and 3";
		//String hql = "from Emp e where e.ename like '��_'";
		//String hql = "from Emp e where e.sal like '%30%'";
		//String hql = "from Emp e where e.sal between 2000 and 3000 and e.ename like '��_'";
		//String hql = "from Emp e where e.sal between 2000 and 3000 or e.ename like '��_'";
		//String hql = "from Emp e where e.sal * 5 >= 20000 ";
		String hql = "from Emp e where e.sal < (select avg(e.sal) from Emp e)";
		//String hql = "from Emp e where e.sal > ALL (select e.sal from Emp e where mod(e.sal, 2)= 0)"; //������
		Query query = session.createQuery(hql);
		for(Emp e : (List<Emp>)query.list()) {
			System.out.println(e);
		}
		HibernateUtil.closeSession(session);
	}
	
	@Test
	public void whereTest2() {
		Session session = HibernateUtil.getSession();
		//String hql = "from Dept d where d.emps is empty";
		String hql = "from Dept d where d.emps is not empty";
		Query query = session.createQuery(hql);
		for(Dept d : (List<Dept>)query.list()) {
			System.out.println(d + " | " + d.getEmps().size());
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ���� :xxx ��ʹ��
	 */
	@Test
	public void whereTest3() {
		Session session = HibernateUtil.getSession();
		String hql = "select e.ename, e.sal from Emp e where e.sal between :min and :max";
		Query query = session.createQuery(hql)
				.setParameter("min", 2000)
				.setInteger("max", 4000);
		for(Object[] o : (List<Object[]>)query.list()) {
			System.out.println(o[0] + " | " + o[1]);
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * query.uniqueResult() ����������ʵ����������Ƿ��ؼ��ϡ�
	 * ��Ҫע��������Ǳ���Ҫͨ��where����ѯ�õ��ļ�¼ֻʣ��һ�����߲�ѯ�����������һ��������ᷢ���쳣��
	 */
	@Test
	public void uniqueResultTest() {
		Session session = HibernateUtil.getSession();
		
		String hql = "from Dept d where d.deptno = 1";
		Query query = session.createQuery(hql);
		Dept d = (Dept) query.uniqueResult();
		System.out.println(d);
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ����������
	 */
	@Test
	public void orderbyTest() {
		Session session = HibernateUtil.getSession();
		String hql = "from Emp order by dept.deptno desc, sal asc";
		Query query = session.createQuery(hql);
		List<Emp> emps = query.list();
		for(Emp e : emps) {
			System.out.println("deptno : " + e.getDept().getDeptno() + " | " + "sal : " + e.getSal());
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ��ҳ��
	 */
	@Test
	public void pageTest() {
		Session session = HibernateUtil.getSession();
		int pageNo = 2;
		int pageRow = 2;
		String hql = "from Emp order by empno desc";
		Query query = session.createQuery(hql).setMaxResults(pageRow).setFirstResult((pageNo-1) * pageRow);
		List<Emp> emps = query.list();
		for(Emp e : emps) {
			System.out.println(e.getEmpno() + "|" + e.getEname());
		}
		HibernateUtil.closeSession(session);
	}
	

	/**
	 * Join����ѯ
	 */
	@Test
	public void joinTest() {
		Session session = HibernateUtil.getSession();
		String hql = "select e.ename, d.dname from Emp e join e.dept d";
		Query query = session.createQuery(hql);
		for(Object[] o : (List<Object[]>)query.list()) {
			System.out.println(o[0] + "|" + o[1]);
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ���������
	 */
	@Test
	public void objectParamterTest() {
		Session session = HibernateUtil.getSession();
		String hql = "from Emp e where e = :otheremp";
		Emp emp = new Emp();
		emp.setEmpno(3);
		Query query = session.createQuery(hql)
				.setParameter("otheremp",emp);
		System.out.println(query.uniqueResult());
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * max min avg sum count
	 * lower upper trim concat length 
	 * abs sqrt mod
	 * �Ⱥ���
	 */
	@Test
	public void functionTest() {
		Session session = HibernateUtil.getSession();
		String hql = "select max(e.empno), "
				+ "min(e.empno), "
				+ "avg(e.empno), "
				+ "sum(e.empno),"
				+ "count(e.empno)"
				+ "from Emp e";
		
		/*String hql = ("select lower(e.ename)," +
		 					"upper(e.ename)," +
		 					"trim(e.ename)," +
		 					"concat(e.ename, '***')," +
		 					"length(e.ename)," +
		 					"abs(e.empno)," +
							"sqrt(e.empno)," +
							"mod(e.empno,2)" +
		 					"from Emp e");
		*/
		
		/*
		String hql = "select current_date, "
				+ "current_time, "
				+ "current_timestamp, "
				+ "e.empno "
				+ "from Emp e";
		*/
		
		
		Query query = session.createQuery(hql);
		for(Object[] os : (List<Object[]>)query.list()) {
			for(Object o : os ) {
				System.out.println(o);
			}
		}
		HibernateUtil.closeSession(session);
	}
	
	
	/**
	 * ע�⣡����
	 * ��in ����ʵ��exists�Ĺ���
	 * ����existsִ��Ч�ʸߣ�
	 */
	@Test
	public void existsTest() {
		Session session = HibernateUtil.getSession();
		String hql = "from Dept d where exists (select e.empno from Emp e where e.dept.deptno=d.deptno)";
		//String hql = "from Dept d where d.deptno in (select distinct e.dept.deptno from Emp e)";
		
		//String hql = "from Dept d where not exists (select e.empno from Emp e where e.dept.deptno=d.deptno)";
		//String hql = "from Dept d where d.deptno not in (select distinct e.dept.deptno from Emp e)";
		Query query = session.createQuery(hql);
		for(Dept d : (List<Dept>)query.list()) {
			System.out.println(d);
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 *
	 * group by �� having ��ʹ��
	 */
	@Test
	public void groupbyHavingTest() {
		Session session = HibernateUtil.getSession();
		String hql = "select e.ename, count(*) from Emp e group by e.ename having count(*) >= 1";
		Query query = session.createQuery(hql);
		for(Object[] os : (List<Object[]>)query.list()) {
			for(Object o : os ) {
				System.out.println(o);
			}
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * dml
	 */
	@Test
	public void updateDeleteTest() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Query q = session.createQuery("update Emp e set e.ename = upper(e.ename)") ;
		//Query q = session.createQuery("delete Emp e where e.ename = "����") ;
		q.executeUpdate();
		session.getTransaction().commit();
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ����sql��ѯ
	 */
	@Test
	public void nativeSQLTest() {
		Session session = HibernateUtil.getSession();
		SQLQuery q = session.createSQLQuery("select * from emp limit 2,4").addEntity(Emp.class);
		for(Emp e : (List<Emp>)q.list())
			System.out.println(e.getEname());
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * QBC : Query By Criteria
	 */
	@Test
	public void QBCTest() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		//criterion ��׼/׼��/Լ��
		Criteria c = session.createCriteria(Emp.class) //from Emp
					 
					 .add(Restrictions.gt("empno", 0)) //greater than = empno > 0
					 .add(Restrictions.lt("empno", 8)) //little than = empno < 8
					 .add(Restrictions.like("ename", "��_")) // ename like '��_'
					 .createCriteria("dept")
					 .add(Restrictions.between("deptno", 1, 5)) //dept.deptno >= 1 and dept.deptno <=5
					 ;
		for(Emp e : (List<Emp>)c.list()) {
			System.out.println(e);
		}
		session.getTransaction().commit();
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * QBE : Query By Example
	 */
	@Test
	public void QBETest() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Emp tExample = new Emp();
		tExample.setEname("��_");
		tExample.setSal(5000);
		
		Example example = Example.create(tExample)
					.ignoreCase().enableLike();
		Criteria c = session.createCriteria(Emp.class)
					 .add(Restrictions.gt("empno", 1))
					 .add(Restrictions.lt("empno", 8))
					 .add(example)
					 ;
		for(Emp e : (List<Emp>)c.list()) {
			System.out.println(e);
		}
		session.getTransaction().commit();
		HibernateUtil.closeSession(session);
	}
}
