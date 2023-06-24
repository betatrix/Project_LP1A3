package com.fiosequeries.service;

import com.fiosequeries.Model.Peca;
import com.fiosequeries.Model.Tecido;
import com.fiosequeries.repository.TecidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TecidoService {
    @Autowired
    private TecidoRepository tecidoRepository;

    private final EntityManager entityManager;

    public TecidoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void excluirTecido(Long tecidoId) {
        Optional<Tecido> tecidoOptional = tecidoRepository.findById(tecidoId);
        if (((Optional<?>) tecidoOptional).isPresent()) {
            Tecido tecido = tecidoOptional.get();
            tecidoRepository.delete(tecido);
        } else {
            throw new IllegalArgumentException("Adicional não encontrado com o ID: " + tecidoId);
        }
    }

    public void atualizarTecido(Tecido tecido) {
        tecidoRepository.save(tecido);
    }

    @Transactional
    public void adicionarTecido(Tecido tecido) {
        tecidoRepository.save(tecido);
    }

    public Tecido buscarTecidoPorNome(String nome) {
        return tecidoRepository.findByNome(nome);
    }


    // Listar tecidos
    public List<Tecido> listarTecidos(){
        List<Tecido> tecidosCadastrados = new ArrayList<>();
        tecidosCadastrados = tecidoRepository.findAll();
        return tecidosCadastrados;
    }
    // Retornar nome para os combobox
    public List<String> RetornaNomeTecido(){
        List<Tecido> tecidos = listarTecidos();
        List<String> nomesTecidos = new ArrayList<>();

        for(Tecido tecido : tecidos){
            nomesTecidos.add(tecido.getNome());
        }
        return nomesTecidos;
    }

//    public Tecido buscarTecidoPorNome(String nome) {
//        String queryString = "SELECT p FROM Tecido p WHERE p.nome = :nome";
//        TypedQuery<Tecido> query = entityManager.createQuery(queryString, Tecido.class);
//        query.setParameter("nome", nome);
//        return query.getSingleResult();
//    }


}