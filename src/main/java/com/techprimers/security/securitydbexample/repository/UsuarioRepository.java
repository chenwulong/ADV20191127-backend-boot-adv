package com.techprimers.security.securitydbexample.repository;

import com.techprimers.security.securitydbexample.model.Acao;
import com.techprimers.security.securitydbexample.model.Users;
import com.techprimers.security.securitydbexample.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByStrLogin(String login);

    Optional<Usuario> findByStrTokenReset(String strTokenReset);

    Optional<Usuario> findByStrLoginAndStrSenha(String login, String senha);

    @Query(value = "select * from tb001_usuario usu where usu.p001_cod_usuario > 1 order by usu.p001_cod_usuario desc",
            nativeQuery = true)
    List<Usuario> queryListarDesc();
} // UsuarioRepository
