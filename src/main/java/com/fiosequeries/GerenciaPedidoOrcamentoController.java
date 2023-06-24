package com.fiosequeries;

import com.fiosequeries.Model.ItemPedido;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class GerenciaPedidoOrcamentoController implements Initializable {


    @FXML
    private Button bnt_voltaHome;

    @FXML
    private Button bnt_cadastraPedOrc;

    @FXML
    private Button btn_listaOrc;

    @FXML
    private Button btn_editaOrc;

    @FXML
    private Button btn_excluiOrc;

    @FXML
    private Button btn_listaPed;

    @FXML
    private Button btn_editaPed;

    @FXML
    private Button btn_excluiPed;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private final StageInitializer stageInitializer;

    public GerenciaPedidoOrcamentoController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    private List<ItemPedido> itensPedido;
    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }


    @FXML
    private void goToPedidos() {
        stageInitializer.changeScene("/ListarPedidosScreen.fxml");
    }

    @FXML
    private void goToOrcamentos() {
        stageInitializer.changeScene("/ListarOrcamentosScreen.fxml");
    }

    @FXML
    private void goToFuncionarioView() {
        stageInitializer.changeScene("/FuncionarioView.fxml");
    }


    //    @FXML
//    private void goToPedidos() {
//        stageInitializer.changeScene("/EditarPedidoScreen.fxml");
//    }
//
//    @FXML
//    private void goToExcluirPedidos() {
//        stageInitializer.changeScene("/ExcluirPedidoScreen.fxml");
//    }
//
    @FXML
    private void goToCadastrarPedOrc() {
        //stageInitializer.setItensPedido(itensPedido); // Passa a lista de itens para o StageInitializer
        stageInitializer.changeScene("/AdicionarItem.fxml");
    }
//
//


}