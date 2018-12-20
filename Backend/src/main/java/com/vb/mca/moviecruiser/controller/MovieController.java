package com.vb.mca.moviecruiser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vb.mca.moviecruiser.domain.Movie;
import com.vb.mca.moviecruiser.exception.MovieExistException;
import com.vb.mca.moviecruiser.exception.MovieNotFoundException;
import com.vb.mca.moviecruiser.service.MovieService;

//@CrossOrigin(origins ="http://localhost:8080", maxAge = 3600)

/**
 * Controller class to control the request mapping flow
 * @RequestMapping used to route the http request which has the format "/api/movie/" 
 * @author VijayBhusan.Kumar
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	@Autowired
	private MovieService movieService;

	/**
	 * Save new movie
	 * @param movie
	 * @return {@link ResponseEntity}
	 */
	@PostMapping
	public ResponseEntity<?> saveNewMovie(@RequestBody final Movie movie) {
		ResponseEntity<?> responseEntity;
		try {
			movieService.saveMovie(movie);
			responseEntity = new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
		} catch (MovieExistException e) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	
	/**
	 * Update an exiting movie
	 * @param id
	 * @param movie
	 * @return {@link ResponseEntity}
	 */
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateMovie(@PathVariable("id") final int id, @RequestBody final Movie movie){
		ResponseEntity<?> responseEntity;
		try {
			movieService.updateMovie(movie);
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"Movie updated\"}", HttpStatus.ACCEPTED);
		} catch (MovieNotFoundException me) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + me.getMessage() + "\"}", HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return responseEntity;
	}
	
	/**
	 * Delete a movie
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping(path ="/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable("id") final int id){
		ResponseEntity<?> responseEntity;
		try {
			movieService.deleteMovie(id);
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"Movie deleted\"}", HttpStatus.OK);
		} catch (MovieNotFoundException me) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + me.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return responseEntity;
	} 
	
	/**
	 * Find a movie by id
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	@GetMapping(path ="/{id}")
	public ResponseEntity<?> findMovieById(@PathVariable("id") final int id){
		ResponseEntity<?> responseEntity;
		Movie movie = null;
		try {
			 movie = movieService.findMovieById(id);		
		} catch (MovieNotFoundException me) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + me.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		responseEntity = new ResponseEntity<Movie>(movie, HttpStatus.OK);
		return responseEntity;
	} 

	/**
	 * List all the movies
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Movie>> fetchAllMovies(){
		return new ResponseEntity<List<Movie>>(movieService.listAllMovies(), HttpStatus.OK);
	}
   
	/*			
	@GetMapping(path ="/{name}")
	public ResponseEntity<?> findMovieByName(@PathVariable("name") final String name){
		ResponseEntity<?> responseEntity;
		Movie movie = null;
		try {
			 movie = movieService.findMovieByName(name);	
		
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		}
		responseEntity = new ResponseEntity<Movie>(movie, HttpStatus.OK);
		return responseEntity;
	}*/
}
