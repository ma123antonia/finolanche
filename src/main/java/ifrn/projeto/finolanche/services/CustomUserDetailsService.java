package ifrn.projeto.finolanche.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ifrn.projeto.finolanche.models.Usuario;
import ifrn.projeto.finolanche.repositories.UsuarioRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository ur;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = ur.findByEmail(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}
		
		return usuario;
	}
	
}
