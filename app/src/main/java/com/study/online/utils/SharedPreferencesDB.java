package com.study.online.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.support.v7.internal.VersionUtils;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 配置数据库
 * 
 * @author Administrator
 * 
 */
public class SharedPreferencesDB {

	/** 配置数据库名称 */
	private static final String CARNATION = "Carnation";

	private Context context;

	private static SharedPreferencesDB instance;

	private SharedPreferences preferences;

	public synchronized static SharedPreferencesDB getInstance(Context context) {
		if (instance == null) {
			instance = new SharedPreferencesDB(context);
		}
		return instance;
	}

	private SharedPreferencesDB(Context context) {
		this.context = context;
		preferences = this.context.getSharedPreferences(
				CARNATION + VersionUtils.isAtLeastL(), 0);
	}

	public void setString(String param, String value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(param, value);
		editor.commit();
	}

	public String getString(String param, String defvalue) {
		return preferences.getString(param, defvalue);
	}

	public void setFloat(String param, float value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putFloat(param, value);
		editor.commit();
	}

	public float getFloat(String param, float defvalue) {
		return preferences.getFloat(param, defvalue);
	}

	public void setLong(String param, long value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(param, value);
		editor.commit();
	}

	public long getLong(String param, long defvalue) {
		return preferences.getLong(param, defvalue);
	}

	public void setBoolean(String param, boolean value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(param, value);
		editor.commit();
	}

	public boolean getBoolean(String param, boolean defvalue) {
		return preferences.getBoolean(param, defvalue);
	}



	/**
	 * 储存图片
	 * 
	 *
	 */
	public void disposeImage(String userPhonenum, Bitmap bitmap) {
		ByteArrayOutputStream outputStream = null;
		try {
			SharedPreferences.Editor editor = preferences.edit();
			outputStream = new ByteArrayOutputStream();
			/*
			 * 读取和压缩图片资源 并将其保存在 ByteArrayOutputStream对象中
			 */
			bitmap.compress(CompressFormat.JPEG, 50, outputStream);
			String imgBase64 = new String(Base64.encode(
					outputStream.toByteArray(), Base64.DEFAULT));
			editor.putString(userPhonenum, imgBase64);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取图片
	 *
	 */
	public Drawable readImage(String userPhonenum) {
		ByteArrayInputStream inputStream = null;
		Drawable drawable = null;
		try {
			String imgbase64 = preferences.getString(userPhonenum, "");
			byte[] imgbyte = Base64
					.decode(imgbase64.getBytes(), Base64.DEFAULT);
			inputStream = new ByteArrayInputStream(imgbyte);
			drawable = Drawable.createFromStream(inputStream, "image");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return drawable;
	}
}
