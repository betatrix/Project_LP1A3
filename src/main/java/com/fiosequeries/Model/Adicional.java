package com.fiosequeries.Model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Adicional")

public class Adicional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // nome da coluna no banco de dados
    private Long id;

    @Column(name = "nome") // nome da coluna no banco de dados
    private String nome;

    @Column(name = "multiplicador") // nome da coluna no banco de dados
    private Double multiplicador;

//    @ManyToOne
//    @JoinColumn(name = "itemPedido_id")
//    private ItemPedido itemPedido;

    @ManyToMany(mappedBy = "adicionais")
    private List<ItemPedido> itemPedidos = new ArrayList<>();


    public Adicional(String nome, Double multiplicador) {
        this.id = id;
        this.nome = nome;
        this.multiplicador = multiplicador;
    }

    public Adicional() {
    }

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

    public Double getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(Double multiplicador) {
        this.multiplicador = multiplicador;
    }
}