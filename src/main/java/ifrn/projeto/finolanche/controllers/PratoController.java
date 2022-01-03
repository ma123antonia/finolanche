package ifrn.projeto.finolanche.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifrn.projeto.finolanche.models.Pedido;
import ifrn.projeto.finolanche.models.Prato;
import ifrn.projeto.finolanche.repositories.PedidoRepository;
import ifrn.projeto.finolanche.repositories.PratoRepository;

@Controller
@RequestMapping("/site")
public class PratoController {

	@Autowired
	private PratoRepository pr;
	
	@Autowired
	private PedidoRepository rp;
	
	@GetMapping("/formPrato")
	public String formPrato(Prato prato) {
		return "/site/formPrato";
	}
	
	@PostMapping("formPrato")
	private String salvarPrato(@Valid Prato prato, BindingResult result, RedirectAttributes atributos) {
		if (result.hasErrors()) {
			return formPrato(prato);
		}
		pr.save(prato);
		atributos.addFlashAttribute("mensagem", "Prato cadastrado com sucesso!");
		return "redirect:/site/formPrato";
	}
	
	@GetMapping("/listarPratos")
	public ModelAndView listarPratos() {
		List<Prato> pratos = pr.findAll();
		ModelAndView mv = new ModelAndView("site/listarPratos");
		mv.addObject("pratos", pratos);
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView detalharPrato(@PathVariable Long id, Pedido pedido) {
		ModelAndView mv = new ModelAndView();
		Optional<Prato> opt = pr.findById(id);

		if (opt.isEmpty()) {
			mv.setViewName("redirect:/site/listarPratos");
			return mv;
		}

		mv.setViewName("site/detalhes");
		Prato prato = opt.get();
		mv.addObject("prato", prato);

		List<Pedido> pedidos = rp.findByPrato(prato);
		mv.addObject("pedidos", pedidos);

		return mv;
	}
	
	@GetMapping("/{id}/remover")
	public String apagarPrato(@PathVariable Long id, RedirectAttributes attributos) {

		Optional<Prato> opt = pr.findById(id);

		if (!opt.isEmpty()) {
			Prato prato = opt.get();

			List<Pedido> pedidos = rp.findByPrato(prato);

			rp.deleteAll(pedidos);
			pr.delete(prato);
			attributos.addFlashAttribute("mensagem", "Prato removido com sucesso!");

		}

		return "redirect:/site/listarPratos";

	}
	
	@GetMapping("/{id}/selecionar")
	public ModelAndView selecionarPrato(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView();
		Optional<Prato> opt = pr.findById(id);
		if (opt.isEmpty()) {
			mv.setViewName("redirect:/site");
			return mv;
		}

		Prato prato = opt.get();
		mv.setViewName("site/formPrato");
		mv.addObject("prato", prato);
		return mv;
	}
	
	@PostMapping("/{idPrato}")
	public ModelAndView salvarPedido(@PathVariable Long idPrato, @Valid Pedido pedido,
			BindingResult result, RedirectAttributes atributos) {

		if (result.hasErrors()) {
			atributos.addFlashAttribute("mensagem", "Verifique os campos!");
			return detalharPrato(idPrato, pedido);
		}

		Optional<Prato> opt = pr.findById(idPrato);
		ModelAndView mv = new ModelAndView();

		

		Prato prato = opt.get();
		pedido.setPrato(prato);

		rp.save(pedido);
		atributos.addFlashAttribute("mensagem", "Pedido realizado com sucesso!");

		mv.setViewName("redirect:/site/{idPrato}");
		return mv;
	}
	
	@GetMapping("/{idPrato}/pedidos/{idPedido}/selecionar")
	public ModelAndView selecionarPedido(@PathVariable Long idPrato, @PathVariable Long idPedido) {
		ModelAndView mv = new ModelAndView();

		Optional<Prato> optPrato = pr.findById(idPrato);
		Optional<Pedido> optPedido = rp.findById(idPedido);

		if (optPrato.isEmpty() || optPedido.isEmpty()) {
			mv.setViewName("redirect:/site");
			return mv;
		}

		Prato prato = optPrato.get();
		Pedido pedido = optPedido.get();

		if (prato.getId() != pedido.getPrato().getId()) {
			mv.setViewName("redirect:/site");
			return mv;

		}

		mv.setViewName("site/detalhes");
		mv.addObject("pedido", pedido);
		mv.addObject("prato", prato);
		mv.addObject("pedidos", rp.findByPrato(prato));

		return mv;
	}
	
	@GetMapping("/{idPrato}/pedidos/{id}/remover")
	public String apagarPedido(@PathVariable Long id, RedirectAttributes attributes) {
		Optional<Pedido> opt = rp.findById(id);
		if (!opt.isEmpty()) {
			rp.deleteById(id);

			attributes.addFlashAttribute("mensagem", "Pedido removida com sucesso!");

		}

		return "redirect:/site/{idPrato}";

	}
	
}
