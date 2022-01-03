package ifrn.projeto.finolanche.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.projeto.finolanche.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);

}
