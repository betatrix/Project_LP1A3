package com.fiosequeries;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class FuncionarioViewController implements Initializable {
    @FXML
    private Button btn_clientes;

    @FXML
    private Button btn_pedidos;

    @FXML
    private Button btn_outros;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    private final StageInitializer stageInitializer;

    public FuncionarioViewController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    @FXML
    private void goToPedidosOrcamentos() {
        stageInitializer.changeScene("/GerenciaPedidoOrcamento.fxml");
    }

    @FXML
    private void goToClientes() {
        stageInitializer.changeScene("/GerenciaCliente.fxml");
    }

    @FXML
    private void goToOutros() {
        stageInitializer.changeScene("/OutrosScreen.fxml");
    }

    @FXML
    private void goToLogin() {
        stageInitializer.changeScene("/MainScreen.fxml");
    }


}
