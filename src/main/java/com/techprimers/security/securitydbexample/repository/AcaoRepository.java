package com.techprimers.security.securitydbexample.repository;

import com.techprimers.security.securitydbexample.model.Acao;
import com.techprimers.security.securitydbexample.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AcaoRepository extends JpaRepository<Acao, Integer> {
    @Query(value = "SELECT * FROM tb002_acao acao order by p002_cod_acao desc",
           nativeQuery = true)
    List<Acao> queryListarDesc();
} // AcaoRepository
