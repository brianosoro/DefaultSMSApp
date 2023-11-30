package com.symatechlabs.defaultsmsapp

import android.content.Context
import android.view.View

interface MainActivityInterface {

     fun getRootView_(): View;
     fun getContext(): Context;
     fun setListeners();

     fun areWeTheDefaultMessagingApp(): Boolean
     fun requestDefaultSmsAppSelection();
}