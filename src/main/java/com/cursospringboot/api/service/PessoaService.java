package com.cursospringboot.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cursospringboot.api.model.Pessoa;
import com.cursospringboot.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa criar(Pessoa pessoa) {
		if(pessoa.getId() != null) {
			throw new IllegalArgumentException("campo id não permitido");
		}
		return pessoaRepository.save(pessoa);
	}
	
	public Pessoa atualizar(Long id, Pessoa pessoa) {
		if(!pessoaRepository.findById(id).isPresent()) {
			throw new EmptyResultDataAccessException("Pessoa não cadastrada",1);
		}	
		Pessoa pessoaSalva = pessoaRepository.findById(id).get();
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return pessoaRepository.save(pessoaSalva);	
	}	
	
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}
	
	
	public Optional<Pessoa> buscarPorId(Long id) {
		return pessoaRepository.findById(id);
	}
	
	public void excluir(Long id) {
		pessoaRepository.deleteById(id);
	}
	
	public void mudarAtivo(Long id, boolean ativo) {
		Pessoa pessoaSalva = pessoaRepository.findById(id).get();
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
}
