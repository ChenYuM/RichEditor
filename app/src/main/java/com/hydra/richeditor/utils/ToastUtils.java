package com.hydra.richeditor.utils;

import android.text.TextUtils;
import android.widget.Toast;
import com.hydra.richeditor.common.AppContext;

public class ToastUtils {
	
	private static Toast toast = null;

	public static void toast(String text){
		toast(text, Toast.LENGTH_SHORT);
	}

	public static void toastLong(String text){
		toast(text, Toast.LENGTH_LONG);
	}

	public static void toast(String text, int length){
		if(TextUtils.isEmpty(text)){
			return;
		}
		if(toast != null){
			toast.cancel();
		}
		toast = Toast.makeText(AppContext.getContext(), text, length);
		toast.show();
	}


}