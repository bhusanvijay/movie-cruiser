import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Movie } from './movie';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators/map';
import { retry } from 'rxjs/operators';

@Injectable()
export class MovieService {

  tmdbEndpoint: string;
  imagePrefix: string;
  apiKey: string;
  watchlistEndpoint: string;
  springEndpoint: string;
  searchEndpint: string;

  constructor(private http: HttpClient) { 
    this.tmdbEndpoint ='https://api.themoviedb.org/3/movie';
    this.imagePrefix = 'https://image.tmdb.org/t/p/w500';
    this.apiKey = "api_key=abcd02c557150893aa9bae540e66cb15";
   //this.watchlistEndpoint = "http://localhost:3000/watchlist";
    this.springEndpoint = 'http://localhost:8080/api/movie';
    this.searchEndpint = 'https://api.themoviedb.org/3/search/movie?';

  }
/**
 * Get and list all the movies of specific type
 * @param type type of the movie e.g. popular, top_rated
 * @param page number of page to display
 */
  getMovies(type: string, page: number = 1): Observable<Array<Movie>> {
    const endPointUrl = `${this.tmdbEndpoint}/${type}?${this.apiKey}&page=${page}`;
    return this.http.get(endPointUrl).pipe(
                retry(3),
                map(this.pickMovieResults),
                map(this.transformPosterPath.bind(this))
    );
  }  

  /**
   * set the poster path in the movie object for each movie in the list
   * @param movies Array<Movie>
   */
  transformPosterPath(movies): Array<Movie> {
    return movies.map(movie => {
      movie.poster_path = `${this.imagePrefix}${movie.poster_path}`;      
      return movie;
    });
  }
  
  /**
   * Returns the results from the response object
   * @param response HttpResponse
   */
  pickMovieResults(response){
    return response['results'];
  }

/**
 * Method to search a movie from TMDB
 * @param searchKey search string to search a Movie
 */
  searchMovies(searchKey: string):Observable<Array<Movie>> {

    if(searchKey.length > 0){
      const searchUrl = `${this.searchEndpint}${this.apiKey}&page=1&query=${searchKey}`;  
       
      return this.http.get(searchUrl).pipe(
        retry(3),
        map(this.pickMovieResults),
        map(this.transformPosterPath.bind(this))
      );
    }
  }

  /**
   * List all the movies which are in the watchlist
   */
  getWatchlistedMovies(): Observable<Array<Movie>>{
    return this.http.get<Array<Movie>>(this.springEndpoint);
  }
   
  /**
   * Add a movie to watchlist, eventually save it to the DB
   * @param movie Movie object
   */
  saveMovieToWatchlist(movie): Observable<HttpResponse<Movie>>{   
    //return this.http.post(this.springEndpoint, movie);
    return this.http.post<Movie>(this.springEndpoint, movie, {observe: "response"});
  }

  /**
   * Delete a movie from watchlist, eventually from the DB
   * @param movie Movie object
   */
  deleteMovieFromWatchlist(movie): Observable<HttpResponse<Object>>{
    const url = `${this.springEndpoint}/${movie.id}`;    
   // return this.http.delete(url, {responseType: 'text'});
    return this.http.delete(url, {observe: "response"});
  }

  /**
   * Update the comment for a movie, locally in the DB
   * @param movie Movie object
   */
  updateMovieComments(movie): Observable<HttpResponse<Object>>{    
    const url = `${this.springEndpoint}/${movie.id}`;    
    //return this.http.put(url, movie);
    console.log("Movie comments :" + movie.comments)
    return this.http.put(url, movie, {observe: "response"});
  }  
}
