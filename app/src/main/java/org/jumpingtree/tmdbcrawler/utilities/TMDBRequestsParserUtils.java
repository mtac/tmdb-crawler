package org.jumpingtree.tmdbcrawler.utilities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jumpingtree.tmdbcrawler.models.ApiConfigurationObject;
import org.jumpingtree.tmdbcrawler.models.ApiMoviesRequest;
import org.jumpingtree.tmdbcrawler.models.Movie;

import java.util.ArrayList;
import java.util.List;

public final class TMDBRequestsParserUtils {

    private static final String TAG = TMDBRequestsParserUtils.class.getSimpleName();

    /**
     * Replace with JSON lib to simplify
     * Test GSON - https://github.com/google/gson
     * Test Moshi - https://github.com/square/moshi
     * Test Jackson - http://wiki.fasterxml.com/JacksonDocumentation
     */

    public static ApiMoviesRequest parseJsonMoviesResponse(String response) throws JSONException {
        final String MOVIES_PAGE = "page";
        final String MOVIES_RESULTS = "results";
        final String MOVIES_TOTAL_RESULTS = "total_results";
        final String MOVIES_TOTAL_PAGES = "total_pages";
        final String MOVIES_STATUS_MSG = "status_message";
        final String MOVIES_STATUS_CODE = "status_code";

        if(response == null) {
            return null;
        }

        JSONObject moviesJson = new JSONObject(response);

        ApiMoviesRequest moviesRequest = new ApiMoviesRequest();

        if (moviesJson.has(MOVIES_PAGE)) {
            moviesRequest.setPage(moviesJson.getInt(MOVIES_PAGE));
        }
        if(moviesJson.has(MOVIES_RESULTS)) {
            List<Movie> movies = new ArrayList<>();
            JSONArray jsonArray = moviesJson.getJSONArray(MOVIES_RESULTS);
            Movie parsedMovie;
            for (int i = 0; i < jsonArray.length(); i++) {
                parsedMovie = parseJsonMovie(jsonArray.getJSONObject(i));
                if(parsedMovie != null) {
                    movies.add(parsedMovie);
                }
            }
            moviesRequest.setMovies(movies);
        }
        if (moviesJson.has(MOVIES_TOTAL_RESULTS)) {
            moviesRequest.setTotalResults(moviesJson.getInt(MOVIES_TOTAL_RESULTS));
        }
        if (moviesJson.has(MOVIES_TOTAL_PAGES)) {
            moviesRequest.setTotalPages(moviesJson.getInt(MOVIES_TOTAL_PAGES));
        }
        if (moviesJson.has(MOVIES_STATUS_CODE)) {
            moviesRequest.setStatusCode(moviesJson.getInt(MOVIES_STATUS_CODE));
        }
        if (moviesJson.has(MOVIES_STATUS_MSG)) {
            moviesRequest.setStatusMessage(moviesJson.getString(MOVIES_STATUS_MSG));
        }

        return moviesRequest;
    }

