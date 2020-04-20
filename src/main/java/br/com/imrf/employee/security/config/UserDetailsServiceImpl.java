package br.com.imrf.employee.security.config;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.imrf.employee.framework.CheckerUtil;
import br.com.imrf.employee.model.User;
import br.com.imrf.employee.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = null;
		try {
			user = this.userRepository.findUserByEmail(email);
		} catch (NoResultException e) {
			throw new UsernameNotFoundException("Usuário informado não encontrado");
		}
		
		if (CheckerUtil.isNull(user)) {
			throw new UsernameNotFoundException("Credenciais informadas são inválidas, favor tentar novamente");
		}

		return UserDetailsImpl.build(user);
	}

}
