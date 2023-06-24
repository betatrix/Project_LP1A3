package com.fiosequeries.service;

import com.fiosequeries.Model.Cliente;
import com.fiosequeries.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes(){
        List<Cliente> clientesCadastrados;
        clientesCadastrados = clienteRepository.findAll();
        return clientesCadastrados;
    }

    public Cliente buscarClientePorNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    public List<Cliente> buscarClientesPorNome(String nome) {
        return clienteRepository.findByNomeContaining(nome);
    }

    public void excluirCliente(Cliente cliente) {
        cliente.setNome("Desconhecido");
        cliente.setEmail(null);
        cliente.setTelefone(null);

        clienteRepository.save(cliente);
    }

    public void salvarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public List<String> RetornaNomeCliente(){
        List<Cliente> clientes = listarClientes();
        List<String> nomesClientes = new ArrayList<>();

        for(Cliente cliente : clientes){
            nomesClientes.add(cliente.getNome());
        }
        return nomesClientes;
    }

}