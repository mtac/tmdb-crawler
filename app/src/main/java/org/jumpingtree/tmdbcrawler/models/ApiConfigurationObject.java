package org.jumpingtree.tmdbcrawler.models;


import java.util.List;

public class ApiConfigurationObject {

    private String baseUrl;
    private String secureBaseUrl;
    private List<String> backdropSizes;
    private List<String> logoSizes;
    private List<String> posterSizes;
    private List<String> profileSizes;
    private List<String> stillSizes;
    private List<String> changeKeys;

    public ApiConfigurationObject() {
    }

    public ApiConfigurationObject(String baseUrl, String secureBaseUrl, List<String> backdropSizes,
                                  List<String> logoSizes, List<String> posterSizes,
                                  List<String> profileSizes, List<String> stillSizes,
                                  List<String> changeKeys) {
        this.baseUrl = baseUrl;
        this.secureBaseUrl = secureBaseUrl;
        this.backdropSizes = backdropSizes;
        this.logoSizes = logoSizes;
        this.posterSizes = posterSizes;
        this.profileSizes = profileSizes;
        this.stillSizes = stillSizes;
        this.changeKeys = changeKeys;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secureBaseUrl = secureBaseUrl;
    }

    public List<String> getBackdropSizes() {
        return backdropSizes;
    }

    public void setBackdropSizes(List<String> backdropSizes) {
        this.backdropSizes = backdropSizes;
    }

    public List<String> getLogoSizes() {
        return logoSizes;
    }

    public void setLogoSizes(List<String> logoSizes) {
        this.logoSizes = logoSizes;
    }

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public void setPosterSizes(List<String> posterSizes) {
        this.posterSizes = posterSizes;
    }

    public List<String> getProfileSizes() {
        return profileSizes;
    }

    public void setProfileSizes(List<String> profileSizes) {
        this.profileSizes = profileSizes;
    }

    public List<String> getStillSizes() {
        return stillSizes;
    }

    public void setStillSizes(List<String> stillSizes) {
        this.stillSizes = stillSizes;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ApiConfigurationObject{");
        sb.append("baseUrl='").append(baseUrl).append('\'');
        sb.append(", secureBaseUrl='").append(secureBaseUrl).append('\'');
        sb.append(", backdropSizes=").append(backdropSizes);
        sb.append(", logoSizes=").append(logoSizes);
        sb.append(", posterSizes=").append(posterSizes);
        sb.append(", profileSizes=").append(profileSizes);
        sb.append(", stillSizes=").append(stillSizes);
        sb.append(", changeKeys=").append(changeKeys);
        sb.append('}');
        return sb.toString();
    }
}
