package com.techprimers.security.securitydbexample.model;

import javax.persistence.*;

@Entity
@Table(name = "tb001_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "p001_cod_usuario")
    private Integer codUsuario;

    @Column(name = "a001_str_login")
    private String strLogin;

    @Column(name = "a001_str_senha")
    private String strSenha;

    @Column(name = "a001_str_token_reset")
    private String strTokenReset;

    public Usuario() {
    }

    public Integer getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getStrLogin() {
        return strLogin;
    }

    public void setStrLogin(String strLogin) {
        this.strLogin = strLogin;
    }

    public String getStrSenha() {
        return strSenha;
    }

    public void setStrSenha(String strSenha) {
        this.strSenha = strSenha;
    }

    public String getStrTokenReset() {
        return strTokenReset;
    }

    public void setStrTokenReset(String strTokenReset) {
        this.strTokenReset = strTokenReset;
    }

    public Usuario(final Usuario usuario) {
        this.codUsuario = usuario.codUsuario;
        this.strLogin = usuario.strLogin;
        this.strSenha = usuario.strSenha;
        this.strTokenReset = usuario.strTokenReset;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "codUsuario=" + codUsuario +
                ", strLogin='" + strLogin + '\'' +
                ", strSenha='" + strSenha + '\'' +
                ", strTokenReset='" + strTokenReset + '\'' +
                '}';
    }
}
