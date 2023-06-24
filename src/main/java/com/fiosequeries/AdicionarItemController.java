package com.fiosequeries;

import com.fiosequeries.Model.*;
import com.fiosequeries.service.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AdicionarItemController implements Initializable {

    @Autowired
    private PecaService pecaService;
    @Autowired
    private ModeloService modeloService;
    @Autowired
    private TamanhoService tamanhoService;
    @Autowired
    private TecidoService tecidoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private CorService corService;
    @Autowired
    private AdicionalService adicionalService;

    StageInitializer stageInitializer;
    public AdicionarItemController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    @FXML
    private ComboBox<String> cbox_Cliente;

    @FXML
    private ComboBox<String> cbox_Peca;

    @FXML
    private ComboBox<String> cbox_Tamanho;

    @FXML
    private ComboBox<String> cbox_Modelo;

    @FXML
    private ComboBox<String> cbox_Cor;

    @FXML
    private ComboBox<String> cbox_Tecido;

    @FXML
    private ComboBox<String> cbox_Add1;

    @FXML
    private ComboBox<String> cbox_Add2;

    @FXML
    private TextField lb_idFunc;


    private Orcamento orcamento;
    private ItemPedido itemPedido;

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    @FXML
    private void goToFazerOrcamento() {
        stageInitializer.changeScene("/FazerOrcamento.fxml");
    }

    @FXML
    private void goToGerenciaPedidoOrcamento() {
        stageInitializer.changeScene("/GerenciaPedidoOrcamento.fxml");
    }

    @FXML
    private void adicionarItem() {
//        String pecaNome = cbox_Peca.getValue();
//
//        // Encontrar o objeto Peca correspondente com base no nome selecionado
//        Peca pecaSelecionada = null;
//        for (Peca peca : pecas) {
//            if (peca.getNome().equals(pecaNome)) {
//                pecaSelecionada = peca;
//                break;
//            }
//        }

//        // Verificar se a peça foi encontrada
//        if (pecaSelecionada == null) {
//            // Peça não encontrada, lógica de tratamento apropriada aqui
//            return;
//        }


        // Obter os nomes selecionados nos ComboBoxes
        String pecaNome = cbox_Peca.getValue();
        String tamanhoNome = cbox_Tamanho.getValue();
        String modeloNome = cbox_Modelo.getValue();
        String corNome = cbox_Cor.getValue();
        String tecidoNome = cbox_Tecido.getValue();

        // Obter os objetos correspondentes com base nos nomes selecionados
        Peca peca = (Peca) pecaService.buscarPecaPorNome(pecaNome);
        Tamanho tamanho = (Tamanho) tamanhoService.buscarTamanhoPorNome(tamanhoNome);
        Modelo modelo = (Modelo) modeloService.buscarModeloPorNome(modeloNome);
        Cor cor = (Cor) corService.buscarCorPorNome(corNome);
        Tecido tecido = (Tecido) tecidoService.buscarTecidoPorNome(tecidoNome);

        // Obter os adicionais selecionados nos ComboBoxes
        List<String> adicionaisSelecionados = new ArrayList<>();
        adicionaisSelecionados.add(cbox_Add1.getValue());
        adicionaisSelecionados.add(cbox_Add2.getValue());

        // Buscar os objetos dos adicionais com base nos nomes selecionados
        List<Adicional> adicionais = new ArrayList<>();
        for (String adicionalNome : adicionaisSelecionados) {
            Adicional adicional = (Adicional) adicionalService.buscarAdicionalPorNome(adicionalNome);
            if (adicional != null) {
                adicionais.add(adicional);
            }
        }

        // Criar um novo ItemPedido
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPeca(peca);
        itemPedido.setTamanho(tamanho);
        itemPedido.setModelo(modelo);
        itemPedido.setTecido(tecido);
        itemPedido.setCor(cor);
        itemPedido.setAdicionais(adicionais);

        // Calcular o valor do item
        Double valorItem = itemPedido.getValorItem();
        itemPedido.setValorItem(valorItem);

        // Exibir uma mensagem de sucesso
        exibirMensagem("Item adicionado com sucesso!");

    }

    private void exibirMensagem(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        // Preencher combobox com nomes de modelos
        List<String> clientes = clienteService.RetornaNomeCliente();
        if (!clientes.isEmpty()) {
            cbox_Cliente.getItems().addAll(clientes);
        }

//            // Preencher combobox com nomes de peças
//            List<String> pecas = pecaService.RetornaNomePeca();
//            if (!pecas.isEmpty()) {
//                cbox_Peca.getItems().addAll(pecas);
//            }

        List<Peca> pecas = pecaService.listarPecas();
        if (!pecas.isEmpty()) {
            List<String> nomesPecas = new ArrayList<>();
            for (Peca peca : pecas) {
                nomesPecas.add(peca.getNome());
            }
            cbox_Peca.setConverter(new StringConverter<String>() {
                @Override
                public String toString(String nomePeca) {
                    return nomePeca;
                }

                @Override
                public String fromString(String string) {
                    return string;
                }
            });

            cbox_Peca.getItems().addAll(nomesPecas);
        }



        // Preencher combobox com nomes de modelos
        List<String> cores = corService.RetornaNomeCor();
        if (!cores.isEmpty()) {
            cbox_Cor.getItems().addAll(cores);
        }

        // Preencher combobox com nomes de modelos
        List<String> tecidos = tecidoService.RetornaNomeTecido();
        if (!tecidos.isEmpty()) {
            cbox_Tecido.getItems().addAll(tecidos);
        }

        // Preencher combobox com nomes de modelos
        List<String> tamanhos = tamanhoService.RetornaNomeTamanho();
        if (!tamanhos.isEmpty()) {
            cbox_Tamanho.getItems().addAll(tamanhos);
        }

        // COMBOX DE ADICIONAIS
        List<String> adicionais = adicionalService.RetornaNomeAdicional();
        if(!adicionais.isEmpty()){
            cbox_Add1.getItems().addAll(adicionais);
            cbox_Add2.getItems().addAll(adicionais);
        }


        // Listener para o combobox de peças
        cbox_Peca.setOnAction(event -> {
            String pecaSelecionada = cbox_Peca.getValue();
            if (pecaSelecionada != null) {
                List<String> nomesModelos = modeloService.RetornaNomeModeloPorPeca(pecaSelecionada);
                if (!nomesModelos.isEmpty()) {
                    if (cbox_Modelo != null) {
                        cbox_Modelo.getItems().clear();
                        cbox_Modelo.getItems().addAll(nomesModelos);
                    }
                }
            }
        });





        // ...
        // Adicione aqui as chamadas para preencher os outros comboboxes, se necessário
    }


}
