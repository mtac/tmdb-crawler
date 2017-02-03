package org.jumpingtree.tmdbcrawler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.jumpingtree.tmdbcrawler.models.Movie;
import org.jumpingtree.tmdbcrawler.preferences.TMDBPreferences;
import org.jumpingtree.tmdbcrawler.utilities.NetworkUtils;

import java.util.List;


public class PostersAdapter extends RecyclerView.Adapter<PostersAdapter.PostersAdapterViewHolder> {

    private static final String TAG = PostersAdapter.class.getSimpleName();

    private List<Movie> mMoviesList;
    private final PosterAdapterOnClickHandler mClickHandler;

    public PostersAdapter(List moviesList, PosterAdapterOnClickHandler clickHandler){
        this.mMoviesList = moviesList;
        this.mClickHandler = clickHandler;
    }

    public interface PosterAdapterOnClickHandler {
        void onPosterClick(int position);
    }

    @Override
    public PostersAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.poster_list_item, viewGroup, false);

        return new PostersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostersAdapterViewHolder holder, int position) {

        Movie tempMovie = mMoviesList.get(position);
        if(tempMovie != null) {
            String url;
            try {
                url = NetworkUtils.buildURLForImage(
                        holder.mPosterImageView.getContext(),
                        tempMovie.getPosterPath(),
                        TMDBPreferences.getInstance().getmLastConfiguration().getPosterSizes());
            }catch (Exception e) {
                e.printStackTrace();
                url = "http://placehold.it/"+holder.mPosterImageView.getWidth()+"x"+holder.mPosterImageView.getHeight();
            }
                Picasso.with(holder.mPosterImageView.getContext())
                    .load(url)
                    .into(holder.mPosterImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (mMoviesList == null){
            return 0;
        }
        return mMoviesList.size();
    }

    public void setMoviesList(List<Movie> moviesList) {
        this.mMoviesList = moviesList;
        notifyDataSetChanged();
    }

    public class PostersAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mPosterImageView;

        public PostersAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onPosterClick(adapterPosition);
        }
    }


}
