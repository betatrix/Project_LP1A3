package com.fiosequeries;

import com.fiosequeries.Model.ItemPedido;
import com.fiosequeries.Model.Orcamento;
import com.fiosequeries.Model.Pedido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import com.fiosequeries.service.PedidoService;
import javafx.beans.property.SimpleObjectProperty;

@Component
public class ListarPedidosController implements Initializable {

    private final StageInitializer stageInitializer;

    public ListarPedidosController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    @Autowired
    private PedidoService pedidoService;

    @FXML
    private TableView<Pedido> tabelaPedidos;

    @FXML
    private TableColumn<Pedido, Long> colunaId;

    @FXML
    private TableColumn<Pedido, LocalDate> colunaDataEntrega;

    @FXML
    private TableColumn<Pedido, Boolean> colunaPago;

    @FXML
    private TableColumn<Pedido, LocalDate> colunaDataPagamento;

    @FXML
    private TableColumn<Pedido, String> colunaTipoPagamento;

    @FXML
    private TableColumn<Pedido, String> colunaSituacao;

    @FXML
    private TableColumn<Pedido, String> colunaUsuario;

    @FXML
    private TableColumn<Pedido, String>  colunaCliente;

    //@FXML
//    private TableColumn<Pedido, List<ItemPedido>> colunaItens;

    @FXML
    private TableColumn<Pedido, List<ItemPedido>> colunaItens;


    @FXML
    private TableColumn<Pedido, String> colunaData;

    @FXML
    private TableColumn<Pedido, Double> colunaValor;

    @FXML
    private TableColumn<Pedido, String> colunaObs;

    @FXML
    private Button btn_editar;

    @FXML
    private Button btn_excluir;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        carregarPedidos();

        tabelaPedidos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isPedidoSelecionado = newSelection != null;
            boolean isPedidoFinalizado = isPedidoSelecionado && newSelection.getSituacao().equals("Finalizado");
            btn_editar.setDisable(!isPedidoSelecionado || isPedidoFinalizado);
            btn_excluir.setDisable(!isPedidoSelecionado);
        });
    }

    @FXML
    private void goToGerenciaPedidoOrcamento() {
        stageInitializer.changeScene("/GerenciaPedidoOrcamento.fxml");
    }


    private void configurarColunas() {
        colunaId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colunaDataEntrega.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataEntrega()));
        colunaPago.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isPago()));
        colunaDataPagamento.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataPagamento()));
        colunaTipoPagamento.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTipoPagamento()).asString());
        colunaSituacao.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSituacao()).asString());


        colunaData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataCriacao()).asString());
        colunaCliente.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCliente().getNome()));
        colunaUsuario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUsuario().getNomeUsuario()));
        colunaObs.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getObservacoes()));
        colunaValor.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValorTotal()));
//        colunaItens.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItensPedido()));

        colunaItens.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(List<ItemPedido> itens, boolean empty) {
                super.updateItem(itens, empty);
                if (empty || itens == null || itens.isEmpty()) {
                    setText(null);
                } else {
                    setText(getFormattedItens(itens));
                }
            }
        });

        colunaItens.setCellValueFactory(cellData -> new SimpleObjectProperty<>(pedidoService.findItensPedidoById(cellData.getValue().getId())));

    }


    private String getFormattedItens(List<ItemPedido> itensPedido) {
        StringBuilder formattedItens = new StringBuilder();
        for (ItemPedido item : itensPedido) {
            formattedItens.append(item.getPeca().getNome())
                    .append(" - ")
                    .append(item.getModelo().getNome())
                    .append(" - ")
                    .append(item.getValorItem())
                    .append("  ");
        }
        return formattedItens.toString();
    }


    private void carregarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        if (!pedidos.isEmpty()) {
            tabelaPedidos.getItems().addAll(pedidos);
        }
    }

    @FXML
    private void excluirPedido() {
        Pedido pedidoSelecionado = tabelaPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Excluir Pedido");
            alert.setContentText("Tem certeza de que deseja excluir o pedido selecionado?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                pedidoService.excluirPedido(pedidoSelecionado.getId());
                tabelaPedidos.getItems().remove(pedidoSelecionado);
            }
        }
    }



    @FXML
    private void goToEditarPedido() {
        stageInitializer.changeScene("/EditarPedidoScreen.fxml");


//        Pedido pedidoSelecionado = tabelaPedidos.getSelectionModel().getSelectedItem();
//        if(pedidoSelecionado != null) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditarPedidoScreen.fxml"));
//            Parent root;
//            try {
//                root = loader.load();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            EditarPedidoController editarPedidoController = loader.getController();
//            editarPedidoController.setPedidoSelecionado(pedidoSelecionado);
//
//            // Use essa linha se você estiver usando a abordagem do StageInitializer
//            stageInitializer.changeScene("/EditarPedidoScreen.fxml");
//
//        }
    }

}