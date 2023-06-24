package com.fiosequeries.service;

import com.fiosequeries.Model.Modelo;
import com.fiosequeries.Model.Tamanho;
import com.fiosequeries.repository.TamanhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class TamanhoService {
    @Autowired
    private TamanhoRepository tamanhoRepository;

    private final EntityManager entityManager;

    @Autowired
    public TamanhoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Tamanho buscarTamanhoPorNome(String nome) {
        String queryString = "SELECT t FROM Tamanho t WHERE t.nome = :nome";
        TypedQuery<Tamanho> query = entityManager.createQuery(queryString, Tamanho.class);
        query.setParameter("nome", nome);
        return query.getSingleResult();
    }

    // Listar modelos
    public List<Tamanho> listarTamanhos(){
        List<Tamanho> tamanhosCadastrados = new ArrayList<>();
        tamanhosCadastrados = tamanhoRepository.findAll();
        return tamanhosCadastrados;
    }

    public List<String> RetornaNomeTamanho() {
        List<Tamanho> tamanhos = listarTamanhos();
        List<String> nomesTamanhos = new ArrayList<>();

        for (Tamanho tamanho : tamanhos) {
            nomesTamanhos.add(tamanho.getNome());
        }
        return nomesTamanhos;
    }
}