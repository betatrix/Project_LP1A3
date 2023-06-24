package com.fiosequeries.Model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "itemPedido")
public class ItemPedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //verificar
    @ManyToOne
    @JoinColumn(name = "peca_id")
    private Peca peca;

    @ManyToOne
    @JoinColumn(name = "tamanho_id")
    private Tamanho tamanho;

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "tecido_id")
    private Tecido tecido;

    @ManyToOne
    @JoinColumn(name = "cor_id")
    private Cor cor;

//    @OneToMany(mappedBy = "itemPedido")
//    private List<Adicional> adicionais = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "ItemPedido_Adicional",
            joinColumns = @JoinColumn(name = "itemPedido_id"),
            inverseJoinColumns = @JoinColumn(name = "adicional_id"))
    @Fetch(FetchMode.JOIN)
    private List<Adicional> adicionais = new ArrayList<>();


    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "orcamento_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Orcamento orcamento;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Pedido pedido;

    @Column(name = "valorItem")
    private Double valorItem;

    public ItemPedido() {

    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Tecido getTecido() {
        return tecido;
    }

    public void setTecido(Tecido tecido) {
        this.tecido = tecido;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public List<Adicional> getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(List<Adicional> adicionais) {
        this.adicionais = adicionais;
    }

//    public Double getValorItem() {
//        return valorItem;
//    }

    public void setValorItem(Double valorItem) {
        this.valorItem = valorItem;
    }

    // Calculo do valor total
    public Double getValorItem() {
        calcularValorItem();
        return valorItem;
    }

    private void calcularValorItem() {
        if (peca != null && modelo != null && tamanho != null && tecido != null) {
            valorItem = peca.getPrecoBase()
                    + tecido.getPreco()
                    + (peca.getPrecoBase() * modelo.getMultiplicador())
                    + (peca.getPrecoBase() * tamanho.getMultiplicador());

            for (Adicional adicional : adicionais) {
                valorItem += peca.getPrecoBase() * adicional.getMultiplicador();
            }
        } else {
            valorItem = 0.0; // ou outro valor padrão, se necessário
        }
    }


}