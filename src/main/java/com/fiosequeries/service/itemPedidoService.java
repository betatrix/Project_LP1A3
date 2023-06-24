package com.fiosequeries.service;

import com.fiosequeries.Model.ItemPedido;
import com.fiosequeries.Model.Modelo;
import com.fiosequeries.Model.Orcamento;
import com.fiosequeries.Model.Peca;
import com.fiosequeries.repository.itemPedidoRepository;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class itemPedidoService {
    @Autowired
    private itemPedidoRepository itemPedidoRepository;

    public List<ItemPedido> listarItens() {
        List<ItemPedido> itens = new ArrayList<>();
        itens = itemPedidoRepository.findAll();
        return itens;
    }

    public List<ItemPedido> buscarItensDoOrcamento(Long idOrc) {
        List<ItemPedido> itens = listarItens();
        List<Orcamento> itensDoOrcamento = new ArrayList<>();
        List<Orcamento> orcamentos = new ArrayList<>();
        for (Orcamento orcamento : orcamentos) {
            if (orcamento.getId().equals(idOrc)) {
                itensDoOrcamento.add(orcamento);
                break;
            }
        }
        return itens;
    }

    public void salvarItemPedido(ItemPedido itemPedido){
        itemPedidoRepository.save(itemPedido);
    }

}