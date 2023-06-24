package com.fiosequeries.service;

import com.fiosequeries.Model.Cor;
import com.fiosequeries.Model.Peca;
import com.fiosequeries.repository.CorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class CorService {
    @Autowired
    private CorRepository corRepository;

    private final EntityManager entityManager;

    public CorService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Cor buscarCorPorNome(String nome) {
        String queryString = "SELECT p FROM Cor p WHERE p.nome = :nome";
        TypedQuery<Cor> query = entityManager.createQuery(queryString, Cor.class);
        query.setParameter("nome", nome);
        return query.getSingleResult();
    }


    // Listar modelos
    public List<Cor> listarCores(){
        List<Cor> coresCadastradas = new ArrayList<>();
        coresCadastradas = corRepository.findAll();
        return coresCadastradas;
    }

    public List<String> RetornaNomeCor(){
        List<Cor> cores = listarCores();
        List<String> nomesCores = new ArrayList<>();

        for(Cor cor : cores){
            nomesCores.add(cor.getNome());
        }
        return nomesCores;
    }
}