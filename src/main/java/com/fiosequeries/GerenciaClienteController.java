package com.fiosequeries;

import com.fiosequeries.Model.Cliente;
import com.fiosequeries.service.ClienteService;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleObjectProperty;

@Component
public class GerenciaClienteController implements Initializable {

    @Autowired
    private ClienteService clienteService;

    @FXML
    private TextField tf_inserir_nome;

    @FXML
    private TableView<Cliente> table_cliente;

    @FXML
    private TableColumn<Cliente, Long> column_id;

    @FXML
    private TableColumn<Cliente, String> column_nome;

    @FXML
    private TableColumn<Cliente, String> column_email;

    @FXML
    private TableColumn<Cliente, String> column_telefone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        carregarClientes();
    }

    private final StageInitializer stageInitializer;

    public GerenciaClienteController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    @FXML
    private void goToInserirCliente() {
        stageInitializer.changeScene("/InserirCliente.fxml");
    }

    @FXML
    private void goToAlterarCliente() {
        stageInitializer.changeScene("/AlterarCliente.fxml");
    }

    @FXML
    private void goToFuncionarioView() {
        stageInitializer.changeScene("/FuncionarioView.fxml");
    }

    private void configurarColunas() {
        column_id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        column_nome.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        column_email.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));
        column_telefone.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTelefone()));
    }

    @FXML
    private void carregarClientes() {
        try {
            // Limpa a tabela antes de adicionar os clientes
            table_cliente.getItems().clear();

            List<Cliente> clientes = clienteService.listarClientes();

            // Filtra os clientes com nome "Desconhecido" e não os mostra na tabela
            clientes = clientes.stream()
                    .filter(cliente -> cliente.getNome() != null && !cliente.getNome().equals("Desconhecido"))
                    .collect(Collectors.toList());

            table_cliente.getItems().addAll(clientes);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

        @FXML
        private void buscarClientePorNome () {
            String nomePesquisado = tf_inserir_nome.getText();

            List<Cliente> clientesEncontrados = clienteService.buscarClientesPorNome(nomePesquisado);

            table_cliente.getItems().clear();

            if (!clientesEncontrados.isEmpty()) {
                table_cliente.getItems().addAll(clientesEncontrados);
            }
        }

        @FXML
        private void excluirCliente () {
            Cliente clienteSelecionado = table_cliente.getSelectionModel().getSelectedItem();

            if (clienteSelecionado != null) {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Confirmação");
                alerta.setHeaderText("Excluir Cliente");
                alerta.setContentText("Tem certeza que deseja excluir o cliente " + clienteSelecionado.getNome() + "?");

                // Configura os botões do alerta
                ButtonType botaoConfirmar = new ButtonType("Confirmar");
                ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                alerta.getButtonTypes().setAll(botaoConfirmar, botaoCancelar);

                // Exibe o alerta e aguarda a resposta do usuário
                Optional<ButtonType> resultado = alerta.showAndWait();

                if (resultado.isPresent() && resultado.get() == botaoConfirmar) {
                    clienteService.excluirCliente(clienteSelecionado);
                    table_cliente.getItems().remove(clienteSelecionado);
                }
            } else {
                // Nenhum cliente selecionado, exiba uma mensagem de erro ou aviso
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Nenhum cliente selecionado");
                alerta.setContentText("Por favor, selecione um cliente para excluir.");
                alerta.showAndWait();
            }
        }
    }
