package com.fiosequeries.service;

import com.fiosequeries.Model.Usuario;
import com.fiosequeries.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder encoder;

    public UsuarioService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }
    public boolean senhaValida(String senha, String senhaVerificada) {
        return encoder.matches(senha, senhaVerificada);
    }

    public Usuario buscarUsuario(Long id){
        Usuario usuario = new Usuario();
        usuario = usuarioRepository.findById(id).orElse(null);
        return usuario;
    }

    public boolean verificarEmailExistente(String emailUsuario) {
        return usuarioRepository.existsByEmailUsuario(emailUsuario);
    }

    public boolean alterarSenhaPorEmail(String emailUsuario, String novaSenha) {
        Usuario usuario = usuarioRepository.findByEmailUsuario(emailUsuario);
        if (usuario != null) {
            usuario.setSenhaUsuario(encoder.encode(novaSenha));
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public Usuario findByEmail(String emailUsuario) {
        return usuarioRepository.findByEmailUsuario(emailUsuario);
    }

    public int createUser(Usuario usuario){
        try{
            Usuario exists = usuarioRepository.findByEmailUsuario(usuario.getEmailUsuario());
            if(exists != null){
                return 2;
            }
            if(usuario != null){
                usuario.setSenhaUsuario(encoder.encode(usuario.getSenhaUsuario()));
                usuarioRepository.save(usuario);
                return 0;
            }
            return 1;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return 1;
        }
    }
}
