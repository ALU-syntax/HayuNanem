package com.Kelompok1.android.hayunanem.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class TanamanId implements Serializable {
    @Exclude
    public String TanamanId;

    public TanamanId() {
    }

    public <T extends TanamanId> T withId (@NonNull final String id){
        this.TanamanId = id;
        return (T) this;
    }

    public String getTanamanId() {
        return TanamanId;
    }

    public void setTanamanId(String tanamanId) {
        TanamanId = tanamanId;
    }
}
