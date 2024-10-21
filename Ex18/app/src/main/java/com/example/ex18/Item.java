package com.example.ex18;

public class Item {
    private String maso;
    private String tieude;
    private Integer thich;

    public Item(String maso, String tieude, Integer thich) {
        this.maso = maso;
        this.tieude = tieude;
        this.thich = thich;
    }

    public String getMaso() {
        return maso;
    }

    public String getTieude() {
        return tieude;
    }

    public Integer getThich() {
        return thich;
    }
}
