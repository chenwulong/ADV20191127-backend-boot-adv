package com.techprimers.security.securitydbexample.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb002_acao")
@ApiModel(description = "Details about Acao")
public class Acao {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "p002_cod_acao")
    @ApiModelProperty(notes = "The unique code identifier of the acao")
    private Integer codAcao;

    @Column(name = "a002_num_pasta")
    @ApiModelProperty(notes = "The folder where the action is stored")
    private String numPasta;

    @Column(name = "a002_str_autor")
    @ApiModelProperty(notes = "The Acao author")
    private String strAutor;

    @Column(name = "a002_str_reu")
    @ApiModelProperty(notes = "The Acao accused person")
    private String strReu;

    @Column(name = "a002_str_observacao")
    @ApiModelProperty(notes = "The Acao aditional observations")
    private String strObservacao;

    @Column(name = "a002_dat_insercao")
    private Date datInsercao;

    @Column(name = "f001_cod_usuario_insercao")
    private int codUsuarioInsercao;

    @Column(name = "a002_dat_alteracao")
    private Date datAlteracao;

    @Column(name = "f001_cod_usuario_alteracao")
    private int codUsuarioAlteracao;

    public Acao() {
    }

    public Acao(Acao acao) {
        this.codAcao = acao.codAcao;
        this.numPasta = acao.numPasta;
        this.strAutor = acao.strAutor;
        this.strReu = acao.strReu;
        this.strObservacao = acao.strObservacao;
        this.datInsercao = acao.datInsercao;
        this.codUsuarioInsercao = acao.codUsuarioInsercao;
        this.datAlteracao = acao.datAlteracao;
        this.codUsuarioAlteracao = acao.codUsuarioAlteracao;
    }

    public Integer getCodAcao() {
        return codAcao;
    }

    public void setCodAcao(Integer codAcao) {
        this.codAcao = codAcao;
    }

    public String getNumPasta() {
        return numPasta;
    }

    public void setNumPasta(String numPasta) {
        this.numPasta = numPasta;
    }

    public String getStrAutor() {
        return strAutor;
    }

    public void setStrAutor(String strAutor) {
        this.strAutor = strAutor;
    }

    public String getStrReu() {
        return strReu;
    }

    public void setStrReu(String strReu) {
        this.strReu = strReu;
    }

    public String getStrObservacao() {
        return strObservacao;
    }

    public void setStrObservacao(String strObservacao) {
        this.strObservacao = strObservacao;
    }

    public Date getDatInsercao() {
        return datInsercao;
    }

    public void setDatInsercao(Date datInsercao) {
        this.datInsercao = datInsercao;
    }

    public int getCodUsuarioInsercao() {
        return codUsuarioInsercao;
    }

    public void setCodUsuarioInsercao(int codUsuarioInsercao) {
        this.codUsuarioInsercao = codUsuarioInsercao;
    }

    public Date getDatAlteracao() {
        return datAlteracao;
    }

    public void setDatAlteracao(Date datAlteracao) {
        this.datAlteracao = datAlteracao;
    }

    public int getCodUsuarioAlteracao() {
        return codUsuarioAlteracao;
    }

    public void setCodUsuarioAlteracao(int codUsuarioAlteracao) {
        this.codUsuarioAlteracao = codUsuarioAlteracao;
    }

    @Override
    public String toString() {
        return "Acao{" +
                "codAcao=" + codAcao +
                ", numPasta='" + numPasta + '\'' +
                ", strAutor='" + strAutor + '\'' +
                ", strReu='" + strReu + '\'' +
                ", strObservacao='" + strObservacao + '\'' +
                ", datInsercao=" + datInsercao +
                ", codUsuarioInsercao=" + codUsuarioInsercao +
                ", datAlteracao=" + datAlteracao +
                ", codUsuarioAlteracao=" + codUsuarioAlteracao +
                '}';
    }
}
