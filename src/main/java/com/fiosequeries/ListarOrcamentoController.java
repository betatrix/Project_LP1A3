package com.fiosequeries;

import com.fiosequeries.Model.ItemPedido;
import com.fiosequeries.Model.Orcamento;
import com.fiosequeries.service.OrcamentoService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ListarOrcamentoController implements Initializable {

    @Autowired
    private OrcamentoService orcamentoService;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField idPesquisa;

    @FXML
    private Button idBtnPesquisa;

    @FXML
    private TableView<Orcamento> orcamentoTableView;

    @FXML
    private TableColumn<Orcamento, Long> colunaId;

    @FXML
    private TableColumn<Orcamento, String> colunaUsuario;

    @FXML
    private TableColumn<Orcamento, String>  colunaCliente;

    @FXML
    private TableColumn<Orcamento, List<ItemPedido>> colunaItens;

    @FXML
    private TableColumn<Orcamento, String> colunaData;

    @FXML
    private TableColumn<Orcamento, Double> colunaTotal;

    @FXML
    private TableColumn<Orcamento, String> colunaObs;

    @FXML
    private Button idBtnVoltar;

    @FXML
    private Button btn_editar;

    @FXML
    private Button btn_excluir;

    @FXML
    private void pesquisarButtonAction() {
        // Implemente aqui a lógica quando o botão "Pesquisar" for clicado.
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        carregarOrcamentos();

        orcamentoTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isOrcamentoSelecionado = newSelection != null;
//            btn_editar.setDisable(!isPedidoSelecionado || isPedidoFinalizado);
            btn_excluir.setDisable(!isOrcamentoSelecionado);
        });
        }

    private final StageInitializer stageInitializer;

    public ListarOrcamentoController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;

//        colunaItens.setCellFactory(new Callback<TableColumn<Orcamento, List<ItemPedido>>, TableCell<Orcamento, List<ItemPedido>>>() {
//            @Override
//            public TableCell<Orcamento, List<ItemPedido>> call(TableColumn<Orcamento, List<ItemPedido>> param) {
//
//                return new TableCell<Orcamento, List<ItemPedido>>() {
//                    @Override
//                    @Transactional
//                    protected void updateItem(List<ItemPedido> itens, boolean empty) {
//                        super.updateItem(itens, empty);
//                        if (empty || itens == null || itens.isEmpty()) {
//                            setText(null);
//                        } else {
//                            StringBuilder sb = new StringBuilder();
//                            for (ItemPedido item : itens) {
//                                sb.append("Peça: ").append(item.getPeca())
//                                        .append(", Modelo: ").append(item.getModelo())
//                                        .append(", Valor Total: ").append(item.getValorItem())
//                                        .append("\n");
//                            }
//                            setText(sb.toString());
//                        }
//                    }
//                };
//            }
//        });

    }

    private void configurarColunas() {
        colunaId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colunaData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataCriacao()).asString());
        colunaCliente.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCliente().getNome()));
        colunaUsuario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUsuario().getNomeUsuario()));
        colunaObs.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getObservacoes()));
        colunaTotal.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValorTotal()));
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

        colunaItens.setCellValueFactory(cellData -> new SimpleObjectProperty<>(orcamentoService.findItensOrcamentoById(cellData.getValue().getId())));

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


    private void carregarOrcamentos() {
        List<Orcamento> orcamentos = orcamentoService.listarOrcamentos();
        if (!orcamentos.isEmpty()) {
            orcamentoTableView.getItems().addAll(orcamentos);
        }
    }

    @FXML
    private void excluirOrcamento() {
        Orcamento orcamentoSelecionado = orcamentoTableView.getSelectionModel().getSelectedItem();
        if (orcamentoSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Excluir Orçamento");
            alert.setContentText("Tem certeza de que deseja excluir o orçamento selecionado?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                orcamentoService.excluirOrcamento(orcamentoSelecionado.getId());
                orcamentoTableView.getItems().remove(orcamentoSelecionado);
            }
        }
    }

    @FXML
    private void goToGerenciaPedidoOrcamento() {
        stageInitializer.changeScene("/GerenciaPedidoOrcamento.fxml");
    }
}