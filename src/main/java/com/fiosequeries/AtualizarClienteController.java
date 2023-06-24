package com.fiosequeries;

import com.fiosequeries.Model.Cliente;
import com.fiosequeries.service.ClienteService;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class AtualizarClienteController implements Initializable {

    @Autowired
    private ClienteService clienteService;

    @FXML
    private ComboBox<Cliente> cbox_clientes;

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
        configurarComboBoxClientes();
    }

    private final StageInitializer stageInitializer;

    public AtualizarClienteController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    private void configurarComboBoxClientes() {
        // Configura o ComboBox com a lista de clientes
        List<Cliente> clientes = clienteService.listarClientes();

        // Filtra os clientes com nome "Desconhecido" e não os mostra na combobox
        List<String> nomesClientes = clientes.stream()
                .map(Cliente::getNome)
                .filter(Objects::nonNull)
                .filter(nome -> !nome.equals("Desconhecido"))
                .collect(Collectors.toList());

        cbox_clientes.getItems().addAll(clientes);

        // Define o StringConverter para exibir apenas o nome do cliente no ComboBox
        cbox_clientes.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente != null ? cliente.getNome() : "";
            }
            @Override //caso precise transformar para objeto novamente
            public Cliente fromString(String s) {
                return null;
            }
        });

        // Define um listener para o evento de seleção no ComboBox
        cbox_clientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            exibirInformacoesCliente(newValue);
        });
    }

    private void exibirInformacoesCliente(Cliente cliente) {
        if (cliente != null) {
            tf_nome.setText(cliente.getNome());
            tf_email.setText(cliente.getEmail());
            tf_telefone.setText(cliente.getTelefone());
        } else {
            // Limpa os campos de texto se nenhum cliente for selecionado
            tf_nome.setText("");
            tf_email.setText("");
            tf_telefone.setText("");
        }
    }
    @FXML
    private void goToGerenciaCliente() {
        stageInitializer.changeScene("/GerenciaCliente.fxml");
    }

    @FXML
    private void SalvarAlteracao() {
        Cliente clienteSelecionado = cbox_clientes.getValue();

        if (clienteSelecionado != null) {
            String novoNome = tf_nome.getText();
            String novoEmail = tf_email.getText();
            String novoTelefone = tf_telefone.getText();

            // Atualiza os atributos do cliente
            clienteSelecionado.setNome(novoNome);
            clienteSelecionado.setEmail(novoEmail);
            clienteSelecionado.setTelefone(novoTelefone);

            // Salva as alterações no serviço ou repositório
            clienteService.salvarCliente(clienteSelecionado);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Sucesso!");
            alerta.setHeaderText(null);
            alerta.setContentText("Alterações salvas com sucesso!");
            alerta.showAndWait();

            // Limpa os campos de texto
            limparCampos();
        }
    }

    private void limparCampos() {
        tf_nome.clear();
        tf_email.clear();
        tf_telefone.clear();
    }

}
