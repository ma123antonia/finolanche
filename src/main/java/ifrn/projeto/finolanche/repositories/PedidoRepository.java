package ifrn.projeto.finolanche.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.projeto.finolanche.models.Pedido;
import ifrn.projeto.finolanche.models.Prato;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	List<Pedido> findByPrato(Prato prato);
	
}
