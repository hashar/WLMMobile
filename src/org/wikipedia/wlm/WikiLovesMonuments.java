package org.wikipedia.wlm;

import android.app.Activity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import org.apache.cordova.*;

public class WikiLovesMonuments extends DroidGap implements OnTouchListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.loadUrl("file:///android_asset/www/index.html");
        appView.setOnTouchListener(this);
    }

    private float zoomInitialDist, zoomFinalDist;
    private boolean zoomActive = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_POINTER_DOWN:
        	zoomActive = true;
        	zoomInitialDist = fingerDistance(event);
        	zoomFinalDist = zoomInitialDist;
        	Log.d("motion", "DOWN dist is " + zoomInitialDist);
        	return true;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        	if (zoomActive) {
        		float delta = zoomFinalDist - zoomInitialDist;
        		Log.d("motion", "UP final delta is " + delta);
        		zoomActive = false;
        		return true;
        	} else {
        		return false;
        	}
        case MotionEvent.ACTION_MOVE:
        	if (zoomActive) {
	        	zoomFinalDist = fingerDistance(event);
	        	Log.d("motion", "MOVE dist is " + zoomFinalDist);
	        	return true;
        	} else {
        		return false;
        	}
        }
        return false;
    }
    
    private float fingerDistance(MotionEvent event) {
    	float dx = event.getX(0) - event.getX(1),
    		dy = event.getY(0) - event.getY(1),
    		dist = FloatMath.sqrt(dx * dx + dy * dy);
    	return dist;
    }
}

