package com.fiosequeries.service;

import com.fiosequeries.Model.ItemPedido;
import com.fiosequeries.Model.Orcamento;
import com.fiosequeries.Model.Pedido;
import com.fiosequeries.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private itemPedidoService itemPedidoService;

    public List<Pedido> listarPedidos(){
        List<Pedido> pedidosCadastrados = new ArrayList<>();
        pedidosCadastrados = pedidoRepository.findAll();
        return pedidosCadastrados;
    }

    public Pedido buscarPedido(Long id){
        return pedidoRepository.findById(id).orElse(null);
    }

    public void salvarPedido(Pedido pedido){
        pedidoRepository.save(pedido);
    }

    public void atualizarPedido(Pedido pedido){
        pedidoRepository.save(pedido);
    }

    public void excluirPedido(Long id){
        pedidoRepository.deleteById(id);
    }

    public List<ItemPedido> findItensPedidoById(Long id) {
        List<ItemPedido> itensPedido = itemPedidoService.listarItens();
        List<ItemPedido> itensEncontrados = new ArrayList<>();

        for (ItemPedido itemPedido : itensPedido) {
            if (itemPedido.getPedido() != null && itemPedido.getPedido().getId().equals(id)) {
                itensEncontrados.add(itemPedido);
            }
        }
        return itensEncontrados;
    }

}