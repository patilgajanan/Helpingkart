package com.appsplanet.helpingkart.Activity.DrawerFragments.Home;

/**
 * Created by surajk44437 on 8/12/2017.
 */

public class HomeItem {
    String id, img, title;

    public HomeItem(String id, String img, String title) {
        this.id = id;
        this.img = img;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
