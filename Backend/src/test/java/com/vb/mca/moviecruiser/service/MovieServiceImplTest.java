package com.vb.mca.moviecruiser.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vb.mca.moviecruiser.domain.Movie;
import com.vb.mca.moviecruiser.exception.MovieExistException;
import com.vb.mca.moviecruiser.exception.MovieNotFoundException;
import com.vb.mca.moviecruiser.repository.MovieRepository;

public class MovieServiceImplTest {
	
	/**
	 * Mock MovieRepository
	 */
	@Mock
	private transient MovieRepository movieRepo;
	private transient Movie movie;
	
	/**
	 * Inject MovieServiceImpl
	 */
	@InjectMocks
	private transient MovieServiceImpl movieServiceImpl;
	transient Optional<Movie> options;
	
	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		movie = new Movie(1, "Avtaar", "www.movies.com", "01/01/2015", "3D Animation movie", "good movie");
		options = Optional.of(movie);
	}
	
	@Test
	public void testMockCreation() {
		assertNotNull("Jpa creations fail: use @injectMock on ServiceMovieImpl, movie");
	}
	
	/**
	 * Testing saveMovie method happy path
	 * @throws MovieExistException
	 */
	@Test
	public void testSaveMovie_success() throws MovieExistException {
		when(movieRepo.save(movie)).thenReturn(movie);
		final boolean flag = movieServiceImpl.saveMovie(movie);
		assertTrue("Saving movie failed, the call to ServiceMovieImpl failed", flag);
		verify(movieRepo, times(1)).save(movie);
		verify(movieRepo, times(1)).findById(movie.getId());
	}
	
	/**
	 * Testing saveMovie method failure case
	 * @throws MovieExistException
	 */
	@Test(expected = MovieExistException.class)	
	public void saveMovie_failure() throws MovieExistException {
		when(movieRepo.findById(1)).thenReturn(options);
		when(movieRepo.save(movie)).thenReturn(movie);
		final boolean flag = movieServiceImpl.saveMovie(movie);
		assertFalse("Saving movie failed", flag);
		verify(movieRepo, times(1)).findById(movie.getId());
	}
	
	/**
	 * Testing updateMovie method
	 * @throws MovieNotFoundException
	 */
	@Test
	public void testUpdateMovie() throws MovieNotFoundException {
		when(movieRepo.findById(1)).thenReturn(options);
		when(movieRepo.save(movie)).thenReturn(movie);
		movie.setComments("It has good animations");
		final boolean flag = movieServiceImpl.updateMovie(movie);
		assertTrue("Movie comments updated", flag);
		assertEquals("Saving movie failed", "It has good animations", movie.getComments());
		verify(movieRepo, times(1)).findById(movie.getId());
	}
	
	/**
	 * Testing deleteMovie method
	 * @throws MovieNotFoundException
	 */
	@Test
	public void testDeleteMovie() throws MovieNotFoundException {
		when(movieRepo.findById(1)).thenReturn(options);
		doNothing().when(movieRepo).delete(movie);
		final boolean flag = movieServiceImpl.deleteMovie(1);
		assertTrue("Deleting movie failed", flag);
		verify(movieRepo, times(1)).delete(movie);
		verify(movieRepo, times(1)).findById(movie.getId());
	}
	

	/**
	 * Testing findByMovieId method
	 * @throws MovieNotFoundException
	 */
	@Test
	public void testFindByMovieId() throws MovieNotFoundException {
		when(movieRepo.findById(1)).thenReturn(options);		
		final Movie actualMovie = movieServiceImpl.findMovieById(1);
		assertEquals("Finding movie by id movie failed", movie, actualMovie);
		verify(movieRepo, times(1)).findById(movie.getId());
	}
	
	/**
	 * Test listAllMovies method
	 */
	@Test
	public void testListAllMovies() {
		final List<Movie> movieList = new ArrayList<>(1);
		when(movieRepo.findAll()).thenReturn(movieList);
		final List<Movie> actualMovieList = movieServiceImpl.listAllMovies();
		assertEquals(movieList, actualMovieList);
		verify(movieRepo, times(1)).findAll();
	}
}
