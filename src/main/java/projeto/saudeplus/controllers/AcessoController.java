package projeto.saudeplus.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import projeto.saudeplus.models.Usuario;
import projeto.saudeplus.repositories.UsuarioRepository;

@Controller
public class AcessoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Tela de login
    @GetMapping("/login")
    public String login() {
        return "login";  // Retorna o template de login
    }

    // Tela de cadastro
    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";  // Retorna o template de cadastro
    }

    // Lógica de login
    @PostMapping("/login")
    public String realizarLogin(String username, String password, Model model) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            // Verificação de senha correta
            model.addAttribute("usuario", usuario);
            return "home";  // Redireciona para a página inicial do paciente
        }
        model.addAttribute("erro", "Usuário ou senha incorretos.");
        return "login";  // Retorna para o login se a autenticação falhar
    }

    // Lógica de cadastro
    @PostMapping("/cadastro")
    public String realizarCadastro(@Valid Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Se houver erro de validação, retorna ao formulário
            return "cadastro";
        }

        // Verifica se o usuário já existe
        if (usuarioRepository.findByUsername(usuario.getUsername()) != null) {
            model.addAttribute("erro", "Usuário já existe.");
            return "cadastro";
        }

        // Salva o novo usuário no banco de dados
        usuarioRepository.save(usuario);
        return "redirect:/login";  // Redireciona para a tela de login após o cadastro
    }

    // Lógica de logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Obtém a sessão atual (se existir)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();  // Invalidar a sessão
        }
        return "redirect:/login";  // Redireciona para a página de login após o logout
    }
}
