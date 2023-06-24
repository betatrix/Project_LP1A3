package com.fiosequeries;

import com.fiosequeries.Model.Cliente;
import com.fiosequeries.service.ClienteService;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class InserirClienteController implements Initializable {

    @Autowired
    private ClienteService clienteService;

    @FXML
    private TextField tf_nome;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_telefone;

    @FXML
    private ComboBox<String> cbox_busto;

    @FXML
    private ComboBox<String> cbox_cintura;

    @FXML
    private ComboBox<String> cbox_quadril;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private final StageInitializer stageInitializer;

    public InserirClienteController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }
    @FXML
    private void goToGerenciaCliente() {
        stageInitializer.changeScene("/GerenciaCliente.fxml");
    }

    @FXML
    private void SalvarCliente() {
        String novoNome = tf_nome.getText();
        String novoEmail = tf_email.getText();
        String novoTelefone = tf_telefone.getText();

        // Verifica se os campos estão preenchidos
        if (novoNome.isEmpty() || novoEmail.isEmpty() || novoTelefone.isEmpty()) {
            // Exibe uma mensagem de erro ou toma alguma ação apropriada
            System.out.println("Preencha todos os campos antes de salvar o cliente.");
            return;
        }

        Cliente novoCliente = new Cliente(novoNome, novoTelefone, novoEmail);

        // Chame o método salvarCliente() do serviço para salvar o novo cliente
        clienteService.salvarCliente(novoCliente);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Sucesso!");
        alerta.setHeaderText(null);
        alerta.setContentText("Cliente inserido com sucesso!");
        alerta.showAndWait();

        // Limpa os campos de texto
        limparCampos();
    }

    private void limparCampos() {
        tf_nome.clear();
        tf_email.clear();
        tf_telefone.clear();
    }

}
