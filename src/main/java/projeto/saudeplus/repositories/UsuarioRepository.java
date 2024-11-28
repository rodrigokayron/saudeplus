package projeto.saudeplus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.saudeplus.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para encontrar o usuário pelo username
    Usuario findByUsername(String username);
}
