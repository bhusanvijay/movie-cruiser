import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { Movie } from '../../movie';
import { HttpClient } from '@angular/common/http';
import { MovieService } from '../../movie.service';
import { MovieDialogComponent } from '../movie-dialog/movie-dialog.component';


@Component({
  selector: 'movie-thumbnail',
  templateUrl: './thumbnail.component.html',  
  styleUrls: ['./thumbnail.component.css']
})
export class ThumbnailComponent implements OnInit {

  @Input()
  movie: Movie;  
  @Input()
  useWatchlistApi: boolean;
  @Output()
  addMovie = new EventEmitter();
  @Output()
  deleteMovie = new EventEmitter();
  @Output()
  updateMovie = new EventEmitter();

  constructor(private movieService: MovieService, private snackBar: MatSnackBar, public dialog: MatDialog) {    
   }

  ngOnInit() {    
  }

  addToWatchlist(){     
    this.addMovie.emit(this.movie);
  }

  deleteFromWatchlist(){    
    this.deleteMovie.emit(this.movie);
  }
  
  updateFromWatchlist(actionType){
    console.log("Movie comments is getting updated");
    let dialogRef = this.dialog.open(MovieDialogComponent, {
      width :'400px',
      data: { obj: this.movie, actionType:actionType}
    });
    console.log("open dialog");
    dialogRef.afterClosed().subscribe(result =>{
      console.log('close dialog');
    })
  }
}

