package org.jumpingtree.tmdbcrawler.models;


import org.parceler.Parcel;

import java.util.Arrays;
import java.util.List;

@Parcel
public class Movie {

    String posterPath;
    Boolean adult;
    String overview;
    String releaseDate;
    List<Integer> genreIds;
    Integer id;
    String originalTitle;
    String originalLanguage;
    String title;
    String backdropPath;
    Double popularity;
    Integer voteCount;
    Boolean video;
    Double voteAverage;

    public Movie() {
    }

    public Movie(String posterPath, Boolean adult, String overview, String releaseDate,
                 List<Integer> genreIds, Integer id, String originalTitle, String originalLanguage,
                 String title, String backdropPath, Double popularity, Integer voteCount,
                 Boolean video, Double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Movie{");
        sb.append("posterPath='").append(posterPath).append('\'');
        sb.append(", adult=").append(adult);
        sb.append(", overview='").append(overview).append('\'');
        sb.append(", releaseDate='").append(releaseDate).append('\'');
        sb.append(", genreIds=").append(genreIds == null ? "null" : Arrays.asList(genreIds).toString());
        sb.append(", id=").append(id);
        sb.append(", originalTitle='").append(originalTitle).append('\'');
        sb.append(", originalLanguage='").append(originalLanguage).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", backdropPath='").append(backdropPath).append('\'');
        sb.append(", popularity=").append(popularity);
        sb.append(", voteCount=").append(voteCount);
        sb.append(", video=").append(video);
        sb.append(", voteAverage=").append(voteAverage);
        sb.append('}');
        return sb.toString();
    }
}
