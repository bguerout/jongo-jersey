package com.xebia.bigdata.data;

public class Stat {
    public String _id;
    public int nbTags;

    //jackson
    private Stat() {
    }

    public Stat(String _id, int nbTags) {
        this._id = _id;
        this.nbTags = nbTags;
    }
}
