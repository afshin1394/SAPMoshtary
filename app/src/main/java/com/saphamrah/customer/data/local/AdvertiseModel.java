package com.saphamrah.customer.data.local;


import java.io.Serializable;

public class AdvertiseModel  implements Serializable {
    private String imageUrl;
    private String link;

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
}
