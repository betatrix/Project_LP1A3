package com.fiosequeries;

import com.fiosequeries.Model.Usuario;
import com.fiosequeries.service.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
@Component
public class MainScreenController implements Initializable {

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private Label lbError;

    @Autowired
    private UsuarioService usuarioService;


    private final StageInitializer stageInitializer;

    public MainScreenController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    @FXML
    private void login() {
        String email = tfEmail.getText();
        String senha = pfSenha.getText();
        if(email.isEmpty() || senha.isEmpty()){
            lbError.setText("Todos os campos precisam ser preenchidos");
            lbError.setTextFill(Color.RED);
            return;
        }
        Usuario user = usuarioService.findByEmail(email);
        if(user != null){
            String userSenha = user.getSenhaUsuario();
            if(usuarioService.senhaValida(senha, userSenha)){
                System.out.println("Senha correta");
                stageInitializer.changeScene("/GerenciaPedidoOrcamento.fxml");
            }else{
                lbError.setText("Email/senha estâo incorretos");
                lbError.setTextFill(Color.RED);
                return;
            }
        }
        lbError.setText("Email/senha estão incorretos");
        lbError.setTextFill(Color.RED);
        return;
    }

    @FXML
    private void goToCriarUsuario(ActionEvent event) throws IOException {
        stageInitializer.changeScene("/GerenteScreen.fxml");
    }

    @FXML
    private void goToRecuperarSenha(ActionEvent event) throws IOException {
        // Basta trocar o /GerenteScreen
        stageInitializer.changeScene("/RecuperarSenha.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
