package ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinequest.R;

import java.util.List;

import data.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<Movie> movieList;
    private OnClickListener listener;

    public MovieAdapter(List<Movie> movieList, OnClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.voteAverageTextView.setText(String.format("%.2f", movie.getVoteAverage()) + " ⭐");

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(holder.posterImageView);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClick(position, movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void submitList(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        notifyDataSetChanged();
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    // Interface for the click listener
    public interface OnClickListener {
        void onClick(int position, Movie model);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView voteAverageTextView;
        ImageView posterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.movie_title);
            voteAverageTextView = itemView.findViewById(R.id.movie_vote_average);
            posterImageView = itemView.findViewById(R.id.movie_poster);
        }
    }
}
