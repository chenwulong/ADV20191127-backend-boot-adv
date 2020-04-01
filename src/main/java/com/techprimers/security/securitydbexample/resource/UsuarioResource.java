package com.techprimers.security.securitydbexample.resource;

import com.techprimers.security.securitydbexample.model.MailRequest;
import com.techprimers.security.securitydbexample.model.Usuario;
import com.techprimers.security.securitydbexample.service.EmailService;
import com.techprimers.security.securitydbexample.service.UsuarioService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@RequestMapping("/api/usuario")
@RestController
@CrossOrigin
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService service;

    Logger logger = LoggerFactory.getLogger(UsuarioResource.class);

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam(name = "login") String login,
                                        @RequestParam(name = "senha") String senha) {
        String sha256hex = DigestUtils.sha256Hex(senha);

        Usuario usuarioBanco = this.usuarioService.loadUsuarioByLoginAndSenha(login, sha256hex);

        if (usuarioBanco == null) {
            logger.error("Erro: Usuário não encontrado");
            throw new EntityNotFoundException();
        } else {
            UUID uuid = UUID.randomUUID();
            String strToken = uuid.toString();

            return ResponseEntity.ok("{\"token\" : \"" + strToken + "\"}");
        }
    } // login

    @PostMapping("/resetar-senha")
    public ResponseEntity<String> resetarSenha(@RequestParam(name = "login") String login) {
        Usuario usuarioBanco = this.usuarioService.loadUsuarioByLogin(login);

        if (usuarioBanco == null) {
            throw new EntityNotFoundException();
        } else {
            try {
                String tokenReset = UUID.randomUUID().toString();
                usuarioBanco.setStrTokenReset(tokenReset);
                this.usuarioService.atualizar(usuarioBanco);

                this.service.sendMail(usuarioBanco);

                return ResponseEntity.ok(login);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            } // catch Exceptions
        } // else usuarioBanco
    } // resetarSenha

    @PostMapping("/confirmar-senha")
    public ResponseEntity<String> confirmarSenha(@RequestParam(name = "token") String token,
                                                 @RequestParam(name = "senha") String senha) {

        try {
            Usuario usuarioBanco = this.usuarioService.loadUsuarioByToken(token);

            String sha256hex = DigestUtils.sha256Hex(senha);

            usuarioBanco.setStrSenha(sha256hex);

            this.usuarioService.atualizar(usuarioBanco);

            UUID uuid = UUID.randomUUID();
            String strToken = uuid.toString();

            return ResponseEntity.ok("{\"token\" : \"" + strToken + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } // catch Exceptions
    } // resetarSenha

    @PostMapping("/salvar")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        if (usuario == null || usuario.getStrLogin() == null || usuario.getStrLogin().length() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        if (usuario.getCodUsuario() == null || usuario.getCodUsuario() <= 0) {
            try {
                String tokenReset = UUID.randomUUID().toString();
                usuario.setStrTokenReset(tokenReset);

                this.usuarioService.inserir(usuario);

                this.service.sendMail(usuario);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            } // catch Exceptions
        } else {
            this.usuarioService.atualizar(usuario);
        } // else getCodUsuario

        return ResponseEntity.ok(usuario);
    } // salvar

    @PostMapping("/excluir")
    //@DeleteMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestBody Usuario usuario) {
        this.usuarioService.excluir(usuario);

        return ResponseEntity.ok("{\"token\":\"uuid-teste\"}");
    } // excluir

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listar(@RequestParam(name = "filtro") String strFiltro) {
        List<Usuario> result = new ArrayList<Usuario>();

        logger.info("Listando Usuários!!!!!!!");

        if (strFiltro.trim().length() == 0) {
            result = this.usuarioService.queryListarDesc();
        } else {
            result = this.usuarioService.listarOrContains(strFiltro);

            if (result == null || result.size() == 0) {
                result = this.usuarioService.listarOrJaro(strFiltro);
            } // if result
        } // else strFiltro

        return ResponseEntity.ok(result);
    } // listar

    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscar(@RequestParam(name = "codUsuario") Integer codUsuario) {
        Usuario result = new Usuario();

        result = this.usuarioService.buscar(codUsuario);

        return ResponseEntity.ok(result);
    } // buscar
} // HelloResource
