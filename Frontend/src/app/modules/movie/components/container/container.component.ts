import { Component, OnInit, Input, Output } from '@angular/core';
import { Movie } from '../../movie';
import { MovieService } from '../../movie.service'
import { ActivatedRoute} from '@angular/router'
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http/src/response';

@Component({
  selector: 'movie-container',
  templateUrl:'./container.component.html',
  styleUrls: ['./container.component.css']
})
export class ContainerComponent implements OnInit {
  
  @Input()
  movie: Movie;
  @Input()  
  movies: Array<Movie>;
  @Input()
  useWatchlistApi: boolean;
  errorResponse: HttpErrorResponse;
  movieResponse: Movie;
  
  constructor(private movieService: MovieService, private snackBar: MatSnackBar) {
  }
  ngOnInit() {    
  }

  /**
   * Add the movie to watchlist and handle the HttpResponse from backend
   * @param movie 
   */
  addMovieToWatchlist(movie){   
    console.log("Add movie to watchlist called...")   
    this.movieService.saveMovieToWatchlist(movie).subscribe(response => {  
      console.log("Response: ", response);         
      this.movieResponse = response.body;      
      let message = `'${this.movieResponse.title}' added to watchlist`;
      this.handleResponse(response, message);
    },
    error => { // error path
      console.log("Error: ", error);
      this.errorResponse = error; 
      this.handleError(this.errorResponse);
    });     
  }

  /**
   * Delete the movie from watchlist and handle the HttpResponse from backend
   * @param movie 
   */
  deleteMovieFromWatchlist(movie){  
    console.log("Delete movie from watchlist called...")     
    let message = `'${movie.title}' deleted from watchlist`;    
    let index = this.movies.indexOf(movie);  
    
    this.movieService.deleteMovieFromWatchlist(movie).subscribe(response => {  
      this.movies.splice(index, 1);
      console.log("Response: ", response);                    
      this.handleResponse(response, message);
    },
    error => { // error path
       console.log("Error: ", error);
       this.errorResponse = error; 
       this.handleError(this.errorResponse);
      } 
    );    
  }

/**
 * Method to handle the http response object
 * @param response the response object returned from backend
 * @param message the custom message to display on the UI
 */
  private handleResponse(response: HttpResponse<Object>, message: string){
    console.log("Response body: ", response.body);
    console.log("Message: ", message);
    if(response.status == 200){      
      this.snackBar.open(message, '', {
        duration: 1500
      });
    }
    if(response.status == 201){
      this.snackBar.open(message, '', {
        duration: 1500
      });
    }    
  }      

  /**
   * Method to handle the http error response
   * @param error the error response object returned from backend
   */
  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      let message = `${error.error.message}`;     
      this.snackBar.open(message, '', {
            duration: 1500
      });
      console.error(`Error [code]: ${error.status}, ` +  `[message]: ${error.error.message}`);      
    }    
  }
}
