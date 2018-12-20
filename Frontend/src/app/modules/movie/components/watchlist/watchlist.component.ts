import { Component, OnInit } from '@angular/core';
import { Movie } from '../../movie';
import { MovieService } from '../../movie.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'movie-watchlist',
  templateUrl: './watchlist.component.html',
  styles: []
})
export class WatchlistComponent implements OnInit {

  movies: Array<Movie>;
  useWatchlistApi: boolean;

  constructor(private movieService: MovieService, private matSnackbar: MatSnackBar) { 
    this.movies = [];
    this.useWatchlistApi = true;    
  }

  ngOnInit() {
    let message = "Watchlist is empty";
    this.movieService.getWatchlistedMovies().subscribe((movies) => {
      if(movies.length === 0){
        this.matSnackbar.open(message, '', {
          duration: 2000
        });
      }
      this.movies.push(...movies)
    });
  }
}
