package com.juegospichurria.qbox.android;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.juegospichurria.qbox.qbox;

public class AndroidLauncher extends AndroidApplication {
	
	AdView publicidad ;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.resolutionStrategy = new FillResolutionStrategy();
	
		RelativeLayout layout = new RelativeLayout(this);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		

		View juego = this.initializeForView(new qbox(),config);
		
		publicidad = new AdView(this);
		publicidad.setAdSize(AdSize.BANNER);
		publicidad.setAdUnitId("ca-app-pub-6261045080177785/6794045754");
		
		 AdRequest adRequest = new AdRequest.Builder().build();
		 publicidad.loadAd(adRequest);
		 
		 layout.addView(juego);
		 
		 RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		 adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		layout.addView(publicidad,adParams);
		
		publicidad.setVisibility(View.VISIBLE);
		
		setContentView(layout);
		
		
	}
}
