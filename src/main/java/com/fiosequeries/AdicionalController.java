package com.fiosequeries;

import com.fiosequeries.Model.Adicional;
import com.fiosequeries.Model.Pedido;
import com.fiosequeries.Model.Tecido;
import com.fiosequeries.service.AdicionalService;
import com.fiosequeries.service.PedidoService;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AdicionalController implements Initializable {

    @Autowired
    private AdicionalService adicionalService;

    @FXML
    private TableView<Adicional> table_Adicionais;

    @FXML
    private TableColumn<Adicional, Long> column_id;

    @FXML
    private TableColumn<Adicional, String> column_nome;

    @FXML
    private TableColumn<Adicional, Double> column_multiplicador;

    @FXML
    private Button btn_voltar;

    @FXML
    private TextField tf_nome;

    @FXML
    private TextField tf_multiplicador;

    @FXML
    private Button btn_adicionar;

    @FXML
    private Button btn_excluir;

    @FXML
    private Button btn_editar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        carregarAdicionais();

    }

    private void configurarColunas(){
        column_id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        column_nome.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        column_multiplicador.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMultiplicador()));
    }

    private void carregarAdicionais(){
        table_Adicionais.getItems().clear(); // limpa a table view antes para não ter repetição de registros
        List<Adicional> adicionais = adicionalService.listarAdicionais();
        table_Adicionais.getItems().addAll(adicionais);
    }

    @FXML
    private void excluirAdicional() {
        Adicional adicionalSelecionado = table_Adicionais.getSelectionModel().getSelectedItem();
        if (adicionalSelecionado != null) {
            adicionalService.excluirAdicional(adicionalSelecionado.getId());
            table_Adicionais.getItems().remove(adicionalSelecionado);
        }
    }

    @FXML
    private void editarAdicional(ActionEvent event) {
        Adicional adicionalSelecionado = table_Adicionais.getSelectionModel().getSelectedItem();
        if (adicionalSelecionado != null) {
            // Habilitar os campos de texto para edição
            tf_nome.setEditable(true);
            tf_multiplicador.setEditable(true);
        }
    }

    @FXML
    private void salvarEdicaoAdicional(ActionEvent event) {
        Adicional adicionalSelecionado = table_Adicionais.getSelectionModel().getSelectedItem();
        if (adicionalSelecionado != null) {

            String nome = tf_nome.getText();
            double multiplicador = Double.parseDouble(tf_multiplicador.getText());


            adicionalSelecionado.setNome(nome);
            adicionalSelecionado.setMultiplicador(multiplicador);


            adicionalService.atualizarAdicional(adicionalSelecionado);


            tf_nome.setEditable(false);
            tf_multiplicador.setEditable(false);

            table_Adicionais.refresh();
        }


    }

    public void adicionarAdicional(ActionEvent event) {

        String nome = tf_nome.getText();
        double multiplicador = Double.parseDouble(tf_multiplicador.getText());


        Adicional novoAdicional = new Adicional(nome, multiplicador);


        adicionalService.adicionarAdicional(novoAdicional);


        tf_nome.clear();
        tf_multiplicador.clear();


        carregarAdicionais();
        table_Adicionais.refresh();
    }

    private final StageInitializer stageInitializer;

    @Autowired
    public AdicionalController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    @FXML
    private void goToOutros() {
        stageInitializer.changeScene("/OutrosScreen.fxml");
    }


}