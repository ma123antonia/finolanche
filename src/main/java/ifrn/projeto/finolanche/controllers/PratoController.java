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
	
}
