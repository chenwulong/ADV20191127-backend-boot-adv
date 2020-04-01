package com.techprimers.security.securitydbexample.service;

import com.techprimers.security.securitydbexample.commons.JaroDistance;
import com.techprimers.security.securitydbexample.model.Acao;
import com.techprimers.security.securitydbexample.model.Cliente;
import com.techprimers.security.securitydbexample.model.Config;
import com.techprimers.security.securitydbexample.model.Usuario;
import com.techprimers.security.securitydbexample.repository.AcaoRepository;
import com.techprimers.security.securitydbexample.repository.ClienteRepository;
import com.techprimers.security.securitydbexample.repository.ConfigRepository;
import com.techprimers.security.securitydbexample.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AcaoService {

    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ConfigRepository configRepository;

    public List<Acao> queryListarDesc() {
        List<Acao> listAcao = acaoRepository.queryListarDesc();

        return listAcao;
    } // queryListarDesc

    public List<Acao> listarOrContains(String filtro) {
        List<Acao> listAcao = acaoRepository.queryListarDesc();
        List<Acao> listAcaoRetorno = new ArrayList<>();

        String[] filtroSeparado = filtro.split(" ");

        for (Acao acao : listAcao) {
            // Dont add duplicates
            if (listAcaoRetorno.contains(acao)) {
                continue;
            } // if contains

            String codAcao = acao.getCodAcao() == null ? null : String.valueOf(acao.getCodAcao()).trim().toLowerCase();
            String numPasta = acao.getNumPasta() == null ? null : acao.getNumPasta().trim().toLowerCase();
            String strAutor = acao.getStrAutor() == null ? null : acao.getStrAutor().trim().toLowerCase();
            String strReu = acao.getStrReu() == null ? null : acao.getStrReu().trim().toLowerCase();
            String strObservacao = acao.getStrObservacao() == null ? null : acao.getStrObservacao().trim().toLowerCase();

            String strCompare = codAcao + numPasta + strAutor + strReu + strObservacao;

            for (String filtroItem : filtroSeparado) {
                String filtroClean = filtroItem.trim().toLowerCase();

                // If any field has any filter word, add to return array
                /*if (       (codAcao.contains(filtroClean))
                        || (numPasta.contains(filtroClean))
                        || (strAutor.contains(filtroClean))
                        || (strReu.contains(filtroClean))
                        || (strObservacao.contains(filtroClean))
                    ) {*/
                if (strCompare.contains(filtroClean)) {
                    listAcaoRetorno.add(acao);
                } // if contains
            } // for filtroSeparado
        } // for listAcao

        return listAcaoRetorno;
    } // listarOrContains

    public List<Acao> listarAndContains(String filtro) {
        List<Acao> listAcao = acaoRepository.queryListarDesc();
        List<Acao> listAcaoRetorno = new ArrayList<>();

        String[] filtroSeparado = filtro.split(" ");

        for (Acao acao : listAcao) {
            // Dont add duplicates
            if (listAcaoRetorno.contains(acao)) {
                continue;
            } // if contains

            String codAcao = acao.getCodAcao() == null ? null : String.valueOf(acao.getCodAcao()).trim().toLowerCase();
            String numPasta = acao.getNumPasta() == null ? null : acao.getNumPasta().trim().toLowerCase();
            String strAutor = acao.getStrAutor() == null ? null : acao.getStrAutor().trim().toLowerCase();
            String strReu = acao.getStrReu() == null ? null : acao.getStrReu().trim().toLowerCase();
            String strObservacao = acao.getStrObservacao() == null ? null : acao.getStrObservacao().trim().toLowerCase();

            String strCompare = codAcao + numPasta + strAutor + strReu + strObservacao;

            boolean containAllWords = true;

            for (String filtroItem : filtroSeparado) {
                String filtroClean = filtroItem.trim().toLowerCase();

                // If any field has EVERY filter word, add to return array
                /*if (!(       (codAcao.contains(filtroClean))
                        || (numPasta.contains(filtroClean))
                        || (strAutor.contains(filtroClean))
                        || (strReu.contains(filtroClean))
                        || (strObservacao.contains(filtroClean))
                    )) {*/
                if (!strCompare.contains(filtroClean)) {
                    containAllWords = false;
                } // if contains
            } // for filtroSeparado

            // if EVERY filtered WORD is present on any of the fields
            if (containAllWords) {
                listAcaoRetorno.add(acao);
            } // if containAllWords
        } // for listAcao

        return listAcaoRetorno;
    } // listarAndContains

    public List<Acao> listarOrJaro(String filtro) {
        List<Acao> listAcao = acaoRepository.queryListarDesc();
        List<Acao> listAcaoRetorno = new ArrayList<>();

        String[] filtroSeparado = filtro.split(" ");

        Config jaroConfig = this.configRepository.findByCodConfig(this.configRepository.JARO_PERCENT);

        //System.out.println("jaroConfig.getStrValor(): " + jaroConfig.getStrValor());

        double jaroPercent = Double.parseDouble(jaroConfig.getStrValor());

        // Converter para deixar independente do formato (, e .)
        jaroPercent = jaroPercent / 100;

        System.out.println("Banco: " + jaroPercent);

        for (Acao acao : listAcao) {
            // Skip duplicates
            if (listAcaoRetorno.contains(acao)) {
                continue;
            } // contains

            String codAcao = acao.getCodAcao() == null ? null : String.valueOf(acao.getCodAcao()).trim().toLowerCase();
            String numPasta = acao.getNumPasta() == null ? null : acao.getNumPasta().trim().toLowerCase();
            String strAutor = acao.getStrAutor() == null ? null : acao.getStrAutor().trim().toLowerCase();
            String strReu = acao.getStrReu() == null ? null : acao.getStrReu().trim().toLowerCase();
            String strObservacao = acao.getStrObservacao() == null ? null : acao.getStrObservacao().trim().toLowerCase();

            for (String filtroItem : filtroSeparado) {
                String filtroClean = filtroItem.trim().toLowerCase();

                // If any field has any filter word, add to return array
                if (       (JaroDistance.distance(codAcao, filtroClean) >= jaroPercent)
                        || (JaroDistance.distance(numPasta, filtroClean) >= jaroPercent)
                        || (JaroDistance.distance(strAutor, filtroClean) >= jaroPercent)
                        || (JaroDistance.distance(strReu, filtroClean) >= jaroPercent)
                        || (JaroDistance.distance(strObservacao, filtroClean) >= jaroPercent)
                ) {
                    listAcaoRetorno.add(acao);
                } // if contains
            } // for filtroSeparado
        } // for listAcao

        return listAcaoRetorno;
    } // listarOrJaro

    public List<Acao> listarAndJaro(String filtro) {
        List<Acao> listAcao = acaoRepository.queryListarDesc();
        List<Acao> listAcaoRetorno = new ArrayList<>();

        String[] filtroSeparado = filtro.split(" ");

        Config jaroConfig = this.configRepository.findByCodConfig(this.configRepository.JARO_PERCENT);

        //System.out.println("jaroConfig.getStrValor(): " + jaroConfig.getStrValor());

        double jaroPercent = Double.parseDouble(jaroConfig.getStrValor());

        // Converter para deixar independente do formato (, e .)
        jaroPercent = jaroPercent / 100;

        System.out.println("Banco: " + jaroPercent);

        for (Acao acao : listAcao) {
            // Skip duplicates
            if (listAcaoRetorno.contains(acao)) {
                continue;
            } // contains

            String codAcao = acao.getCodAcao() == null ? null : String.valueOf(acao.getCodAcao()).trim().toLowerCase();
            String numPasta = acao.getNumPasta() == null ? null : acao.getNumPasta().trim().toLowerCase();
            String strAutor = acao.getStrAutor() == null ? null : acao.getStrAutor().trim().toLowerCase();
            String strReu = acao.getStrReu() == null ? null : acao.getStrReu().trim().toLowerCase();
            String strObservacao = acao.getStrObservacao() == null ? null : acao.getStrObservacao().trim().toLowerCase();

            boolean containAllWords = true;

            for (String filtroItem : filtroSeparado) {
                String filtroClean = filtroItem.trim().toLowerCase();

                // If any field has EVERY filter word, add to return array
                if (!(       (JaroDistance.distance(codAcao, filtroClean) >= jaroPercent)
                        || (JaroDistance.distance(numPasta, filtroClean) >= jaroPercent)
                        || (JaroDistance.distance(strAutor, filtroClean) >= jaroPercent)
                        || (JaroDistance.distance(strReu, filtroClean) >= jaroPercent)
                        || (JaroDistance.distance(strObservacao, filtroClean) >= jaroPercent)
                )) {
                    containAllWords = false;
                } // if contains
            } // for filtroSeparado

            // if EVERY filtered WORD is present on any of the fields
            if (containAllWords) {
                listAcaoRetorno.add(acao);
            }
        } // for listAcao

        return listAcaoRetorno;
    } // listarAndJaro

    public Acao buscar(Integer codAcao) {
        return this.acaoRepository.getOne(codAcao);
    } // buscar

    public Acao inserir(Acao acao) {
        acao.setDatInsercao(new Date());
        this.acaoRepository.save(acao);
        return acao;
    } // inserir

    public Acao atualizar(Acao acao) {
        acao.setDatAlteracao(new Date());
        this.acaoRepository.save(acao);
        return acao;
    } // atualizar

    public void excluir(Acao acao) {
        this.acaoRepository.delete(acao);
    } // excluir

    public List<String> listarClientes(String nomeCliente) {
        List<Cliente> listAcao = clienteRepository.queryListarClientes();

        List<String> retorno = new ArrayList<>();

        String filterOk = nomeCliente.trim().toLowerCase();

        Config firstClients = this.configRepository.findByCodConfig(this.configRepository.FIRST_CLIENTS);

        int qtdClients = Integer.parseInt(firstClients.getStrValor());

        // search by contains
        for (Cliente acao : listAcao) {
            String clienteAtual = acao.getStrNome().trim().toLowerCase();

            if (retorno.contains(clienteAtual)) {
                continue;
            } // if contains on list

            if (clienteAtual.contains(filterOk)) {
                retorno.add(acao.getStrNome());

                if (retorno.size() >= qtdClients) {
                    break; // exit when reached max clients on list
                }
            } // if contains keyword
        } // for listAcao

        // if could not locate with contains, search by jaro
        if (retorno.size() == 0) {
            Config jaroConfig = this.configRepository.findByCodConfig(this.configRepository.JARO_PERCENT);

            double jaroPercent = Double.parseDouble(jaroConfig.getStrValor());

            // Converter para deixar independente do formato (, e .)
            jaroPercent = jaroPercent / 100;

            for (Cliente acao : listAcao) {
                if (retorno.contains(acao.getStrNome())) {
                    continue;
                } // if contains on list

                String clienteAtual = acao.getStrNome().trim().toLowerCase();

                // jaro compare
                if (JaroDistance.distance(clienteAtual, nomeCliente) >= jaroPercent) {
                    retorno.add(acao.getStrNome());

                    if (retorno.size() >= qtdClients) {
                        break; // exit when reached max clients on list
                    }
                } // if JaroDistance
            } // for listAcao
        } // if size

        return retorno;
    } // listarClientes
} // AcaoService
