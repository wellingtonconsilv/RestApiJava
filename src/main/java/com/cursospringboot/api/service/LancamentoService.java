package com.cursospringboot.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cursospringboot.api.model.Lancamento;
import com.cursospringboot.api.model.Pessoa;
import com.cursospringboot.api.repository.LancamentoRepository;
import com.cursospringboot.api.repository.PessoaRepository;
import com.cursospringboot.api.repository.ResumoLancamento;
import com.cursospringboot.api.repository.filter.LancamentoFilter;
import com.cursospringboot.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	LancamentoRepository lancamentoRepository;
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	public Lancamento criar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.getOne(lancamento.getPessoa().getId());
		if (pessoa == null || pessoa.isInativo())
			throw new PessoaInexistenteOuInativaException();
		
			return  lancamentoRepository.save(lancamento);
	}
	
	public Lancamento atualizar(Lancamento lancamento, Long id) {
		
		if(!lancamentoRepository.findById(id).isPresent()) {
			throw new EmptyResultDataAccessException("Lancamento não encontrado",1);
		}
		
		Lancamento lancamentoSalvo = lancamentoRepository.findById(id).get();
		lancamentoSalvo.setPessoa(pessoaRepository.findById(lancamento.getPessoa().getId()).get());
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");		
		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	public Page<Lancamento> listar(LancamentoFilter lancamento, Pageable pageable){
		return lancamentoRepository.filtrar(lancamento, pageable);
	}
	
//	public List<Lancamento> listar(LocalDate inicio, LocalDate fim){
//		return lancamentoRepository.findByDataVencimentoBetween(inicio, fim);
//	}
//	
	public void excluir(Long id) {
		lancamentoRepository.deleteById(id);
	}
	
	public Optional<Lancamento> buscarPorId(Long id) {
		return lancamentoRepository.findById(id);
	}

	public Page<ResumoLancamento> resumir(LancamentoFilter lancamento, Pageable pageable) {
		return lancamentoRepository.resumir(lancamento, pageable);
	}
	
	

}
