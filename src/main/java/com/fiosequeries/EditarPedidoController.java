package com.fiosequeries;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import com.fiosequeries.Model.Pedido;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarPedidoController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label lblEditarPedido;

    @FXML
    private Label lb_idPed;

    @FXML
    private Label lblItens;

    @FXML
    private Label lb_valorTotal;

    @FXML
    private Label lblObservacoes;

    @FXML
    private Label lblTipoPagamento;

    @FXML
    private Label lblSituacao;

    @FXML
    private Label lblDataEntrega;

    @FXML
    private ComboBox<String> cbox_situacao;

    @FXML
    private ComboBox<String> cbox_pago;

    @FXML
    private ComboBox<String> cbox_tipoPag;

    @FXML
    private ComboBox<String> cbox_itensPedido;


    @FXML
    private TextField txt_dataEntrega;

    @FXML
    private TextField txt_dataPag;

    @FXML
    private TextField txt_obs;

    @FXML
    private Label lbIdPed;

    @FXML
    private Label lbValorTotal;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnDelItem;

    private Long pedidoId;

    private Pedido pedidoSelecionado;

    public void setPedidoSelecionado(Pedido pedido) {
        this.pedidoSelecionado = pedido;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicialize os ComboBox com as opções padrão
        cbox_situacao.getItems().addAll("CRIADO", "PRODUCAO", "PAGO", "ENTREGUE", "AJUSTE", "FINALIZADO");
        cbox_pago.getItems().addAll("Sim", "Não");
        cbox_tipoPag.getItems().addAll("DEBITO", "CREDITO", "DINHEIRO", "PIX");
//        cbox_itensPedido.getItems().addAll("Item 1", "Item 2", "Item 3");

    }


    // Adicione métodos para manipular eventos dos botões e outros componentes, se necessário.
}