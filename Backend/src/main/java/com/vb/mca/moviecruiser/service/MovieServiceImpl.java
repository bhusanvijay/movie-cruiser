package com.vb.mca.moviecruiser.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vb.mca.moviecruiser.domain.Movie;
import com.vb.mca.moviecruiser.exception.MovieExistException;
import com.vb.mca.moviecruiser.exception.MovieNotFoundException;
import com.vb.mca.moviecruiser.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	MovieRepository movieRepository;

	/**
	 * Save new movie to the repository
	 */
	@Override
	public boolean saveMovie(final Movie movie) throws MovieExistException {
		final Optional<Movie> movieOpt = movieRepository.findById(movie.getId());
		if(movieOpt.isPresent()) {
			throw new MovieExistException("Movie already exist, can't save!");
		}
		movieRepository.save(movie);
		return true;
	}
	
	/**
	 * Update movie to the repository
	 */
	@Override
	public boolean updateMovie(final Movie updatedMovie) throws MovieNotFoundException {
		
		Optional<Movie> movieOpt = movieRepository.findById(updatedMovie.getId()); 
		if(!movieOpt.isPresent()) {
			throw new MovieNotFoundException("Movie doesn't exist, can't update!");
		}
		movieOpt.get().setComments(updatedMovie.getComments());
		movieRepository.save(movieOpt.get());		
		return true;
	}

	/**
	 * Delete movie from the repository
	 */
	@Override
	public boolean deleteMovie(int id) throws MovieNotFoundException {
		Optional<Movie> movieOpt = movieRepository.findById(id); 
		if(!movieOpt.isPresent()) {
			throw new MovieNotFoundException("Movie doesn't exist, can't delete!");
		}
		movieRepository.delete(movieOpt.get());
		return true;
	}

	/**
	 * Find movie by Id
	 */
	@Override
	public Movie findMovieById(int id) throws MovieNotFoundException {
		Optional<Movie> movieOpt = movieRepository.findById(id); 
		if(!movieOpt.isPresent()) {
			throw new MovieNotFoundException("Movie doesn't exist, couldn't found!");
		}		
		return movieOpt.get();
	}

	/**
	 * Find movie by title
	 */
	@Override
	public Movie findMovieByTitle(String title) throws MovieNotFoundException {
		// TODO Auto-generated method stub
		Movie movie = movieRepository.findByTitle(title);
		if(Objects.isNull(movie)) {
			throw new MovieNotFoundException("Movie doesn't exist, couldn't found!");
		}
		return movie;
	}

	/**
	 * List all the movies
	 */
	@Override
	public List<Movie> listAllMovies() {
		return movieRepository.findAll();
	}
}
