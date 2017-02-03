package org.jumpingtree.tmdbcrawler.utilities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import org.jumpingtree.tmdbcrawler.R;
import org.jumpingtree.tmdbcrawler.preferences.TMDBPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    /**
     * This method checks if we have internet connection
     */
    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String buildURLForTMDBConfigurations(Context context) {
        String configBaseURL = context.getString(R.string.tmdb_base_url) + context.getString(R.string.tmdb_configuration);
        Uri builtUri = Uri.parse(configBaseURL).buildUpon()
                .appendQueryParameter(context.getString(R.string.tmdb_api_key_param), context.getString(R.string.tmdb_api_key))
                .build();
        return builtUri.toString();
    }

    public static String buildURLForTMDBPopularMovies(Context context) {
        return buildURLForTMDBPopularMovies(context,null);
    }

    public static String buildURLForTMDBPopularMovies(Context context, String page) {
        StringBuilder popularBaseURL = new StringBuilder();
        popularBaseURL.append(context.getString(R.string.tmdb_base_url));
        popularBaseURL.append(context.getString(R.string.tmdb_movie));
        popularBaseURL.append(context.getString(R.string.tmdb_popular_movies));

        Uri.Builder builder = Uri.parse(popularBaseURL.toString()).buildUpon();
        builder.appendQueryParameter(context.getString(R.string.tmdb_api_key_param), context.getString(R.string.tmdb_api_key));

        if(page != null) {
            builder.appendQueryParameter(context.getString(R.string.tmdb_page_param), page);
        }

        Uri builtUri = builder.build();

        return builtUri.toString();
    }

    public static String buildURLForTMDBTopRatedMovies(Context context) {
        return buildURLForTMDBTopRatedMovies(context,null);
    }

    public static String buildURLForTMDBTopRatedMovies(Context context, String page) {
        StringBuilder popularBaseURL = new StringBuilder();
        popularBaseURL.append(context.getString(R.string.tmdb_base_url));
        popularBaseURL.append(context.getString(R.string.tmdb_movie));
        popularBaseURL.append(context.getString(R.string.tmdb_top_rated_movies));

        Uri.Builder builder = Uri.parse(popularBaseURL.toString()).buildUpon();
        builder.appendQueryParameter(context.getString(R.string.tmdb_api_key_param), context.getString(R.string.tmdb_api_key));

        if(page != null) {
            builder.appendQueryParameter(context.getString(R.string.tmdb_page_param), page);
        }

        Uri builtUri = builder.build();

        return builtUri.toString();
    }

    public static String buildURLForImage(Context context, String imagePath, List<String> sizes) {
        String baseUrl = TMDBPreferences.getInstance().getmLastConfiguration().getBaseUrl();
        String defaultSize = context.getString(R.string.tmdb_default_image_size);
        String size;

        if(sizes.contains(defaultSize)) {
            size = defaultSize;
        } else {
            size = sizes.get(0);
            Log.w(TAG, "Default image size (" + defaultSize + ") not available, the first size available was chosen (" + size + ").");
        }

        StringBuilder imageUrl = new StringBuilder();

        imageUrl.append(baseUrl);
        imageUrl.append(size);
        imageUrl.append(imagePath);

        return imageUrl.toString();
    }

    public static String getResponseFromHttpUrl(String url) {
        OkHttpClient client = new OkHttpClient();
        Log.d(TAG, "Execute GET from URL: " + url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
