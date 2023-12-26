package com.example.ezhomeservice.model;

import android.net.Uri;

public class PortfolioModel {
    Uri image ;
    boolean isLoading;

    public PortfolioModel(Uri image) {
        this.image = image;
        this.isLoading = false;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
