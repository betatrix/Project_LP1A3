package com.fiosequeries.repository;

import com.fiosequeries.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByNome(String nome);
    List<Cliente> findByNomeContaining(String nome);
}