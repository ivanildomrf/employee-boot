package br.com.imrf.employee.repository;

import br.com.imrf.employee.framework.Repository;
import br.com.imrf.employee.model.User;

public interface UserRepository extends Repository<User> {
	
	User findUserByEmail(String email);

}
