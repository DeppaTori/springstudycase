package com.mitrais.springlearn.studycase.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mitrais.springlearn.studycase.model.UserRole;

@Repository
public class RoleDAOImpl implements RoleDAO{

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public boolean delete(UserRole userRole) {
		Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from UserRole where userId = :userId and role=:role");
        query.setParameter("userId",userRole.getUserId());
        query.setParameter("role",userRole.getRole());
        int result = query.executeUpdate();
        return result>0?true:false;
	}

	@Override
	public boolean add(UserRole userRole) {
		Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("insert into user_role (user_id,role) values(:userId,:role)");
        query.setParameter("userId",userRole.getUserId());
        query.setParameter("role",userRole.getRole());
        int result = query.executeUpdate();
        return result>0?true:false;
	}

}
