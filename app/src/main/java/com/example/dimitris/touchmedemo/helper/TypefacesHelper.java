package com.example.dimitris.touchmedemo.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

public class TypefacesHelper {

	private static final String TAG = TypefacesHelper.class.getSimpleName();

	public static final String FONT = "fonts/RobotoCondensed-Regular.ttf";
	public static final String FONT_BOLD = "fonts/RobotoCondensed-Bold.ttf";
	public static final String FONT_THIN = "fonts/Roboto-Thin.ttf";
	public static final String FONT_LIGHT = "fonts/RobotoCondensed-Light.ttf";
	public static final String FONT_OS_B = "fonts/Oswald-Bold.ttf";
	public static final String FONT_OS_R = "fonts/Oswald-Regular.ttf";
	public static final String FONT_VR_R = "fonts/VarelaRound-Regular.otf";
	public static final String FONT_SF_R = "fonts/SF-UI-Text-Regular.otf";
	public static final String FONT_SF_B = "fonts/SF-UI-Text-Bold.otf";
	public static final String FONT_PT_B = "fonts/PT-Sans-Bold.ttf";

	private static final Hashtable<String, Typeface> mTypefaces = new Hashtable<String, Typeface>();

	public static Typeface get(Context c, String assetPath) {
		synchronized (mTypefaces) {
			if (!mTypefaces.containsKey(assetPath)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							assetPath);
					mTypefaces.put(assetPath, t);
				} catch (Exception e) {
					Log.e(TAG, "Could not get typeface '" + assetPath
							+ "' because " + e.getMessage());
					return null;
				}
			}
			return mTypefaces.get(assetPath);
		}
	}
}
