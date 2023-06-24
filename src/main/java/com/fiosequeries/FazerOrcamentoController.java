package com.fiosequeries;

import com.fiosequeries.Model.ItemPedido;
import com.fiosequeries.Model.Orcamento;
import com.fiosequeries.Model.Pedido;
import com.fiosequeries.service.itemPedidoService;
import com.fiosequeries.service.OrcamentoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FazerOrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    @Autowired
    private itemPedidoService itemPedidoService;

    @FXML
    private TextField lb_dataOrc;

    @FXML
    private TextField lb_valorOrc;

    @FXML
    private TextArea lb_Obs;

    @FXML
    private Button btn_SalvaOrc;

    @FXML
    private Button btn_prosseguePed;

    @FXML
    private Button btn_cancelaOrc;

    @FXML
    private Label lb_idFunc;

    private final StageInitializer stageInitializer;

    public FazerOrcamentoController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }



//    private AdicionarItemController adicionarItemController;
//    public void setAdicionarItemController(AdicionarItemController adicionarItemController) {
//        this.adicionarItemController = adicionarItemController;
//    }


    private ItemPedido itemPedido;

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    private List<ItemPedido> itensPedido;

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }


    @FXML
    private void btn_SalvaOrcAction() {
        String dataOrcamento = lb_dataOrc.getText();
        String valorOrcamento = lb_valorOrc.getText();
        String observacoes = lb_Obs.getText();

        // Verifica se os campos obrigatórios foram preenchidos
        if (dataOrcamento.isEmpty() || valorOrcamento.isEmpty()) {
            exibirMensagem("Preencha todos os campos obrigatórios!");
            return;
        }

        // Cria um novo objeto Orcamento
        Orcamento orcamento = new Orcamento();
        orcamento.setDataCriacao(LocalDate.parse(dataOrcamento));
        orcamento.setValorTotal(Double.parseDouble(valorOrcamento));
        orcamento.setObservacoes(observacoes);

//        orcamento.adicionarItemPedido(itemPedido);

        // Salva o orçamento e o item no banco de dados
        orcamentoService.salvarOrcamento(orcamento);
        itemPedidoService.salvarItemPedido(itemPedido);

        // Exibe uma mensagem de sucesso
        exibirMensagem("Orçamento salvo com sucesso!");

        // Limpa os campos e retorna à tela anterior
        lb_dataOrc.clear();
        lb_valorOrc.clear();
        lb_Obs.clear();
    }

    @FXML
    private void btn_prosseguePedAction() {
        stageInitializer.changeScene("/FinalizarPedido.fxml");
    }

    @FXML
    private void btn_cancelaOrcAction() {
        stageInitializer.changeScene("/GerenciaPedidoOrcamento.fxml");
    }


    private void exibirMensagem(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}