package ifrn.projeto.finolanche.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.projeto.finolanche.models.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {

	Papel findByNome(String nome);
	
}
