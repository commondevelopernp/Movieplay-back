package api.movieplay.model.dao;


import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import api.movieplay.model.entity.Movie;

import java.util.List;

@Repository
public class MovieDaoImpl implements MovieDao {
	    
	    @PersistenceContext
	    private EntityManager entityManager;
	    
	    @Override
	    @Transactional
	    public List<Movie> getAllMovies(String genre, String title, String actor, String sort, String order, Integer rate, int page, int pageSize) {
	        StringBuilder hql = new StringBuilder("SELECT DISTINCT m FROM Movie m WHERE 1=1");
	        
	        if (genre != null && !genre.isEmpty()) {
	            hql.append(" AND :genre MEMBER OF m.genres");
	        }

	        if (title != null && !title.isEmpty()) {
	            hql.append(" AND m.title LIKE :title");
	        }

	        if (rate != null) {
	            hql.append(" AND m.rating = :rate");
	        }

	        // Filtrar por actor usando una subconsulta
	        if (actor != null && !actor.isEmpty()) {
	            hql.append(" AND EXISTS (SELECT 1 FROM Movie mc JOIN mc.cast c WHERE mc.id = m.id AND c = :actor)");
	        }

	        // Agregar la cláusula ORDER BY si se especifica el parámetro de ordenamiento
	        if (sort != null && !sort.isEmpty()) {
	            hql.append(" ORDER BY m.").append(sort).append(" ").append(order);
	        }

	        TypedQuery<Movie> query = entityManager.createQuery(hql.toString(), Movie.class);
	        query.setFirstResult((page - 1) * pageSize);
	        query.setMaxResults(pageSize);

	        if (genre != null && !genre.isEmpty()) {
	            query.setParameter("genre", genre);
	        }
	        if (title != null && !title.isEmpty()) {
	            query.setParameter("title", "%" + title + "%");
	        }
	        if (actor != null && !actor.isEmpty()) {
	            query.setParameter("actor", actor);
	        }
	        if (rate != null) {
	            query.setParameter("rate", rate);
	        }

	        return query.getResultList();
	    }
	

    @Override
    @Transactional
    public Movie getMovieById(String id) {
        return entityManager.find(Movie.class, id);
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        if (movie.getId() == null) {
            entityManager.persist(movie); // Si el ID es nulo, se inserta una nueva entidad
        } else {
            movie = entityManager.merge(movie); // Si el ID no es nulo, se actualiza la entidad existente
        }
        return movie;
    }

}
