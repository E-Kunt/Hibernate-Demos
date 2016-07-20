import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.ekunt.Util.HibernateUtil;
import com.ekunt.entity.Grade;
import com.ekunt.entity.Student;

public class HibernateTest {

	/**
	 * ���Զ�Զ�˫�����
	 */
	@Test
	public void saveTest(){
		Student s1 = new Student("����",18);
		Student s2 = new Student("����",20);
		Set<Student> students = new HashSet<Student>();
		students.add(s1);
		students.add(s2);
		Grade grade = new Grade("�����1��","��ࣺ�����1�࣬С�ࣺ���2��");
		grade.setStudents(students);
		
		Session session = HibernateUtil.getSession();
		Transaction t = session.beginTransaction();
		session.save(grade);
		session.save(s1);
		session.save(s2);
		t.commit();
		HibernateUtil.closeSession(session);
		
	}
}
