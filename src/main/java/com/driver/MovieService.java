package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;
    public String addMovie(Movie movie) {
        boolean isMovieAdded = movieRepository.addMovie(movie);
        if(isMovieAdded) {
            return "Movie Added Successfully.";
        } else {
            return "";
        }
    }

    public String addDirector(Director director) {
        boolean isDirectorAdded = movieRepository.addDirector(director);
        if(isDirectorAdded) {
            return "Director Added Successfully.";
        } else {
            return "";
        }

    }
    public boolean isMovieAlreadyPaired(String movieName) {
        Collection<ArrayList<String>> AllMappedMovies = movieRepository.findAllMappedMovies();
        if(AllMappedMovies != null) {
            for (ArrayList<String> movies : AllMappedMovies) {
                boolean isExist = movies.contains(movieName);
                if(isExist) return true;
            }
        }
        return false;
    }
    public String addMovieDirectorPair(String movieName, String directorName) {
        Director director = movieRepository.getDirectorByName(directorName);
        Movie movie = movieRepository.getMovieByName(movieName);
        if(movie!= null) {
            if(director != null) {
                if(!isMovieAlreadyPaired(movieName)) {
                    boolean isMovieDirectorPaired = movieRepository.addMovieDirectorPair(movieName, directorName);
                    boolean isMovieCountUpdated =  movieRepository.updateMovieCount(directorName);
                    if(isMovieDirectorPaired && isMovieCountUpdated) {
                        return "Existing Movie is Paired to the Director";
                    } else {
                        return "Something Went wrong";
                    }
                } else {
                    return "Movie Already Paired";
                }
            } else {
                return "Director not exist";
            }
        } else {
            return "Movie not exist";
        }
    }

    public Movie getMovieByName(String movieName) {
        return movieRepository.getMovieByName(movieName);
    }

    public Director getDirectorByName(String directorName) {
        return movieRepository.getDirectorByName(directorName);
    }

    public List<String> getMoviesByDirectorName(String directorName) {
        return movieRepository.getMoviesByDirectorName(directorName);
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAllMovies();
    }
    private void deleteMappedMovies(List<String> movieNameList) {
        if(movieNameList != null) {
            for(String movieName: movieNameList) {
                if(getMovieByName(movieName) != null) {
                    movieRepository.deleteMovie(movieName);
                }
            }
        }
    }

    public boolean deleteDirectorByName(String directorName) {
        if(getDirectorByName(directorName) != null) {
            movieRepository.deleteDirector(directorName);
        } else {
            return false;
        }

        List<String> movieNameList = getMoviesByDirectorName(directorName);
        deleteMappedMovies(movieNameList);

        if(getMoviesByDirectorName(directorName) != null) {
            movieRepository.deleteDirectorByName(directorName);
        } else {
            return false;
        }
        return true;
    }

    public boolean deleteAllDirectors() {
        Collection<ArrayList<String>> AllMappedMovies = movieRepository.findAllMappedMovies();
        if(AllMappedMovies != null) {
            for(ArrayList<String> movies: AllMappedMovies) {
                deleteMappedMovies(movies);
            }
        }
        movieRepository.deleteAllDirectors();
        movieRepository.deleteCombineTable();
        return true;
    }
}
