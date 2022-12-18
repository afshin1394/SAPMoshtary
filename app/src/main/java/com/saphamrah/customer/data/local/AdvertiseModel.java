package com.saphamrah.customer.data.local;


import java.io.Serializable;

public class AdvertiseModel  implements Serializable {
    private String imageUrl;
    private String link;
    private String title;
    private String codeKala;
    private int ccSazmanforosh;
    private String sazmanForosh;

    public AdvertiseModel(String imageUrl, String link, String title, String codeKala, int ccSazmanforosh, String sazmanForosh) {
        this.imageUrl = imageUrl;
        this.link = link;
        this.title = title;
        this.codeKala = codeKala;
        this.ccSazmanforosh = ccSazmanforosh;
        this.sazmanForosh = sazmanForosh;
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

    public String getCodeKala() {
        return codeKala;
    }

    public void setCodeKala(String codeKala) {
        this.codeKala = codeKala;
    }

    public int getCcSazmanforosh() {
        return ccSazmanforosh;
    }

    public void setCcSazmanforosh(int ccSazmanforosh) {
        this.ccSazmanforosh = ccSazmanforosh;
    }

    public String getSazmanForosh() {
        return sazmanForosh;
    }

    public void setSazmanForosh(String sazmanForosh) {
        this.sazmanForosh = sazmanForosh;
    }
}
