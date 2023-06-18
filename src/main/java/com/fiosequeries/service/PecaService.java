package com.fiosequeries.service;

import com.fiosequeries.Model.Peca;
import com.fiosequeries.repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PecaService {
    @Autowired
    private PecaRepository pecaRepository;

    // Método usado para mostrar os nomes das peças no combobox

    public List<String> RetornaNomePeca(){
        List<Peca> pecas = pecaRepository.findAll();
        List<String> nomesPecas = new ArrayList<>();

        for(Peca peca : pecas){
            nomesPecas.add(peca.getNome());
        }
        return nomesPecas;
    }




}

