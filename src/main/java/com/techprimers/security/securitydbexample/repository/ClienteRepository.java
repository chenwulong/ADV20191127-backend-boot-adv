package com.techprimers.security.securitydbexample.repository;

import com.techprimers.security.securitydbexample.model.Acao;
import com.techprimers.security.securitydbexample.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    @Query(value = "select distinct dados.str_nome from (SELECT acao.a002_str_autor as str_nome FROM tb002_acao acao UNION SELECT acao2.a002_str_reu as str_nome FROM tb002_acao acao2 ) as dados order by dados.str_nome",
            nativeQuery = true)
    List<Cliente> queryListarClientes();
} // AcaoRepository
