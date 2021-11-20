package com.example.antisocial;

public class postModel {
    private String postId;
    private String des;
    private String postImage;
    private String postedBy;
    private long postedAt;

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public postModel(String postId) {
        this.postId = postId;
    }


    public postModel(String postId, String des, String postImage, long postedAt) {
        this.postId = postId;
        this.des = des;
        this.postImage = postImage;
        this.postedAt = postedAt;
    }

    public postModel() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }
}
