package com.techprimers.security.securitydbexample.resource;

import com.techprimers.security.securitydbexample.commons.Pager;
import com.techprimers.security.securitydbexample.model.Acao;
import com.techprimers.security.securitydbexample.model.Cliente;
import com.techprimers.security.securitydbexample.model.Usuario;
import com.techprimers.security.securitydbexample.repository.AcaoRepository;
import com.techprimers.security.securitydbexample.service.AcaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/acao")
@RestController
@CrossOrigin
public class AcaoResource {

    @Autowired
    private AcaoService acaoService;

    Logger logger = LoggerFactory.getLogger(AcaoResource.class);

    @PostMapping("/salvar")
    public ResponseEntity<Acao> salvar(@RequestBody Acao acao) {
        if (acao.getCodAcao() == null || acao.getCodAcao() <= 0) {
            this.acaoService.inserir(acao);
        } else {
            this.acaoService.atualizar(acao);
        } // else getCodAcao

        return ResponseEntity.ok(acao);
    } // salvar

    @PostMapping("/excluir")
    //@DeleteMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestBody Acao acao) {
        this.acaoService.excluir(acao);

        return ResponseEntity.ok("{\"token\":\"uuid-teste\"}");
    } // excluir

    @GetMapping("/listar")
    @ApiOperation(value = "List all Acao objects on database",
                notes = "provide filter parameter for filtering data results",
                response = ResponseEntity.class)
    public ResponseEntity<Map<String, Object>> listar(@ApiParam(value = "filter to be applied on record", required = false)
                                                      @RequestParam(name = "filtro") String strFiltro,
                                                      @RequestParam(name = "numPagina") Integer numPagina) {
        List<Acao> result;

        //System.out.println("numPagina" + numPagina);
        this.logger.info("numPagina" + numPagina);

        if (strFiltro.trim().length() == 0) {
            result = this.acaoService.queryListarDesc();
        } else {
            /*result = this.acaoService.listarOrContains(strFiltro);

            if (result == null || result.size() == 0) {
                result = this.acaoService.listarOrJaro(strFiltro);
            } // if result*/

            result = this.acaoService.listarAndContains(strFiltro);

            if (result == null || result.size() == 0) {
                result = this.acaoService.listarAndJaro(strFiltro);
            } // if result
        } // else strFiltro

        int paginaAtual = numPagina;
        int itensPorPagina = 9; // TODO: grab from database?
        int qtdPaginas = 1;

        if (result.size() > itensPorPagina) {
            qtdPaginas = result.size() / itensPorPagina;
        } // itensPorPagina

        List<Acao> result2 = Pager.getPage(result, paginaAtual, itensPorPagina);

        Map<String, Object> retorno = new HashMap<>();

        retorno.put("listaAcao", result2);
        retorno.put("qtdPaginas", qtdPaginas);

        return ResponseEntity.ok(retorno);
    } // listar

    @GetMapping("/buscar")
    public ResponseEntity<Acao> buscar(@RequestParam(name = "codAcao") Integer codAcao) {
        Acao result = new Acao();

        result = this.acaoService.buscar(codAcao);

        return ResponseEntity.ok(result);
    } // buscar

    @GetMapping("/listar-clientes")
    public ResponseEntity<List<String>> listarClientes(@RequestParam(name = "nomeCliente") String nomeCliente) {
        List<String> result = new ArrayList<String>();

        result = this.acaoService.listarClientes(nomeCliente);

        return ResponseEntity.ok(result);
    } // listar-clientes
} // AcaoResource
