package com.rx.testviewdraghelpdemo;

import android.view.animation.Interpolator;

/**
 * Author:XWQ
 * Time   2018/11/13
 * Descrition: this is HesitateInterpolator
 */

public class HesitateInterpolator implements Interpolator
{

    public HesitateInterpolator() {}

    @Override
    public float getInterpolation(float input) {
        float x = 2.0f * input - 0.8f;
        return 0.5f * (x * x * x + 1.0f);
    }
}
