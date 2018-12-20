import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Router } from '@angular/router/src/router';
import { ContainerComponent } from './components/container/container.component';
import { TmdbContainerComponent } from './components/tmdb-container/tmdb-container.component';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { SearchComponent } from './components/search/search.component';
const movieRoutes: Routes = [
    {
        path: 'movies',
        children: [
            {
                path: 'popular',
                component: TmdbContainerComponent,   
                data:{
                    movieType: 'popular'
                }
            },
            {
                path: 'top_rated',
                component: TmdbContainerComponent,
                data:{
                    movieType: 'top_rated'
                }
            },
            {
                path: 'watchlist',
                component: WatchlistComponent,                
            },
            {
                path: '',
                redirectTo: '/movies/popular',
                pathMatch: 'full'
            },
            {
                path: 'search', 
                component: SearchComponent                               
            }
        ]
    }
]

@NgModule({
    imports: [
        RouterModule.forChild(movieRoutes)
    ],
    exports:[
        RouterModule,
    ]

})

export class MovieRouterModule{

}