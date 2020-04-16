package com.krpiotrek.constraintlayoutstuff;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class PerformanceTest {

    @Test
    public void test() {
        final long relativeLayoutTime = getLayoutTime(R.layout.item_old_relative);
        final long linearLayoutTime = getLayoutTime(R.layout.item_old_linear);
        final long constraintLayoutTime = getLayoutTime(R.layout.item_new);

        Log.i("time", "constraint : " + constraintLayoutTime);
        Log.i("time", "linear : " + linearLayoutTime);
        Log.i("time", "relative : " + relativeLayoutTime);
    }

    private long getLayoutTime(int layoutRes) {
        final LayoutInflater layoutInflater = LayoutInflater.from(InstrumentationRegistry.getInstrumentation().getTargetContext());

        final long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            final View view = layoutInflater.inflate(layoutRes, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(0, 0));

            view.measure(View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            final int measuredHeight = view.getMeasuredHeight();
            final int measuredWidth = view.getMeasuredWidth();

            view.layout(0, 0, measuredWidth, measuredHeight);
        }
        return System.nanoTime() - startTime;
    }
}
