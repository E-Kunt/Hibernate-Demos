import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.junit.Test;

import com.ekunt.Util.HibernateUtil;
import com.ekunt.entity.Student;

public class HibernateTest {

	@Test
	public void saveTest(){
		Student s1 = new Student("���¸�Ƿ",35);
		Student s2 = new Student("������",45);
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.save(s1);
		session.save(s2);
		session.getTransaction().commit();
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ������
	 * 
	 * LockOptions.UPGRADE �� ��ѯ������forUpdate
	 */
	@Test
	public void pessimisticLockTest(){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		Student s = (Student) session.get(Student.class, 1,LockOptions.UPGRADE);
		int age = s.getAge();
		//do some thing
		age = age + 1;
		s.setAge(age);
		
		session.getTransaction().commit();
		HibernateUtil.closeSession(session);
	}
	
	
}
