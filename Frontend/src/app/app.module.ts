import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog'
import { MatInputModule } from '@angular/material/input';
import { MovieModule } from './modules/movie/movie.module';
import { AppComponent } from './app.component';

const appRoutes:Routes = [
  {
    path: '',
    redirectTo: 'movies',
    pathMatch: 'full'
  }
] 

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    RouterModule,
    MovieModule,
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatInputModule,
    RouterModule.forRoot(appRoutes),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
