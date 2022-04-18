package com.apipagamentos.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.apipagamentos.model.Pagamento;

import io.swagger.annotations.ApiOperation;

@RestController
public class APIPagamentosController {

	List<Pagamento> listaPagamentos = new ArrayList<Pagamento>(); 
	
	List<Pagamento> listaEstornoPagamentos = new ArrayList<Pagamento>();
	
	@ApiOperation(value = "Informar pagamento", response = Iterable.class, tags = "createPagamento")
	@PostMapping("/createPagamento")
	@ResponseBody
	public Pagamento createPagamento(@RequestBody Pagamento pagamento) {
		
		SecureRandom secureRandom = new SecureRandom();
		 //String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		String CHARACTERS = "0123456789";
		    StringBuilder generatedString= new StringBuilder();
		    StringBuilder generatedString1 = new StringBuilder();
		    for (int i = 0; i < 10; i++) {
		        int randonSequence = secureRandom .nextInt(CHARACTERS.length());
		        generatedString.append(CHARACTERS.charAt(randonSequence));
		    }

		    for (int i = 0; i < 10; i++) {
		        int randonSequence = secureRandom .nextInt(CHARACTERS.length());
		        generatedString1.append(CHARACTERS.charAt(randonSequence));
		    }
		    
		    listaPagamentos.add(pagamento);
		    pagamento.getTransacao().getDescricao().setStatus("AUTORIZADO");
		    pagamento.getTransacao().getDescricao().setNsu(generatedString.toString());
		    pagamento.getTransacao().getDescricao().setCodigoAutorizacao(generatedString1.toString());
			return pagamento;
	}
	
	@ApiOperation(value = "Informar estorno pagamento", response = Iterable.class, tags = "createEstornoPagamento")
	@PostMapping("/createEstornoPagamento")
	@ResponseBody
	public Pagamento createEstornoPagamento(@RequestBody Pagamento pagamento) {
			listaEstornoPagamentos.add(pagamento);
		    pagamento.getTransacao().getDescricao().setStatus("CANCELADO");
			return pagamento;
	}

	@ApiOperation(value = "Listar Todos Pagamentos", response = Iterable.class, tags = "getPagamentos")

	@GetMapping(value = "/getPagamentos")
	@ResponseBody
	public List<Pagamento> getPagamentos() {
		return listaPagamentos;
	}
	
	@ApiOperation(value = "Listar Todos Estorno de Pagamentos", response = Iterable.class, tags = "getEstornoPagamentos")

	@GetMapping(value = "/getEstornoPagamentos")
	@ResponseBody
	public List<Pagamento> getEstornoPagamentos() {
		return listaEstornoPagamentos;
	}

	@ApiOperation(value = "Consultar Pagamento pelo ID", response = Pagamento.class, tags = "getPagamento")
	@GetMapping(value = "/getPagamento/{id}")
	@ResponseBody
	public Pagamento getPagamento(@PathVariable(value = "id") String id) {
		List<Pagamento> pag = listaPagamentos.stream().filter(c -> c.getTransacao().getId().startsWith(id)).collect(Collectors.toList());
		return pag != null && pag.size() > 0 ? pag.get(0) : new Pagamento();
	}
}
