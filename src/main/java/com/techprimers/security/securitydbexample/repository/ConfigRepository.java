package com.techprimers.security.securitydbexample.repository;

import com.techprimers.security.securitydbexample.model.Config;
import com.techprimers.security.securitydbexample.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, Integer> {

    public static int JARO_PERCENT   = 1;
    public static int FRONT_ADDRESS  = 2;
    public static int FRONT_PROTOCOL = 3;
    public static int FRONT_PORT     = 4;
    public static int FIRST_CLIENTS  = 5;

    Config findByCodConfig(int codConfig);
} // UsuarioRepository
