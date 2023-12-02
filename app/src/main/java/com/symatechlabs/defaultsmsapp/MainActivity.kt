package com.symatechlabs.defaultsmsapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var mainActivityMVC: MainActivityMVC;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityMVC = MainActivityMVC(LayoutInflater.from(this), null, this);
        setContentView(mainActivityMVC.getRootView_());
        mainActivityMVC.setListeners();
    }

}