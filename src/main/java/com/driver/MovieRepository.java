package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MovieRepository {
    Map<String, Movie> movieTable = new HashMap<>();
    Map<String, Director> directorTable = new HashMap<>();

    Map<String, ArrayList<String>> combineTable= new HashMap<>();

    public boolean addMovie(Movie movie) {
        movieTable.put(movie.getName(), movie);
        return true;
    }

    public boolean addDirector(Director director) {
        directorTable.put(director.getName(), director);
        return true;
    }

    public Director getDirectorByName(String directorName) {
        return directorTable.get(directorName);
    }
    public Movie getMovieByName(String movieName) {
        return movieTable.get(movieName);
    }
    public boolean updateMovieCount(String directorName) {
        if(directorTable.get(directorName) != null) {
            int moviesCount = directorTable.get(directorName).getNumberOfMovies();
            directorTable.get(directorName).setNumberOfMovies(moviesCount+1);
            return true;
        } else {
            return false;
        }

    }
    public boolean addMovieDirectorPair(String movieName, String directorName) {
        if (combineTable.get(directorName) != null) {
            ArrayList<String> movies = combineTable.get(directorName);
            movies.add(movieName);
            combineTable.put(directorName, movies);
        } else {
            ArrayList<String> movies = new ArrayList<>();
            movies.add(movieName);
            combineTable.put(directorName, movies);
        }
        return true;
    }

    public List<String> getMoviesByDirectorName(String directorName) {
        return combineTable.get(directorName);
    }

    public List<Movie> findAllMovies() {
        return new ArrayList<>(movieTable.values());
    }
    public Collection<ArrayList<String>> findAllMappedMovies() {
        return combineTable.values();
    }

    public void deleteDirectorByName(String directorName) {
        combineTable.remove(directorName);
    }
    public void deleteMovie(String movieName) {
        movieTable.remove(movieName);
    }
    public void deleteDirector(String directorName) {
        directorTable.remove(directorName);
    }

    public void deleteAllDirectors() {
        directorTable.clear();
    }
    public void deleteCombineTable() {
        combineTable.clear();
    }
}
