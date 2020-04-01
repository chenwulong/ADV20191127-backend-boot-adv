package com.techprimers.security.securitydbexample.service;

import com.techprimers.security.securitydbexample.commons.JaroDistance;
import com.techprimers.security.securitydbexample.model.Config;
import com.techprimers.security.securitydbexample.model.CustomUserDetails;
import com.techprimers.security.securitydbexample.model.Users;
import com.techprimers.security.securitydbexample.model.Usuario;
import com.techprimers.security.securitydbexample.repository.ConfigRepository;
import com.techprimers.security.securitydbexample.repository.UsersRepository;
import com.techprimers.security.securitydbexample.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConfigRepository configRepository;

    public Usuario loadUsuarioByLogin(String login) throws UsernameNotFoundException {
        Optional<Usuario> optionalUsuarios = usuarioRepository.findByStrLogin(login);

        optionalUsuarios.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return optionalUsuarios.map(Usuario::new).get();
    }

    public Usuario loadUsuarioByToken(String strTokenReset) throws UsernameNotFoundException {
        Optional<Usuario> optionalUsuarios = usuarioRepository.findByStrTokenReset(strTokenReset);

        optionalUsuarios.orElseThrow(() -> new UsernameNotFoundException("User token not found"));

        return optionalUsuarios.map(Usuario::new).get();
    }

    public Usuario loadUsuarioByLoginAndSenha(String login, String senha) throws UsernameNotFoundException {
        Optional<Usuario> optionalUsuarios = usuarioRepository.findByStrLoginAndStrSenha(login, senha);

        optionalUsuarios.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return optionalUsuarios.map(Usuario::new).get();
    }

    public List<Usuario> queryListarDesc() {
        List<Usuario> listUsuario = usuarioRepository.queryListarDesc();

        return listUsuario;
    } // queryListarDesc

    public List<Usuario> listarOrContains(String filtro) {
        List<Usuario> listUsuario = usuarioRepository.queryListarDesc();
        List<Usuario> listUsuarioRetorno = new ArrayList<>();

        String[] filtroSeparado = filtro.split(" ");

        for (Usuario usuario : listUsuario) {
            // Dont add duplicates
            if (listUsuarioRetorno.contains(usuario)) {
                continue;
            } // if contains

            String codUsuario = String.valueOf(usuario.getCodUsuario()).trim().toLowerCase();
            String strLogin = usuario.getStrLogin().trim().toLowerCase();

            for (String filtroItem : filtroSeparado) {
                String filtroClean = filtroItem.trim().toLowerCase();

                // If any field has any filter word, add to return array
                if (       (codUsuario.contains(filtroClean))
                        || (strLogin.contains(filtroClean))
                ) {
                    listUsuarioRetorno.add(usuario);
                } // if contains
            } // for filtroSeparado
        } // for listAcao

        return listUsuarioRetorno;
    } // listarOrContains

    public List<Usuario> listarOrJaro(String filtro) {
        List<Usuario> listUsuario = usuarioRepository.queryListarDesc();
        List<Usuario> listUsuarioRetorno = new ArrayList<>();

        String[] filtroSeparado = filtro.split(" ");

        Config jaroConfig = this.configRepository.findByCodConfig(this.configRepository.JARO_PERCENT);

        double jaroPercent = Double.parseDouble(jaroConfig.getStrValor());

        // Converter para deixar independente do formato (, e .)
        jaroPercent = jaroPercent / 100;

        for (Usuario usuario : listUsuario) {
            // Skip duplicates
            if (listUsuarioRetorno.contains(usuario)) {
                continue;
            } // contains

            String codUsuario = String.valueOf(usuario.getCodUsuario()).trim().toLowerCase();
            String strLogin = usuario.getStrLogin().trim().toLowerCase();

            for (String filtroItem : filtroSeparado) {
                String filtroClean = filtroItem.trim().toLowerCase();

                // If any field has any filter word, add to return array
                if (       (JaroDistance.distance(codUsuario, filtroClean) >= jaroPercent)
                        || (JaroDistance.distance(strLogin, filtroClean) >= jaroPercent)
                ) {
                    listUsuarioRetorno.add(usuario);
                } // if contains
            } // for filtroSeparado
        } // for listUsuario

        return listUsuarioRetorno;
    } // listarOrJaro

    public Usuario buscar(Integer codUsuario) {
        return this.usuarioRepository.getOne(codUsuario);
    } // buscar

    public Usuario inserir(Usuario usuario) {
        UUID uuid = UUID.randomUUID();
        usuario.setStrTokenReset(uuid.toString());

        this.usuarioRepository.save(usuario);

        System.out.println("Inserido usuario: " + usuario.getStrLogin() + " token: " + usuario.getStrTokenReset());

        // TODO: enviar e-mail com o uuid gerado

        return usuario;
    } // inserir

    public Usuario atualizar(Usuario usuario) {
        this.usuarioRepository.save(usuario);
        return usuario;
    } // atualizar

    public void excluir(Usuario usuario) {
        this.usuarioRepository.delete(usuario);
    } // excluir
} // UsuarioService
