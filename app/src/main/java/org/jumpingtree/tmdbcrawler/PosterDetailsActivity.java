package org.jumpingtree.tmdbcrawler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jumpingtree.tmdbcrawler.models.Movie;
import org.jumpingtree.tmdbcrawler.preferences.TMDBPreferences;
import org.jumpingtree.tmdbcrawler.utilities.NetworkUtils;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Locale;

public class PosterDetailsActivity extends AppCompatActivity {

    private static final String TAG = PosterDetailsActivity.class.getSimpleName();

    private Movie mSelectedMovie;
    private TextView mMovieTitle;
    private TextView mMovieReleaseDate;
    private TextView mMovieVoteAverage;
    private TextView mMoviePlotSynopsis;
    private ImageView mMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_details);

        if(savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.intent_key_selected_movie))) {
            mSelectedMovie = Parcels.unwrap(savedInstanceState.getParcelable(getString(R.string.intent_key_selected_movie)));
        } else {
            Intent parentActivityIntent = getIntent();
            if (parentActivityIntent != null) {
                if (parentActivityIntent.hasExtra(getString(R.string.intent_key_selected_movie))) {
                    this.mSelectedMovie = Parcels.unwrap(getIntent().getParcelableExtra(getString(R.string.intent_key_selected_movie)));
                }
            }
        }

        if(this.mSelectedMovie != null) {
            Log.d(TAG, "Details for " + this.mSelectedMovie.getTitle());

            mMovieTitle = (TextView) findViewById(R.id.movie_details_title);
            mMovieReleaseDate = (TextView) findViewById(R.id.movie_details_release_date);
            mMovieVoteAverage = (TextView) findViewById(R.id.movie_details_vote_average);
            mMoviePlotSynopsis = (TextView) findViewById(R.id.movie_details_plot_synopsis);
            mMoviePoster = (ImageView) findViewById(R.id.movie_details_poster);

            mMovieTitle.setText(this.mSelectedMovie.getOriginalTitle());
            mMovieReleaseDate.setText(this.mSelectedMovie.getReleaseDate());
            mMovieVoteAverage.setText(String.valueOf(this.mSelectedMovie.getVoteAverage()));
            mMoviePlotSynopsis.setText(this.mSelectedMovie.getOverview());

            String url;
            try {
                url = NetworkUtils.buildURLForImage(
                        mMoviePoster.getContext(),
                        mSelectedMovie.getPosterPath(),
                        TMDBPreferences.getInstance().getmLastConfiguration().getPosterSizes());
            }catch (Exception e) {
                e.printStackTrace();
                url = "http://placehold.it/"+mMoviePoster.getWidth()+"x"+mMoviePoster.getHeight();
            }
            Picasso.with(mMoviePoster.getContext())
                    .load(url)
                    .into(mMoviePoster);

        } else {
            Log.w(TAG, "Could not get movie to show, closing details activity!");
            finish();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(getString(R.string.intent_key_selected_movie), Parcels.wrap(mSelectedMovie));
        super.onSaveInstanceState(outState);
    }
}
