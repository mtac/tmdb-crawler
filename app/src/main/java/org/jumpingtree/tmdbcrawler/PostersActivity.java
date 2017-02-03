package org.jumpingtree.tmdbcrawler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.jumpingtree.tmdbcrawler.models.ApiConfigurationObject;
import org.jumpingtree.tmdbcrawler.models.ApiMoviesRequest;
import org.jumpingtree.tmdbcrawler.models.Movie;
import org.jumpingtree.tmdbcrawler.preferences.TMDBPreferences;
import org.jumpingtree.tmdbcrawler.utilities.NetworkUtils;
import org.jumpingtree.tmdbcrawler.utilities.TMDBRequestsParserUtils;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PostersActivity extends AppCompatActivity implements PostersAdapter.PosterAdapterOnClickHandler {

    private static final String TAG = PostersActivity.class.getSimpleName();

    private static final int GRID_COLUMN_COUNT = 2;

    private static final int SORT_POPULAR_MOVIES = 0;
    private static final int SORT_TOP_RATED_MOVIES = 1;

    private RecyclerView mRecyclerView;
    private PostersAdapter mPostersAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private Toast mActiveToast;

    private List<Movie> mMoviesList;
    private int mSelectedSort = SORT_POPULAR_MOVIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.intent_key_movies_list))) {
            mMoviesList = Parcels.unwrap(savedInstanceState.getParcelable(getString(R.string.intent_key_movies_list)));
        } else {
            mMoviesList = new ArrayList<>();
        }
        if(savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.intent_key_selected_sort))) {
            mSelectedSort = savedInstanceState.getInt(getString(R.string.intent_key_selected_sort),SORT_POPULAR_MOVIES);
        }

        setContentView(R.layout.activity_posters);

        updateActivityTitle();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie_posters);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_load_error_message);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, GRID_COLUMN_COUNT);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mPostersAdapter = new PostersAdapter(mMoviesList, this);
        mRecyclerView.setAdapter(mPostersAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadConfiguration();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(getString(R.string.intent_key_movies_list), Parcels.wrap(mMoviesList));
        outState.putInt(getString(R.string.intent_key_selected_sort), mSelectedSort);
        super.onSaveInstanceState(outState);
    }

    private void loadConfiguration() {
        Long lastConfigCheck = TMDBPreferences.getInstance().getLastConfigurationChangeDate(PostersActivity.this);
        long dif = new Date().getTime() - lastConfigCheck;
        long days = TimeUnit.MILLISECONDS.toDays(dif);
        boolean reload = days >= TMDBPreferences.getInstance().PREF_MIN_DAYS_BETWEEN_CONFIG_CHECKS;
        ApiConfigurationObject conf = TMDBPreferences.getInstance().getLastConfiguration(PostersActivity.this);

        if (conf == null || reload) {
            Log.d(TAG,"Fetching new configuration from server!");
            //TODO encapsulate Log to protect against exceptions while logging
            try{
                Log.d(TAG, "Last config load: " + new Date(lastConfigCheck));
                Log.d(TAG, "Days since last config load: " + days);
                Log.d(TAG, "Days between config loads: " + TMDBPreferences.getInstance().PREF_MIN_DAYS_BETWEEN_CONFIG_CHECKS);
            }catch(Exception e) {
                e.printStackTrace();
            }

            if(NetworkUtils.isOnline(PostersActivity.this)) {
                new FetchConfigsTask().execute();
            } else {
                showConnectionErrorMessage();
            }
        } else {
            Log.d(TAG,"Using cached configuration!");
            TMDBPreferences.getInstance().setmLastConfiguration(conf);
            if(mMoviesList == null || mMoviesList.isEmpty()) {
                showLoadingIndicator(true);
                loadMovies();
            }
        }
    }

    private void loadMovies() {
        if(NetworkUtils.isOnline(PostersActivity.this)) {
            String moviesUrl = null;

            switch (mSelectedSort) {
                case SORT_POPULAR_MOVIES:
                    moviesUrl = NetworkUtils.buildURLForTMDBPopularMovies(PostersActivity.this);
                    break;
                case SORT_TOP_RATED_MOVIES:
                    moviesUrl = NetworkUtils.buildURLForTMDBTopRatedMovies(PostersActivity.this);
                    break;
            }
            updateActivityTitle();
            new FetchMoviesTask().execute(moviesUrl);
        } else {
            showConnectionErrorMessage();
            showLoadingIndicator(false);
        }
    }

    private void updateActivityTitle() {
        switch (mSelectedSort) {
            case SORT_POPULAR_MOVIES:
                setTitle(getString(R.string.action_sort_popular));
                break;
            case SORT_TOP_RATED_MOVIES:
                setTitle(getString(R.string.action_sort_top_rated));
                break;
        }
    }

    @Override
    public void onPosterClick(int position) {
        Movie selectedMovie = mMoviesList.get(position);
//        showToast(Toast.makeText(PostersActivity.this, "Open Details for " + selectedMovie.getTitle(), Toast.LENGTH_LONG));
        Intent intent = new Intent(PostersActivity.this, PosterDetailsActivity.class);
        intent.putExtra(getString(R.string.intent_key_selected_movie), Parcels.wrap(selectedMovie));
        startActivity(intent);
    }

    private void showMoviePostersView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showNoDataMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setText(getString(R.string.no_data_message));
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setText(getString(R.string.load_error_message));
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showConnectionErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setText(getString(R.string.no_connection_message));
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showLoadingIndicator(boolean show) {
        if(show) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    private void showToast(Toast newToast) {
        if (mActiveToast != null) {
            mActiveToast.cancel();
        }
        mActiveToast = newToast;
        mActiveToast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //TODO should allow selection of the choice already loaded on screen for refresh purposes, or block it?

        switch (id) {
            case R.id.action_sort_popular:
                showToast(Toast.makeText(PostersActivity.this,"Load Popular Movies", Toast.LENGTH_LONG));
                mSelectedSort = SORT_POPULAR_MOVIES;
                loadMovies();
                break;
            case R.id.action_sort_top_rated:
                showToast(Toast.makeText(PostersActivity.this,"Load Top Rated Movies", Toast.LENGTH_LONG));
                mSelectedSort = SORT_TOP_RATED_MOVIES;
                loadMovies();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchConfigsTask extends AsyncTask<Void, Void, ApiConfigurationObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingIndicator(true);
        }

        @Override
        protected ApiConfigurationObject doInBackground(Void... params) {

            String url = NetworkUtils.buildURLForTMDBConfigurations(PostersActivity.this);
            String jsonConfig = NetworkUtils.getResponseFromHttpUrl(url);

            TMDBPreferences.getInstance().saveLastConfiguration(PostersActivity.this,jsonConfig);

            try {
                return TMDBRequestsParserUtils.parseJsonConfigResponse(jsonConfig);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ApiConfigurationObject config) {
            if (config != null) {
                Log.d(TAG,config.toString());
                TMDBPreferences.getInstance().setmLastConfiguration(config);
                if(mMoviesList == null || mMoviesList.isEmpty()) {
                    loadMovies();
                } else {
                    showLoadingIndicator(false);
                }
            } else {
                showLoadingIndicator(false);
                showErrorMessage();
            }
        }
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, ApiMoviesRequest> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingIndicator(true);
        }

        @Override
        protected ApiMoviesRequest doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String url = params[0];
            String jsonMovies = NetworkUtils.getResponseFromHttpUrl(url);

            try {
                return TMDBRequestsParserUtils.parseJsonMoviesResponse(jsonMovies);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ApiMoviesRequest moviesRequest) {
            if (moviesRequest != null) {
                //TODO Use page number to load more pages
                List<Movie> movies = moviesRequest.getMovies();
                if(movies != null) {
                    if (!movies.isEmpty()) {
                        showMoviePostersView();
                        mMoviesList = movies;
                        mPostersAdapter.setMoviesList(movies);
                    } else {
                        showNoDataMessage();
                    }
                } else {
                    showErrorMessage();
                    String errorMsg = moviesRequest.getStatusMessage();
                    if(errorMsg != null && !errorMsg.isEmpty()) {
                        showToast(Toast.makeText(PostersActivity.this,errorMsg,Toast.LENGTH_LONG));
                    }
                }
            } else {
                showErrorMessage();
            }
            showLoadingIndicator(false);
        }
    }
}
