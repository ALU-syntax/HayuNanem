package com.Kelompok1.android.hayunanem.Model;

import java.io.Serializable;
import java.util.Date;

public class Tanaman extends TanamanId implements Serializable {

    private String tempat, jenis, user;
    private Date time;

    public Tanaman() {
    }

    public Tanaman(String tempat, String jenis) {
        this.tempat = tempat;
        this.jenis = jenis;
    }

    public Tanaman(String tempat, String jenis, String user) {
        this.tempat = tempat;
        this.jenis = jenis;
        this.user = user;
    }

    public Tanaman(String tempat, String jenis, String user, Date time) {
        this.tempat = tempat;
        this.jenis = jenis;
        this.user = user;
        this.time = time;
    }

    public Tanaman(String tempat, String jenis, Date time) {
        this.tempat = tempat;
        this.jenis = jenis;
        this.time = time;
    }

    public String getTempat() {
        return tempat;
    }

    public String getJenis() {
        return jenis;
    }

    public Date getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
