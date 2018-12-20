package com.vb.mca.moviecruiser.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vb.mca.moviecruiser.domain.Movie;

/**
 * Class to run MovieRespository
 * @author VijayBhusan.Kumar
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class MovieRepositoryTest {
	
	@Autowired
	private transient MovieRepository movieRepo;
	
	public void setMovieRepo(final MovieRepository movieRepo) {
		this.movieRepo = movieRepo;
	}
	
	@Test
	public void testSaveMovie() throws Exception {
		Movie actualMovie = movieRepo.save(createMovie(1, "Sholay", "A film by Ramesh Shippy", "/ubuntu/Desktop/MovieCruiserApp/poster", "A story of dacoit", "01/01/1980"));
		final Movie movie = movieRepo.getOne(1);
		assertEquals(movie.getId(), actualMovie.getId());
	}
	
	@Test
	public void testUpdateMovie() throws Exception {
		movieRepo.save(createMovie(1, "Sholay", "A film by Ramesh Shippy", "/ubuntu/Desktop/MovieCruiserApp/poster", "A story of dacoit", "01/01/1980"));
		final Movie movie = movieRepo.getOne(1);
		movie.setComments("Bollywood biggest movie ever");
		movieRepo.save(movie);
		final Movie updatedMovie = movieRepo.getOne(1);
		assertEquals("Bollywood biggest movie ever", updatedMovie.getComments());
	}
	
	@Test
	public void testDeleteMovie() throws Exception {
		Movie actualMovie = movieRepo.save(createMovie(1, "Sholay", "A film by Ramesh Shippy", "/ubuntu/Desktop/MovieCruiserApp/poster", "A story of dacoit", "01/01/1980"));
		final Movie movie = movieRepo.getOne(1);		
		assertEquals(movie.getTitle(), actualMovie.getTitle());
		movieRepo.delete(movie);		
		assertEquals(Optional.empty(), movieRepo.findById(1));		
	}
	
	@Test
	public void testFindMovie() {
		Movie actualMovie = movieRepo.save(createMovie(1, "Sholay", "A film by Ramesh Shippy", "/ubuntu/Desktop/MovieCruiserApp/poster", "A story of dacoit", "01/01/1980"));
		final Movie movie = movieRepo.getOne(1);
		assertEquals(movie.getTitle(), actualMovie.getTitle());
	}
	
	public void testListAllMovies() {
		movieRepo.save(createMovie(1, "Sholay", "A film by Ramesh Shippy", "/ubuntu/Desktop/MovieCruiserApp/poster", "A story of dacoit", "01/01/1980"));
		movieRepo.save(createMovie(2, "Titanic", "One of my favourite movies", "/ubuntu/Desktop/MovieCruiserApp/poster", "A tale of Titanic ship", "01/01/1990"));
		List<Movie> movies = movieRepo.findAll();
		assertEquals(movies.get(0).getTitle(), "Sholay");
		assertEquals(movies.get(1).getTitle(), "Titanic");
	}
	
	private Movie createMovie(int id, String name, String comments, String poster_path, String overview, String release_date) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(name);
		movie.setComments(comments);
		movie.setPoster_path(poster_path);
		movie.setRelease_date(release_date);
		movie.setOverview(overview);
		return movie;		
	}
}
