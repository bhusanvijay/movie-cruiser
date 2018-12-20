package com.vb.mca.moviecruiser.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vb.mca.moviecruiser.domain.Movie;
import com.vb.mca.moviecruiser.service.MovieService;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

	/**
	 * Reference to MockMvc
	 */
	@Autowired
	private transient MockMvc mvc;
	@MockBean
	private transient MovieService movieService;
	private transient Movie movie;
	static List<Movie> movies;

	@Before
	public void setUp() {
		movies = new ArrayList<>();
		movie = new Movie(1, "Border" , "www.movies.com", "01/01/1997", "Shows the valor of Indian Army", "Nice movie");
		movies.add(movie);
		movie = new Movie(1, "LoC Kargil" , "www.movies.com", "01/01/2000", "Shows the valor of Indian Army", "Based on Kargil");
		movies.add(movie);
	}

	/**
	 * to test save movie
	 * @throws Exception
	 */
	@Test
	public void testSaveNewMovie_success() throws Exception {
		when(movieService.saveMovie(movie)).thenReturn(true);
		mvc.perform(post("/api/movie").contentType(MediaType.APPLICATION_JSON).content(jsonToString(movie))).andExpect(status().isCreated());
		verify(movieService, times(1)).saveMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(movieService);
	}

	/**
	 * to test update movie
	 * @throws Exception
	 */
	@Test
	public void testUpdateMovie_success() throws Exception {
		movie.setComments("very good movie");
		when(movieService.updateMovie(movie)).thenReturn(true);
		mvc.perform(put("/api/movie/{id}",1).contentType(MediaType.APPLICATION_JSON).content(jsonToString(movie))).andExpect(status().isAccepted());
		verify(movieService, times(1)).updateMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(movieService);
	}

	/**
	 * to test delete movie
	 * @throws Exception
	 */
	@Test
	public void testDeleteMovie_success() throws Exception {
		when(movieService.deleteMovie(1)).thenReturn(true);
		mvc.perform(delete("/api/movie/{id}", 1)).andExpect(status().isOk());
		verify(movieService, times(1)).deleteMovie(1);
		verifyNoMoreInteractions(movieService);
	}

	/**
	 * to test find movie by id
	 * @throws Exception
	 */
	@Test
	public void testFindMovieById_success() throws Exception {
		when(movieService.findMovieById(1)).thenReturn(movies.get(0));
		mvc.perform(get("/api/movie/{id}", 1)).andExpect(status().isOk());
		verify(movieService, times(1)).findMovieById(1);
		verifyNoMoreInteractions(movieService);
	}

	/**
	 * to test list all movies
	 * @throws Exception
	 */
	@Test
	public void testFetchAllMovies_success() throws Exception {
		when(movieService.listAllMovies()).thenReturn(null);
		mvc.perform(get("/api/movie")).andExpect(status().isOk());
		verify(movieService, times(1)).listAllMovies();
		verifyNoMoreInteractions(movieService);
	}

	/**
	 * Parse string format object to json format
	 * @param object
	 * @return
	 * @throws JsonProcessingException
	 */
	private static String jsonToString(final Object object) throws JsonProcessingException {

		final ObjectMapper objMapper = new ObjectMapper();
		String stringValue;
		try {
			final String jsonValue = objMapper.writeValueAsString(object);
			stringValue = jsonValue;
		} catch (JsonProcessingException e) {
			stringValue = "Json processing exception";
		}
		return stringValue;
	}
}
