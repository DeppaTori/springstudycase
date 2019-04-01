package com.mitrais.springlearn.studycase.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mitrais.springlearn.studycase.model.User;
import com.mitrais.springlearn.studycase.model.UserRole;

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	

	@Override
	public List<User> list() {
		return sessionFactory.getCurrentSession()
                .createQuery("from User")
                .list();
	
	}


	@Override
	public User find(Long id) {
		return (User) sessionFactory.getCurrentSession()
                .createQuery("from User where id=:id")
                .setParameter("id", id)
                .uniqueResult();
	}


	@Override
	public boolean update(User user) {
		Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("update User set user_name =:userName, password =:password where id = :id");
        query.setParameter("userName", user.getUsername());  
        query.setParameter("password", user.getPassword()); 
        query.setParameter("id",user.getId());
        int result = query.executeUpdate();
        
        /// store new user role
        Set<UserRole> userRoles = user.getUserRoles();
        if(userRoles != null) {
        	for(UserRole userRole: userRoles) {
            	Query queryRole = session.createSQLQuery("insert into user_role (user_id,role) values(:userId,:role)");
            	queryRole.setParameter("userId", user.getId());  
            	queryRole.setParameter("role", userRole.getRole());
            	int resultRole = queryRole.executeUpdate();
            }
        }
        
        // end store user role
        
        return result>0?true:false;
	}


	@Override
	public boolean save(User user) {
		Session session = this.sessionFactory.getCurrentSession();

       // Query query = session.createSQLQuery("insert into User (user_name,password) values(:username,:password)");
      //  query.setParameter("username", user.getUsername());  
       // query.setParameter("password", user.getPassword()); 
      //  int result = query.executeUpdate();
		 session.save(user);
		
        return user.getId() > 0 ?true:false;
	}


	@Override
	public boolean delete(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from User where id = :id");
        query.setParameter("id",id);
        int result = query.executeUpdate();
        return result>0?true:false;
	}


	@Override
	public User find(String username) {
		return (User) sessionFactory.getCurrentSession()
                .createQuery("from User u where u.username=:username")
                .setParameter("username", username)
                .uniqueResult();
	}



}
