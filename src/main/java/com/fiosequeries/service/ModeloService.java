package com.fiosequeries.service;

import com.fiosequeries.Model.Modelo;
import com.fiosequeries.Model.Peca;
import com.fiosequeries.Model.Tamanho;
import com.fiosequeries.repository.ModeloRepository;
import com.fiosequeries.repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private PecaRepository pecaRepository;

    private final EntityManager entityManager;

    public ModeloService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Modelo adicionarModelo(String nome, Double multiplicador) {
        Modelo modelo = new Modelo(nome, multiplicador);
        return modeloRepository.save(modelo);
    }

    public List<Modelo> listarModelos2() {
        List<Modelo> modelos =  modeloRepository.findAll();
        for (Modelo modelo : modelos) {
            modelo.getPecas().size(); // Buscar as medidas associadas a cada peça
        }
        return modelos;
    }

    public Modelo editarModelo(Long modeloId, Double novoMultiplicador) {
        Optional<Modelo> optionalModelo = modeloRepository.findById(modeloId);
        if (optionalModelo.isPresent()) {
            Modelo modelo = optionalModelo.get();
            modelo.setMultiplicador(novoMultiplicador); //só altera o multiplicador

            // Mantém o nome atual do modelo
            String nomeAtual = modelo.getNome();
            modelo.setNome(nomeAtual);

            return modeloRepository.save(modelo);
        }
        throw new IllegalArgumentException("Modelo com ID " + modeloId + " não encontrado");
    }


    public void removerPecaDoModelo(Long modeloId, Long pecaId) {
        Optional<Modelo> optionalModelo = modeloRepository.findById(modeloId);
        Optional<Peca> optionalPeca = pecaRepository.findById(pecaId);

        if (optionalModelo.isPresent() && optionalPeca.isPresent()) {
            Modelo modelo = optionalModelo.get();
            Peca peca = optionalPeca.get();

            modelo.getPecas().remove(peca);
            modeloRepository.save(modelo);
        } else {
            throw new IllegalArgumentException("Modelo com ID " + modeloId + " ou peça com ID " + pecaId + " não encontrados");
        }
    }

    public Modelo buscarModeloPorNome(String nome) {
        String queryString = "SELECT t FROM Modelo t WHERE t.nome = :nome";
        TypedQuery<Modelo> query = entityManager.createQuery(queryString, Modelo.class);
        query.setParameter("nome", nome);
        return query.getSingleResult();
    }
    // Listar modelos
    public List<Modelo> listarModelos(){
        List<Modelo> modelosCadastrados = new ArrayList<>();
        modelosCadastrados = modeloRepository.findAll();
        return modelosCadastrados;
    }

    public List<String> RetornaNomeModelo(){
        List<Modelo> modelos = listarModelos();
        List<String> nomesModelos = new ArrayList<>();

        for(Modelo modelo : modelos){
            nomesModelos.add(modelo.getNome());
        }
        return nomesModelos;
    }

    @Transactional
    public List<String> RetornaNomeModeloPorPeca(String nomePeca) {
        List<Modelo> modelos = listarModelos();
        List<String> nomesModelos = new ArrayList<>();

        for (Modelo modelo : modelos) {
            List<Peca> pecas = modelo.getPecas();
            for (Peca peca : pecas) {
                if (peca.getNome().equals(nomePeca)) {
                    nomesModelos.add(modelo.getNome());
                    break;
                }
            }
        }

        return nomesModelos;
    }



}