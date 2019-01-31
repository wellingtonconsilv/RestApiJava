package com.cursospringboot.api.repository.filter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cursospringboot.api.model.Lancamento;
import com.cursospringboot.api.repository.ResumoLancamento;

public interface LancamentoRepositoryQuery {
	public Page<Lancamento> filtrar(LancamentoFilter lancamento, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
