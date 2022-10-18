package com.hydra.richeditor.common;

import android.app.Application;


public class AppContext extends Application{

	private static AppContext mContext = null;

	public static AppContext getContext(){
		return mContext;
	}

	/**创建应用*/
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}

}