package com.vb.mca.moviecruiser.service;

import java.util.List;

import com.vb.mca.moviecruiser.domain.Movie;
import com.vb.mca.moviecruiser.exception.MovieExistException;
import com.vb.mca.moviecruiser.exception.MovieNotFoundException;

public interface MovieService {
	
	/**
	 * Save the movie
	 * @param movie
	 * @return boolean
	 * @throws MovieExistException
	 */
	boolean saveMovie(Movie movie) throws MovieExistException;
	
	/**
	 * Update a movie details
	 * @param movie
	 * @return boolean
	 * @throws MovieNotFoundException
	 */
	boolean updateMovie(Movie movie) throws MovieNotFoundException;
	
	/**
	 * Delete a movie
	 * @param id
	 * @return boolean
	 * @throws MovieNotFoundException
	 */
	boolean deleteMovie(int id) throws MovieNotFoundException;
	
	/**
	 * Find a movie by Id
	 * @param id
	 * @return Movie
	 * @throws MovieNotFoundException
	 */
	Movie findMovieById(int id) throws MovieNotFoundException;
	
	/**
	 * Find a movie by name
	 * @param name
	 * @return Movie
	 * @throws MovieNotFoundException
	 */
	Movie findMovieByTitle(String name) throws MovieNotFoundException;
	
	/**
	 * List all the movies
	 * @return List<Movie>
	 */
	List<Movie> listAllMovies();
}
