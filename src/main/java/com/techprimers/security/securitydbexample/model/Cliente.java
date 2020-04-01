package com.techprimers.security.securitydbexample.model;

import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dados")
public class Cliente {
/*
    @Id
    @Column(name = "rownumber")
    private Integer rowNumber;
*/
    @Id
    @Column(name = "str_nome")
    private String strNome;

    public String getStrNome() {
        return strNome;
    }

    public void setStrNome(String strNome) {
        this.strNome = strNome;
    }

    public Cliente() {
    }

    public Cliente(Cliente cliente) {
        //this.rowNumber = cliente.rowNumber;
        this.strNome = cliente.strNome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                //"rowNumber=" + rowNumber +
                ", strNome='" + strNome + '\'' +
                '}';
    }

    /*
    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }*/
}
