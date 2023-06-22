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
public class GerenteScreenController implements Initializable {
    @Autowired
    private UsuarioService usuarioService;

    @FXML
    private TextField tfCodGerente;

    @FXML
    private TextField tfNomeUsuario;

    @FXML
    private PasswordField pfSenhaUsuario;

    @FXML
    private TextField tfEmailUsuario;

    @FXML
    private Label lbInfo;

    private final StageInitializer stageInitializer;

    public GerenteScreenController(StageInitializer stageInitializer) {
        this.stageInitializer = stageInitializer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        stageInitializer.changeScene("/MainScreen.fxml");
    }

    @FXML
    private void createUsuario(ActionEvent event)  throws IOException{
        String codGerenteText = tfCodGerente.getText();
        boolean gerente = codGerenteText.equals("F&Q2023")? true : false;
        if(!gerente){
            lbInfo.setText("Código de gerente incorreto!");
            lbInfo.setTextFill(Color.RED);
            return;
        }
        String nome = tfNomeUsuario.getText();
        String email = tfEmailUsuario.getText();
        String senha = pfSenhaUsuario.getText();
        if(nome.isEmpty() || senha.isEmpty() || email.isEmpty()){
            lbInfo.setText("Preencha todos os campos!");
            lbInfo.setTextFill(Color.RED);
            return;
        }

        Usuario user = new Usuario();
        user.setEmailUsuario(email);
        user.setNomeUsuario(nome);
        user.setSenhaUsuario(senha);
        user.setGerente(gerente);
        int response = usuarioService.createUser(user);
        switch (response){
            case 0:
                lbInfo.setText("Usuário cadastrado com sucesso!");
                lbInfo.setTextFill(Color.LAWNGREEN);
                break;
            case 1:
                lbInfo.setText("Não foi possível cadastrar!");
                lbInfo.setTextFill(Color.RED);
                break;
            case 2:
                lbInfo.setText("Já existe um usuário com esse email!");
                lbInfo.setTextFill(Color.RED);
                break;
        }

    }
}
