package com.fiosequeries;

import com.fiosequeries.Model.Adicional;
import com.fiosequeries.Model.Tecido;
import com.fiosequeries.service.AdicionalService;
import com.fiosequeries.service.TecidoService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
@Component
public class TecidoController implements Initializable{

    @Autowired
    private TecidoService tecidoService;

    @FXML
    private TableView<Tecido> table_tecidos;

    @FXML
    private TableColumn<Tecido, Long> column_id;

    @FXML
    private TableColumn<Tecido, String> column_nome;

    @FXML
    private TableColumn<Tecido, Double> column_precoBase;

    @FXML
    private Button btn_voltar;

    @FXML
    private Button btn_excluir;

    @FXML
    private Button btn_adicionar;

    @FXML
    private Button btn_editar;

    @FXML
    private TextField tf_nome;

    @FXML
    private TextField tf_precoBase;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        carregarTecidos();
    }

    private void configurarColunas(){
        column_id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        column_nome.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        column_precoBase.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPreco()));
    }

    private void carregarTecidos(){
        table_tecidos.getItems().clear(); // limpa a table view antes para não ter repetição de registros
        List<Tecido> tecidos = tecidoService.listarTecidos();
        table_tecidos.getItems().addAll(tecidos);
    }

    @FXML
    private void excluirTecido() {
        Tecido tecidoSelecionado = table_tecidos.getSelectionModel().getSelectedItem();
        if (tecidoSelecionado != null) {
            tecidoService.excluirTecido(tecidoSelecionado.getId());
            table_tecidos.getItems().remove(tecidoSelecionado);
        }
    }

    @FXML
    private void editarTecido(ActionEvent event) {
        Tecido tecidoSelecionado = table_tecidos.getSelectionModel().getSelectedItem();
        if (tecidoSelecionado != null) {
            // Habilitar os campos de texto para edição
            tf_nome.setEditable(true);
            tf_precoBase.setEditable(true);
        }
    }

    @FXML
    private void salvarEdicaoTecido(ActionEvent event) {
        Tecido tecidoSelecionado = table_tecidos.getSelectionModel().getSelectedItem();
        if (tecidoSelecionado != null) {

            String nome = tf_nome.getText();
            double preco = Double.parseDouble(tf_precoBase.getText());


            tecidoSelecionado.setNome(nome);
            tecidoSelecionado.setPreco(preco);


            tecidoService.atualizarTecido(tecidoSelecionado);


            tf_nome.setEditable(false);
            tf_precoBase.setEditable(false);

            table_tecidos.refresh();
        }


    }

    public void adicionarTecido(ActionEvent event) {
        // Recuperar os valores dos TextFields
        String nome = tf_nome.getText();
        double preco = Double.parseDouble(tf_precoBase.getText());

        // Criar um novo objeto Tecido com os valores fornecidos
        Tecido novoTecido = new Tecido(nome, preco);

        // Chamar o serviço para adicionar o novo tecido no banco de dados
        tecidoService.adicionarTecido(novoTecido);

        // Limpar os campos de texto
        tf_nome.clear();
        tf_precoBase.clear();

        // Atualizar a exibição da TableView com os tecidos atualizados
        carregarTecidos();
        table_tecidos.refresh();
    }

    private final StageInitializer stageInitializer;

    @Autowired
    public TecidoController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    @FXML
    private void goToOutros() {
        stageInitializer.changeScene("/OutrosScreen.fxml");
    }
}