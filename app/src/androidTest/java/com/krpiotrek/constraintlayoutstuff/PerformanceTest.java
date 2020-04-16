package com.krpiotrek.constraintlayoutstuff;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
        // Warn up, make the result more accurate
        getLayoutTime(R.layout.activity_main);

        // Benchmark
        final BMTime linearLayoutTime = getLayoutTime(R.layout.item_old_linear);
        final BMTime relativeLayoutTime = getLayoutTime(R.layout.item_old_relative);
        final BMTime constraintLayoutTime = getLayoutTime(R.layout.item_new);

        Log.i("time", "Linear    :\t" + linearLayoutTime);
        Log.i("time", "Relative  :\t" + relativeLayoutTime);
        Log.i("time", "Constraint:\t" + constraintLayoutTime);
    }

    private BMTime getLayoutTime(int layoutRes) {
        final LayoutInflater layoutInflater = LayoutInflater.from(InstrumentationRegistry.getInstrumentation().getTargetContext());

        long inflateTime = 0;
        long measureTime = 0;
        long layoutTime = 0;
        for (int i = 0; i < 1000; i++) {
            TimeCounter.start();
            final View view = layoutInflater.inflate(layoutRes, null);
            inflateTime += TimeCounter.count();

            view.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
            view.measure(View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            final int measuredHeight = view.getMeasuredHeight();
            final int measuredWidth = view.getMeasuredWidth();
            measureTime += TimeCounter.count();

            view.layout(0, 0, measuredWidth, measuredHeight);
            layoutTime += TimeCounter.count();
        }
        return new BMTime(inflateTime, measureTime, layoutTime);
    }

    static class TimeCounter {
        private static long time;

        static void start() {
            time = System.nanoTime();
        }

        static long count() {
            long now = System.nanoTime();
            long count = now - time;
            time = now;
            return count;
        }
    }

    static class BMTime {
        long inflateTime;
        long measureTime;
        long layoutTime;

        BMTime(long inflateTime, long measureTime, long layoutTime) {
            this.inflateTime = inflateTime;
            this.measureTime = measureTime;
            this.layoutTime = layoutTime;
        }

        @NonNull
        @Override
        public String toString() {
            return "inflate:\t" + inflateTime +
                    "\tmeasure:\t" + measureTime +
                    "\tlayout:\t" + layoutTime +
                    "\ttotal:\t" + (inflateTime + measureTime + layoutTime);
        }
    }
}
