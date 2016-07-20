package com.ekunt.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	private static Session session;
	
	/**
	 * ����Configuration���󣬶�ȡhibernate.cfg.xml�ļ�����ɳ�ʼ��
	 */
	static{
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		sessionFactory = cfg.buildSessionFactory(serviceRegistry);
	}

	/**
	 * ��ȡSessionFactory
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * ��ȡSession
	 * @return Session
	 */
	public static Session getSession() {
		session = sessionFactory.openSession();
		return session;
	}
	
	/**
	 * �ر�Session
	 */
	public static void closeSession(Session session) {
		if(session != null) {
			session.close();
			session = null;
		}
	}
	
	

}
