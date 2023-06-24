package com.fiosequeries.service;

import com.fiosequeries.Model.ItemPedido;
import com.fiosequeries.Model.Orcamento;
import com.fiosequeries.repository.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrcamentoService {
    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private itemPedidoService itemPedidoService;


    // Listar orçamentos
    public List<Orcamento> listarOrcamentos(){
        List<Orcamento> orcamentosCadastrados = new ArrayList<>();
        orcamentosCadastrados = orcamentoRepository.findAll();
        return orcamentosCadastrados;
    }

    // Buscar orçamento pelo Id
    public Orcamento buscarOrcamento(Long id){
        Orcamento orcamento = new Orcamento();
        orcamento = orcamentoRepository.findById(id).orElse(null);
        return orcamento;
    }

    public void salvarOrcamento(Orcamento orcamento){
        orcamentoRepository.save(orcamento);
    }

    public void atualizarOrcamento(Orcamento orcamento){
        orcamentoRepository.save(orcamento);
    }

    public void excluirOrcamento(Long id){
        orcamentoRepository.deleteById(id);
    }


    public List<ItemPedido> findItensOrcamentoById(Long id) {
        List<ItemPedido> itensPedido = itemPedidoService.listarItens();
        List<ItemPedido> itensEncontrados = new ArrayList<>();

        for (ItemPedido itemPedido : itensPedido) {
            if (itemPedido.getOrcamento() != null && itemPedido.getOrcamento().getId().equals(id)) {
                itensEncontrados.add(itemPedido);
            }
        }
        return itensEncontrados;
    }




}