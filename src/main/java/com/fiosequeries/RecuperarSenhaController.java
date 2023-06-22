package com.fiosequeries;

import com.fiosequeries.service.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

@Component
public class RecuperarSenhaController implements Initializable {


    private final UsuarioService usuarioService;
    @FXML
    private PasswordField tf_nova_senha;

    @FXML
    private PasswordField tf_confirmacao;

    @FXML
    private TextField tf_email;

    @FXML
    private Button btn_voltar;

    @FXML
    private Button btn_enviar;

    @FXML
    private Label lb_situacao_email;

    private final StageInitializer stageInitializer;

    @Autowired
    public RecuperarSenhaController(UsuarioService usuarioService, StageInitializer stageInitializer) {
        this.usuarioService = usuarioService;
        this.stageInitializer = stageInitializer;
    }

    public Connection clicarBotaoEnviar(ActionEvent event) {

        String email = tf_email.getText();
        String novaSenha = tf_nova_senha.getText();
        String confirmacaoSenha = tf_confirmacao.getText();

        if (email != null && !email.isEmpty() && novaSenha != null && !novaSenha.isEmpty()) {
            if (novaSenha.equals(confirmacaoSenha)) {
                boolean alterouSenha = usuarioService.alterarSenhaPorEmail(email, novaSenha);
                if (alterouSenha) {
                    lb_situacao_email.setText("Senha atualizada!");
                    lb_situacao_email.setTextFill(Color.LAWNGREEN);
                    // Senha alterada com sucesso
                } else {
                    lb_situacao_email.setText("*E-mail não encontrado");
                    lb_situacao_email.setTextFill(Color.RED);
                    // Não foi possível alterar a senha
                }
            } else {
                lb_situacao_email.setText("*A confirmação da senha não corresponde à nova senha");
                lb_situacao_email.setTextFill(Color.RED);
                // Confirmação de senha inválida
            }
        } else {
            lb_situacao_email.setText("*Todos os campos devem ser preenchidos");
            lb_situacao_email.setTextFill(Color.RED);
            // Campos vazios, solicite que o usuário insira o email e a nova senha
        }
//
//        String email = tf_email.getText();
//        String novaSenha = tf_nova_senha.getText();
//        String confirmacaoSenha = tf_confirmacao.getText();
//
//        if (email != null && !email.isEmpty() && novaSenha != null && !novaSenha.isEmpty()) {
//            boolean alterouSenha = usuarioService.alterarSenhaPorEmail(email, novaSenha);
//            if (alterouSenha) {
//                lb_situacao_email.setText("Senha atualizada!");
//                lb_situacao_email.setTextFill(Color.LAWNGREEN);
//                // Senha alterada com sucesso
//
//            } else {
//                lb_situacao_email.setText("*E-mail não encontrado");
//                lb_situacao_email.setTextFill(Color.RED);
//                // Não foi possível alterar a senha
//
//            }
//        } else {
//            lb_situacao_email.setText("*Todos os campos devem ser preenchidos");
//            lb_situacao_email.setTextFill(Color.RED);
//            // Campos vazios, solicite que o usuário insira o email e a nova senha
//
//        }

//        String emailUsuario = tf_email.getText();
//
//        boolean emailExistente = usuarioService.verificarEmailExistente(emailUsuario);
//
//        if (emailExistente) {
//            lb_situacao_email.setText("Senha atualizada!");
//            lb_situacao_email.setTextFill(Color.LAWNGREEN);
//        } else {
//            lb_situacao_email.setText("*E-mail não encontrado.");
//            lb_situacao_email.setTextFill(Color.RED);
//        }


//        if(tf_email.getText().contains(email)){
//            lb_situacao_email.setText("Senha atualizada!");
//            lb_situacao_email.setTextFill(Color.LAWNGREEN);
//        }else{
//            lb_situacao_email.setText("*E-mail não encontrado.");
//            lb_situacao_email.setTextFill(RED);
//        }
        return null;
    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        stageInitializer.changeScene("/MainScreen.fxml");
    }

    public void initialize(URL url, ResourceBundle rb){

    }
}