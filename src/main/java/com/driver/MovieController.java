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

    MovieService movieService = new MovieService();

    @PostMapping("/add-movie")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie) {
        String message = movieService.addMovie(movie);
        if(!message.isEmpty()) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An internal server error occurred.");
        }
    }

    @PostMapping("/add-director")
    public ResponseEntity<String> addDirector(@RequestBody Director director) {
        String message = movieService.addDirector(director);
        if(!message.isEmpty()) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An internal server error occurred.");
        }
    }

    @PutMapping("/add-movie-director-pair")
    public ResponseEntity<String> addMovieDirectorPair(@RequestParam("movieName") String movieName, @RequestParam("directorName") String directorName) {
        String message = movieService.addMovieDirectorPair(movieName, directorName);
        if(!message.isEmpty()) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An internal server error occurred.");
        }
    }

    @GetMapping("/get-movie-by-name/{name}")
    public ResponseEntity<?> getMovieByName(@PathVariable("name") String movieName) {
        Movie movie = movieService.getMovieByName(movieName);
        if(movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie does not exist.");
        }
    }

    @GetMapping("/get-director-by-name/{name}")
    public ResponseEntity<?> getDirectorByName(@PathVariable("name") String directorName) {
        Director director = movieService.getDirectorByName(directorName);
        if(director != null) {
            return ResponseEntity.ok(director);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Director does not exist.");
        }
    }
    @GetMapping("/get-movies-by-director-name/{director}")
    public ResponseEntity<?> getMoviesByDirectorName(@PathVariable("director") String directorName) {
        ArrayList<String> movies = movieService.getMoviesByDirectorName(directorName);
        if(movies != null) {
            return ResponseEntity.ok(movies);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The director don't have movie");
        }
    }
    @GetMapping("/get-all-movies")
    public ResponseEntity<?> findAllMovies() {
        Collection<Movie> movies = movieService.findAllMovies();
        if(movies != null) {
            return ResponseEntity.ok(movies);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Movies Exist.");
        }
    }
    @DeleteMapping("delete-director-by-name")
    public ResponseEntity<?> deleteDirectorByName(@RequestParam("directorName")String directorName) {
        boolean isDeleted = movieService.deleteDirectorByName(directorName);
        if(isDeleted) {
            return ResponseEntity.ok("Deleted All Directors and their Movies.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Director not found or not able to delete.");
        }
    }
    @DeleteMapping("delete-all-directors")
    public ResponseEntity<?> deleteAllDirectors() {
        boolean isDeleted = movieService.deleteAllDirectors();
        if(isDeleted) {
            return ResponseEntity.ok("Deleted All Directors and their Movies.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Director not found or not able to delete.");
        }
    }
}