    private static Movie parseJsonMovie(JSONObject jsonMovie) throws JSONException {
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_ADULT = "adult";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_GENRE_IDS = "genre_ids";
        final String MOVIE_ID = "id";
        final String MOVIE_ORIGINAL_TITLE = "original_title";
        final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
        final String MOVIE_TITLE = "title";
        final String MOVIE_BACKDROP_PATH = "backdrop_path";
        final String MOVIE_POPULARITY = "popularity";
        final String MOVIE_VOTE_COUNT = "vote_count";
        final String MOVIE_VIDEO = "video";
        final String MOVIE_VOTE_AVERAGE = "vote_average";

        if(jsonMovie != null) {
            Movie movie = new Movie();

            if (jsonMovie.has(MOVIE_POSTER_PATH)) {
                movie.setPosterPath(jsonMovie.getString(MOVIE_POSTER_PATH));
            }
            if (jsonMovie.has(MOVIE_ADULT)) {
                movie.setAdult(jsonMovie.getBoolean(MOVIE_ADULT));
            }
            if (jsonMovie.has(MOVIE_OVERVIEW)) {
                movie.setOverview(jsonMovie.getString(MOVIE_OVERVIEW));
            }
            if (jsonMovie.has(MOVIE_RELEASE_DATE)) {
                movie.setReleaseDate(jsonMovie.getString(MOVIE_RELEASE_DATE));
            }
            if (jsonMovie.has(MOVIE_GENRE_IDS)) {
                JSONArray jsonArray = jsonMovie.getJSONArray(MOVIE_GENRE_IDS);
                List<Integer> ids = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    ids.add(jsonArray.getInt(i));
                }
                movie.setGenreIds(ids);
            }
            if (jsonMovie.has(MOVIE_ID)) {
                movie.setId(jsonMovie.getInt(MOVIE_ID));
            }
            if (jsonMovie.has(MOVIE_ORIGINAL_TITLE)) {
                movie.setOriginalTitle(jsonMovie.getString(MOVIE_ORIGINAL_TITLE));
            }
            if (jsonMovie.has(MOVIE_ORIGINAL_LANGUAGE)) {
                movie.setOriginalLanguage(jsonMovie.getString(MOVIE_ORIGINAL_LANGUAGE));
            }
            if (jsonMovie.has(MOVIE_TITLE)) {
                movie.setTitle(jsonMovie.getString(MOVIE_TITLE));
            }
            if (jsonMovie.has(MOVIE_BACKDROP_PATH)) {
                movie.setBackdropPath(jsonMovie.getString(MOVIE_BACKDROP_PATH));
            }
            if (jsonMovie.has(MOVIE_POPULARITY)) {
                movie.setPopularity(jsonMovie.getDouble(MOVIE_POPULARITY));
            }
            if (jsonMovie.has(MOVIE_VOTE_COUNT)) {
                movie.setVoteCount(jsonMovie.getInt(MOVIE_VOTE_COUNT));
            }
            if (jsonMovie.has(MOVIE_VIDEO)) {
                movie.setVideo(jsonMovie.getBoolean(MOVIE_VIDEO));
            }
            if (jsonMovie.has(MOVIE_VOTE_AVERAGE)) {
                movie.setVoteAverage(jsonMovie.getDouble(MOVIE_VOTE_AVERAGE));
            }
            return movie;
        } else {
            return null;
        }
    }

    public static ApiConfigurationObject parseJsonConfigResponse(String response) throws JSONException {

        final String CONFIG_IMAGES = "images";
        final String CONFIG_BASE_URL = "base_url";
        final String CONFIG_SECURE_BASE_URL = "secure_base_url";
        final String CONFIG_BACKDROP_SIZES = "backdrop_sizes";
        final String CONFIG_LOGO_SIZES = "logo_sizes";
        final String CONFIG_POSTER_SIZES = "poster_sizes";
        final String CONFIG_PROFILE_SIZES = "profile_sizes";
        final String CONFIG_STILL_SIZES = "still_sizes";
        final String CONFIG_CHANGE_KEYS = "change_keys";

        ApiConfigurationObject config = new ApiConfigurationObject();
        JSONArray jsonArray;
        List<String> configs;

        if(response != null) {
            JSONObject configJson = new JSONObject(response);

            if (configJson.has(CONFIG_IMAGES)) {
                JSONObject imagesJson = configJson.getJSONObject(CONFIG_IMAGES);

                if(imagesJson.has(CONFIG_BASE_URL)) {
                    config.setBaseUrl(imagesJson.getString(CONFIG_BASE_URL));
                }
                if(imagesJson.has(CONFIG_SECURE_BASE_URL)) {
                    config.setSecureBaseUrl(imagesJson.getString(CONFIG_SECURE_BASE_URL));
                }
                if(imagesJson.has(CONFIG_BACKDROP_SIZES)) {
                    jsonArray = imagesJson.getJSONArray(CONFIG_BACKDROP_SIZES);
                    configs = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++) {
                        configs.add(jsonArray.getString(i));
                    }
                    config.setBackdropSizes(configs);
                }
                if(imagesJson.has(CONFIG_LOGO_SIZES)) {
                    jsonArray = imagesJson.getJSONArray(CONFIG_LOGO_SIZES);
                    configs = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++) {
                        configs.add(jsonArray.getString(i));
                    }
                    config.setLogoSizes(configs);
                }
                if(imagesJson.has(CONFIG_POSTER_SIZES)) {
                    jsonArray = imagesJson.getJSONArray(CONFIG_POSTER_SIZES);
                    configs = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++) {
                        configs.add(jsonArray.getString(i));
                    }
                    config.setPosterSizes(configs);
                }
                if(imagesJson.has(CONFIG_PROFILE_SIZES)) {
                    jsonArray = imagesJson.getJSONArray(CONFIG_PROFILE_SIZES);
                    configs = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++) {
                        configs.add(jsonArray.getString(i));
                    }
                    config.setProfileSizes(configs);
                }
                if(imagesJson.has(CONFIG_STILL_SIZES)) {
                    jsonArray = imagesJson.getJSONArray(CONFIG_STILL_SIZES);
                    configs = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++) {
                        configs.add(jsonArray.getString(i));
                    }
                    config.setStillSizes(configs);
                }
            }
            if(configJson.has(CONFIG_CHANGE_KEYS)) {
                jsonArray = configJson.getJSONArray(CONFIG_CHANGE_KEYS);
                configs = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++) {
                    configs.add(jsonArray.getString(i));
                }
                config.setChangeKeys(configs);
            }
        }
        return config;
    }
}
