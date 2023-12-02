package com.symatechlabs.defaultsmsapp

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.symatechlabs.defaultsmsapp.databinding.MainActivityBinding

class MainActivityMVC(inflater: LayoutInflater, parent: ViewGroup?, application: AppCompatActivity) : MainActivityInterface {

    var rootView: View;
    var application: AppCompatActivity;
    var mainActivityBinding: MainActivityBinding;

    init {
        mainActivityBinding = MainActivityBinding.inflate(inflater);
        rootView = mainActivityBinding.root;
        this.application = application;
    }

    override fun getRootView_(): View {
        return rootView;
    }

    override fun getContext(): Context {
        return rootView.context;
    }

    override fun setListeners() {
        mainActivityBinding.enableDefaultSMSApp.setOnClickListener{
            if(!areWeTheDefaultMessagingApp()){
                requestDefaultSmsAppSelection();
            }
        }
    }



    override fun areWeTheDefaultMessagingApp(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val packageName = application.packageName;
            val smsPackage = Telephony.Sms.getDefaultSmsPackage(application)
            packageName == smsPackage
        } else {
            // Below KitKat, the OS doesn't allow you to set a default app
            true
        }
    }

    override fun requestDefaultSmsAppSelection() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            val roleManager = application.getSystemService(RoleManager::class.java);
            if (roleManager.isRoleAvailable(RoleManager.ROLE_SMS)) {
                if (roleManager.isRoleHeld(RoleManager.ROLE_SMS)) {
                    Toast.makeText(
                        application,
                        "DefaultSMSApp set as default.",
                        Toast.LENGTH_SHORT
                    ).show()
                    application.startActivity(Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS))

                } else {
                    application.startActivityForResult(roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS), 2)
                }
            }

        } else {
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, application.packageName)
            application.startActivity(intent)
        }
    }
}