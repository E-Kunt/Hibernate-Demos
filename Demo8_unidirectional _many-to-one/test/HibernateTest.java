import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.ekunt.Util.HibernateUtil;
import com.ekunt.entity.Grade;
import com.ekunt.entity.Student;

/**
 * ������һ��ϵ��ϵ��ѧ��--->�༶��
 * ע������ķ���
 */
public class HibernateTest {

	/**
	 * ���������
	 * �����༶1��ѧ��1��2�� ��ѧ��1��2������༶1��
	 */
	@Test
	public void saveTest(){
		Grade g = new Grade("JAVAһ��","JavaSE");
		Student s1 = new Student("������",35);
		Student s2 = new Student("������",45);
		s1.setGrade(g);
		s2.setGrade(g);
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(g);
		session.save(s1);
		session.save(s2);
		session.getTransaction().commit();
		HibernateUtil.closeSession(session);
	}
	
	
}
