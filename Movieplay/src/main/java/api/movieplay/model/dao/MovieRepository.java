package api.movieplay.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import api.movieplay.model.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si es necesario
}
