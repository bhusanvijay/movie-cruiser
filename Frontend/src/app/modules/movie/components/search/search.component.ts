import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../movie.service';
import { Movie } from '../../movie';

@Component({
  selector: 'movie-search',
  templateUrl: './search.component.html',
  styles: [`
    .search-form{
      margin: 10px;
    }
    `
  ]
})
export class SearchComponent implements OnInit {

  movies: Array<Movie>;
  constructor(private movieService: MovieService) { }

  ngOnInit() {
  }

  onEnter(searchKey){
    console.log('Search movie called... to search:', searchKey);
    this.movieService.searchMovies(searchKey).subscribe(movies => {
      this.movies = movies;
    })
  }

}
