package br.com.imrf.employee.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import br.com.imrf.employee.framework.AbstractRepository;
import br.com.imrf.employee.model.User;
import br.com.imrf.employee.repository.UserRepository;

@Component
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

	@Override
	public EntityManager getEntityManager() {
		return super.getEntityManager();
	}

	@Override
	public User findUserByEmail(String email) {

		StringBuilder str = new StringBuilder();
		str.append("     Select user From User user ");
		str.append(" Inner Join user.person person  ");
		str.append("      Where person.email = :email ");

		Query query = this.getEntityManager().createQuery(str.toString());
		query.setParameter("email", email);
		query.setMaxResults(1);

		return (User) query.getSingleResult();
	}

}