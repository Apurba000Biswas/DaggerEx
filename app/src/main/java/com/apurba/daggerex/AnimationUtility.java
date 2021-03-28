package com.apurba.daggerex;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationUtility {


    public static Animation getFadeOutAnimation(Context context){
        if (context != null){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
            animation.setDuration(1300);
            return animation;
        }
        return null;
    }


}
