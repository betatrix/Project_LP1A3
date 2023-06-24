package com.fiosequeries;

import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class FinalizarPedidoController implements Initializable {
    @FXML
    private Label titleLabel;

    @FXML
    private TextField dataEntregaTextField;

    @FXML
    private TextField dataPagamentoTextField;

    @FXML
    private ComboBox<String> tipoPagamentoComboBox;

    @FXML
    private ComboBox<String> situacaoPagamentoComboBox;

    @FXML
    private ComboBox<String> situacaoPedidoComboBox;

    @FXML
    private Button concluirPedidoButton;

    @FXML
    private Button cancelarButton;

    private final StageInitializer stageInitializer;

    public FinalizarPedidoController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    @FXML
    private void concluirPedidoButtonAction() {
        //salva
        stageInitializer.changeScene("/GerenciaPedidoOrcamento.fxml");
    }

    @FXML
    private void cancelarButtonAction() {
        stageInitializer.changeScene("/GerenciaPedidoOrcamento.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}