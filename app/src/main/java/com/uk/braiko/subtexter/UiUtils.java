package com.uk.braiko.subtexter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UiUtils {
	public static void unbindDrawables(View view, final boolean agressive) {
		if (view instanceof ViewGroup)
		{
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
			{
				unbindDrawables(((ViewGroup) view).getChildAt(i), agressive);
			}
			if (!AdapterView.class.isInstance(view))
			{
				((ViewGroup) view).removeAllViews();
			}
		}
		else
		{
			try
			{
				Drawable bmp = view.getBackground();
				if (bmp == null && ImageView.class.isInstance(view))
					bmp = ((ImageView) view).getDrawable();
				if (bmp != null)
				{
					bmp.setCallback(null);
					if (agressive && (Drawable.class.isInstance(bmp)))
					{
						try
						{
							Bitmap bm = ((BitmapDrawable) bmp).getBitmap();
							if (bm != null)
							{
								bm.recycle();
								bm = null;
								view.destroyDrawingCache();
								view = null;
							}
						}
						catch (Exception e)
						{
							view.destroyDrawingCache();
						}
					}
				}
			}
			catch (Exception e)
			{
			}
		}
	}

	public static AnimationSet getMoveScaleAnimation(View _from, View _to, int _duration) {
		int[] mCcooFrom = new int[2];
		_from.getLocationOnScreen(mCcooFrom);

		int[] mCcooTo = new int[2];
		_to.getLocationOnScreen(mCcooTo);

		TranslateAnimation mMove = new TranslateAnimation(0, mCcooTo[0] - mCcooFrom[0], 0, mCcooTo[1] - mCcooFrom[1]);
		mMove.setStartOffset(0);
		mMove.setFillAfter(true);
		mMove.setDuration(_duration);

		float[] mScaleFrom = new float[2];
		mScaleFrom[0] = _from.getWidth();
		mScaleFrom[1] = _from.getHeight();

		float[] mScaleTo = new float[2];
		mScaleTo[0] = _to.getWidth();
		mScaleTo[1] = _to.getHeight();

		ScaleAnimation mScale = new ScaleAnimation(1, mScaleTo[0] / mScaleFrom[0], 1, mScaleTo[1] / mScaleFrom[1], 0.5f, 0.5f);
		mScale.setStartOffset(0);
		mScale.setFillAfter(true);
		mScale.setDuration(_duration);

		AnimationSet mReturnAnimation = new AnimationSet(true);
		mReturnAnimation.setFillAfter(true);
		mReturnAnimation.setZAdjustment(AnimationSet.ZORDER_TOP);
		mReturnAnimation.addAnimation(mScale);
		mReturnAnimation.addAnimation(mMove);

		return mReturnAnimation;
	}

	public static void releaseView(View view) {
		try
		{
			if (view != null)
			{
				view.removeCallbacks(null);
				view.destroyDrawingCache();
				view = null;
			}
		}
		catch (Exception e)
		{
		}
	}

	public static int dipToPx(Context _activity, int _dip) {
		Resources r = _activity.getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _dip, r.getDisplayMetrics());
		return px;
	}

	public static void hideKeyboard(Activity _activity, EditText _view) {
		// hide keyboard
		InputMethodManager imm = (InputMethodManager) _activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(_view.getWindowToken(), 0);
	}

	public static void share(Activity _activity, String subject, String text) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		_activity.startActivity(intent);
	}

	public static void overrideFonts(final Context context, final View v, Typeface _font, int _style) {
		try
		{
			if (v instanceof ViewGroup)
			{
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0; i < vg.getChildCount(); i++)
				{
					View child = vg.getChildAt(i);
					overrideFonts(context, child, _font, _style);
				}
			}
			else if (v instanceof TextView)
			{
				((TextView) v).setTypeface(_font, _style);
			}
			else if (v instanceof Button)
			{
				((Button) v).setTypeface(_font, _style);
			}
			else if (v instanceof EditText)
			{
				((EditText) v).setTypeface(_font, _style);
			}

		}
		catch (Exception e)
		{
		}
	}

	public static void disableUiControls(final Context context, final View v) {
		try
		{
			if (v instanceof ViewGroup)
			{
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0; i < vg.getChildCount(); i++)
				{
					View child = vg.getChildAt(i);
					disableUiControls(context, child);
				}
			}
			else if (v instanceof TextView || v instanceof Button || v instanceof EditText || v instanceof CheckBox)
			{
				v.setEnabled(false);
			}
		}
		catch (Exception e)
		{
		}
	}

}
