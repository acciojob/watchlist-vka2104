package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie) {
        String message = movieService.addMovie(movie);
        if(!message.isEmpty()) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Movie already present in the List.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-director")
    public ResponseEntity<String> addDirector(@RequestBody Director director) {
        String message = movieService.addDirector(director);
        if(!message.isEmpty()) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/add-movie-director-pair")
    public ResponseEntity<String> addMovieDirectorPair(@RequestParam("movieName") String movieName, @RequestParam("directorName") String directorName) {
        String message = movieService.addMovieDirectorPair(movieName, directorName);
        if(!message.isEmpty()) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-movie-by-name/{name}")
    public ResponseEntity<Movie> getMovieByName(@PathVariable("name") String movieName) {
        Movie movie = movieService.getMovieByName(movieName);
        if(movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity<Director> getDirectorByName(@PathVariable("name") String directorName) {
        Director director = movieService.getDirectorByName(directorName);
        if(director != null) {
            return new ResponseEntity<>(director, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-movies-by-director-name/{director}")
    public ResponseEntity<ArrayList<String>> getMoviesByDirectorName(@PathVariable("director") String directorName) {
        ArrayList<String> movies = movieService.getMoviesByDirectorName(directorName);
        if(movies != null) {
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-all-movies")
    public ResponseEntity<Collection<Movie>> findAllMovies() {
        Collection<Movie> movies = movieService.findAllMovies();
        if(movies != null) {
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("delete-director-by-name")
    public ResponseEntity<String> deleteDirectorByName(@RequestParam("directorName")String directorName) {
        boolean isDeleted = movieService.deleteDirectorByName(directorName);
        if(isDeleted) {
            return new ResponseEntity<>("Deleted All Directors and their Movies.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Director not found or not able to delete.", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("delete-all-directors")
    public ResponseEntity<String> deleteAllDirectors() {
        boolean isDeleted = movieService.deleteAllDirectors();
        if(isDeleted) {
            return new ResponseEntity<>("Deleted All Directors and their Movies.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Director not found or not able to delete.", HttpStatus.BAD_REQUEST);
        }
    }
}
