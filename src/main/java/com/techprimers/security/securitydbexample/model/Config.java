package com.techprimers.security.securitydbexample.model;

import javax.persistence.*;

@Entity
@Table(name = "tb003_config")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "p003_cod_config")
    private int codConfig;

    @Column(name = "a003_str_config")
    private String strConfig;

    @Column(name = "a003_str_valor")
    private String strValor;

    public Config() {
    }

    public int getCodConfig() {
        return codConfig;
    }

    public void setCodConfig(int codConfig) {
        this.codConfig = codConfig;
    }

    public String getStrConfig() {
        return strConfig;
    }

    public void setStrConfig(String strConfig) {
        this.strConfig = strConfig;
    }

    public String getStrValor() {
        return strValor;
    }

    public void setStrValor(String strValor) {
        this.strValor = strValor;
    }

    @Override
    public String toString() {
        return "Config{" +
                "codConfig=" + codConfig +
                ", strConfig='" + strConfig + '\'' +
                ", strValor='" + strValor + '\'' +
                '}';
    }
}
