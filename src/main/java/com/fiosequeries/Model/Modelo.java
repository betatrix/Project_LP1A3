package com.fiosequeries.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Modelo")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    // verificar
    @OneToMany(mappedBy = "modelo")
    private List<Peca> pecas = new ArrayList<>();

    @Column(name = "multiplicador")
    private Double multiplicador;

    @OneToMany(mappedBy = "modelo")
    private List<ItemPedido> itensPedido = new ArrayList<>();


    public Modelo(String nome,Double multiplicador) {
        this.nome = nome;
        this.multiplicador = multiplicador;
    }

    public Modelo(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Peca> getPecas() {
        return pecas;
    }

    public void setPecas(List<Peca> pecas) {
        this.pecas = pecas;
    }

    public Double getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(Double multiplicador) {
        this.multiplicador = multiplicador;
    }
}