import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.ekunt.Util.HibernateUtil;
import com.ekunt.entity.Grade;
import com.ekunt.entity.Student;

/**
 * ˫���ϵ���༶<--->ѧ����
 * 
 * ����ע�⣬@OneToMany(��������mappedby) @ManyToOne 
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
		
		//����˫�������ϵ,�ڰ༶��һ�������ÿ��Ʒ�ת����inverse=true��һ������ά�͹�����ϵ�������ٷ��������update��䣬�������
		grade.setStudents(students);
		s1.setGrade(grade);
		s2.setGrade(grade);
		
		Session session = HibernateUtil.getSession();
		Transaction t = session.beginTransaction();
		
		//�ڰ༶��һ�������ü�������cascade��Ϊnone��,�����ڶ�ӦȨ�޲���,��save�У��������ֶ�saveѧ�����෽����Hibernate���Զ�����save��
		//ͬ��ѧ�����෽��Ҳ������cascade���ԡ�
	
		//session.save(grade);
		session.save(s1);
		session.save(s2);
		
		t.commit();
		HibernateUtil.closeSession(session);
		
	}
	
	/**
	 * ��ѯ������
	 * ����˫�������ͨ����ѯĳһ�����ݺ󣬿���ֱ�ӵ�����ѯ����һ����������ݡ������з������ơ�
	 */
	@Test
	public void getStudentFromGradeTest(){
		Session session = HibernateUtil.getSession();
		Grade grade = (Grade) session.get(Grade.class, 1);
		Set<Student> students = grade.getStudents();
		for(Student s : students) {
			System.out.println(s);
		}
		HibernateUtil.closeSession(session);
	}
	
	/**
	 * ��ѯ������
	 * ����˫�������ͨ����ѯĳһ�����ݺ󣬿���ֱ�ӵ�����ѯ����һ����������ݡ������з������ơ�
	 */
	@Test
	public void getGradeFromStudentTest(){
		Session session = HibernateUtil.getSession();
		Student s = (Student)session.get(Student.class, 2);
		Grade g = s.getGrade();
		System.out.println(g);
		HibernateUtil.closeSession(session);
	}
}
