package com.saphamrah.customer.data.local;


import java.io.Serializable;

public class AdvertiseModel  implements Serializable {
    private String imageUrl;
    private String link;
    private String title;

    public AdvertiseModel(String imageUrl, String link, String title) {
        this.imageUrl = imageUrl;
        this.link = link;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
