package com.ekunt.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * @author E-Kunt
 *
 */
public class Grade implements Serializable {
	private int id;
	private String name;
	private String description;
	private Set<Student> students;
	
	public Grade() {
		
	}

	public Grade(String name, String desc) {
		this.name = name;
		this.description = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	//��ʹ��ע�⣺ֱ��@OneToMany��@JoinColumn(name=studentId)���ɡ�
	//@JoinColumnһ��Ҫ�����򵱳ɶ�Զദ������һ�ű�
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Grade [id=" + id + ", name=" + name + ", description=" + description +"]";
	}

	
	
	
}
