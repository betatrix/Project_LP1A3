package com.fiosequeries.service;

import com.fiosequeries.Model.Adicional;
import com.fiosequeries.Model.Peca;
import com.fiosequeries.repository.AdicionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdicionalService {

    @Autowired
    private AdicionalRepository adicionalRepository;

    private final EntityManager entityManager;

    public AdicionalService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void excluirAdicional(Long adicionalId) {
        Optional<Adicional> adicionalOptional = adicionalRepository.findById(adicionalId);
        if (((Optional<?>) adicionalOptional).isPresent()) {
            Adicional adicional = adicionalOptional.get();
            adicionalRepository.delete(adicional);
        } else {
            throw new IllegalArgumentException("Adicional não encontrado com o ID: " + adicionalId);
        }
    }

    public void atualizarAdicional(Adicional adicional) {
        adicionalRepository.save(adicional);
    }

    @Transactional
    public void adicionarAdicional(Adicional adicional) {
        adicionalRepository.save(adicional);
    }

    // Listar adicionais
    public List<Adicional> listarAdicionais(){
        List<Adicional> adicionaisCadastrados = new ArrayList<>();
        adicionaisCadastrados = adicionalRepository.findAll();
        return adicionaisCadastrados;
    }

    // Retornar nome para os combobox
    public List<String> RetornaNomeAdicional(){
        List<Adicional> adicionais = listarAdicionais();
        List<String> nomesAdicionais = new ArrayList<>();

        for(Adicional adicional : adicionais){
            nomesAdicionais.add(adicional.getNome());
        }
        return nomesAdicionais;
    }

    public Adicional buscarAdicionalPorNome(String nome) {
        String queryString = "SELECT p FROM Adicional p WHERE p.nome = :nome";
        TypedQuery<Adicional> query = entityManager.createQuery(queryString, Adicional.class);
        query.setParameter("nome", nome);
        return query.getSingleResult();
    }

}