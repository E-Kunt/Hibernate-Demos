import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.ekunt.Util.HibernateUtil;
import com.ekunt.entity.Group;
import com.ekunt.entity.Student;

/**
 * ��ʾHibernate��1+N���⣺
 * fetch=FetchType.eager�����£�
 * ȡĳ����Student�����������ԣ��Իᵼ�����������(Group)һ����ѯ��
 * ����ֻ��Ҫ��1��SQL��伴����ɣ����ȴ����1+N��SQL��䣬Ӱ�����ܡ�
 * 
 * ���������
 * 1.fetch = FetchType.lazy , ��ʼֻ�����ڱ����SQL��䣬�����õ���������ʱ���ٷ��������SQL��䡣
 * 2.�ڹ����������ϱ�ע@BatchSize(size=x) , ���ù�������ѯʱһ���Բ�ѯ��������¼�� ʹתΪΪ 1+n/x���⡣�����Ƽ���
 * 3.join fetch , �� ʹ��"from Student s left join fetch s.group g"�� ���б����Ӳ�ѯ����ʱ�ͷ�1��SQL��䡣
 * 4.ʹ��QBC��ѯ��Ĭ��Ч����3��ͬ��
 * 
 * @author E-Kunt
 *
 */
public class HibernateTest {

	@Test
	public void saveTest(){
		Group g1 = new Group("JAVA","JavaSE");
		Group g2 = new Group("ASP.NET","ASP.NET3.5");
		Student s1 = new Student("���¸�Ƿ",35);
		Student s2 = new Student("������",45);
		s1.setGroup(g1);
		s2.setGroup(g2);
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(g1);
		session.save(g2);
		session.save(s1);
		session.save(s2);
		session.getTransaction().commit();
		HibernateUtil.closeSession(session);
	}
	
	@Test
	public void queryTest() {
		Session session = HibernateUtil.getSession();
		List<Student> students = session.createQuery("from Student").list();
		for(Student s : students) {
			System.out.println(s.getId() + "-" + s.getName());
		}
	}
	
	/**
	 * 1+N����������3
	 */
	@Test
	public void joinFetchTest() {
		Session session = HibernateUtil.getSession();
		List<Student> students = session.createQuery("from Student s left join fetch s.group g").list();
		for(Student s : students) {
			System.out.println(s.getId() + "-" + s.getName());
		}
	}
	
	/**
	 * 1+N����������4
	 */
	@Test
	public void QBCTest() {
		Session session = HibernateUtil.getSession();
		List<Student> students = session.createCriteria(Student.class).list();
		for(Student s : students) {
			System.out.println(s.getId() + "-" + s.getName());
		}
	}
	
	
}
