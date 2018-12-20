import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Movie } from '../../movie';
import { MovieService } from '../../movie.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http/src/response';

@Component({
  selector: 'movie-movie-dialog',
  templateUrl: './movie-dialog.component.html',
  styles: ['./movie-dialog.component.css']
})
export class MovieDialogComponent implements OnInit {
  
  movie: Movie;
  comments: string;
  actionType: string; 
  errorResponse: HttpErrorResponse; 

  constructor(private snackBar: MatSnackBar, private dialogRef: MatDialogRef<MovieDialogComponent>, 
  @Inject(MAT_DIALOG_DATA) public data: any, private movieService: MovieService) { }

  ngOnInit() {    
    this.movie = this.data.obj;    
  }
  
  onCancelClick(){
    this.dialogRef.close();
  }

  /**
   * Update movie comment
   */
  updateComments(){  
    console.log("Update movie comments called...")      
    let message = `'${this.movie.title}' movie comments updated`;    
    this.movie.comments = this.comments;
    this.dialogRef.close();    
    this.movieService.updateMovieComments(this.movie).subscribe(response => {  
      this.handleResponse(response, message);
    },
    error => { // error path
      this.errorResponse = error; 
      this.handleError(this.errorResponse);
    });    
  }

  /**
   * Method to handle the http response object
   * @param response the response object returned from backend
   * @param message the custom message to display on the UI
   */
  private handleResponse(response: HttpResponse<Object>, message: string){
    console.log("Response body: ", response.body);
    console.log("Message: ", message);  
    if(response.status == 202){
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
