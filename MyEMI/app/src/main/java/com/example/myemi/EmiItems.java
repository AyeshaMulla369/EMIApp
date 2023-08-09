package com.example.myemi;

public class EmiItems {
    String name;

    long sid;
    float p;
    float r;
    float n;

    public float getP() {
        return p;
    }

    public void setP(float p) {
        this.p = p;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getN() {
        return n;
    }

    public void setN(float n) {
        this.n = n;
    }

    public EmiItems(long sid , String name , float p, float n, float r) {
        this.name = name;
        this.sid = sid;
        this.p = p;
        this.n = n;
        this.r = r;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

