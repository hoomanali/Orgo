package com.example.orgo.views.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orgo.homepage.TileAdapter;
import com.google.firebase.database.core.Context;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the home page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}