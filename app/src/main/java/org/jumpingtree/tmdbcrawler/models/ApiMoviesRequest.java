package org.jumpingtree.tmdbcrawler.models;


import java.util.List;

public class ApiMoviesRequest {

    private Integer page;
    private List<Movie> movies;
    private Integer totalResults;
    private Integer totalPages;
    private String statusMessage;
    private Integer statusCode;

    public ApiMoviesRequest() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ApiMoviesRequest{");
        sb.append("page=").append(page);
        sb.append(", movies=").append(movies);
        sb.append(", totalResults=").append(totalResults);
        sb.append(", totalPages=").append(totalPages);
        sb.append(", statusMessage='").append(statusMessage).append('\'');
        sb.append(", statusCode=").append(statusCode);
        sb.append('}');
        return sb.toString();
    }
}
