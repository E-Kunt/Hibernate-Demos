import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.ekunt.Util.HibernateUtil;
import com.ekunt.entity.Grade;
import com.ekunt.entity.Student;

/**
 * ����һ�Զ��ϵ��ϵ���༶--->ѧ����
 * ����������ϵ�󣬿��Է���Ĵ�һ�����󵼺�����һ������
 * ע������ķ���
 */
public class HibernateTest {

	/**
	 * ���������
	 * �����༶1��ѧ��1��2�� ��ѧ��1��2������༶1��
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
	
	/**
	 * ��ѯ������
	 * ��ѯ�༶1������ѧ����Ϣ.
	 * 
	 * ����һ�Զ�Ĺ�ϵ�У�
	 * ֻ��ͨ��һ����ѯ�࣬����ͨ��������ѯһ��
	 * ��ֻ��ͨ���༶����ѯѧ��������ͨ��ѧ������ѯ�༶��
	 */
	@Test
	public void getTest(){
		Session session = HibernateUtil.getSession();
		Grade grade = (Grade) session.get(Grade.class, 1);
		System.out.println(grade);
		
		Set<Student> students = grade.getStudents();
		for(Student s : students) {
			System.out.println(s);
		}
		
		HibernateUtil.closeSession(session);
		
	}
	
	/**
	 * ���²�����
	 * �����༶2���ѱ��Ϊ1��ѧ���Ӱ༶1��Ϊ�༶2��
	 */
	@Test
	public void updateTest(){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		Student student = (Student) session.get(Student.class, 1);
		
		Grade grade = new Grade("�����2��","ͨ�Ź���");
		Set<Student> students = new HashSet<Student>();
		students.add(student);
		grade.setStudents(students);
		session.save(grade);
		
		session.getTransaction().commit();
		HibernateUtil.closeSession(session);
		
	}
	
	/**
	 * ɾ��������
	 * ɾ��ѧ��2
	 */
	@Test
	public void deleteTest(){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Student student = (Student) session.get(Student.class, 2);
		session.delete(student);
		session.getTransaction().commit();
		HibernateUtil.closeSession(session);
		
	}
}
