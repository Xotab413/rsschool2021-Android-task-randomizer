package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements RandomizerMainActivity {
    private static OpenedFragment currentFragment = OpenedFragment.FIRST;
    private static int previous = 0;
    private static int min = 0;
    private static int max = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(previous);
    }

    @Override
    public void firstFragmentSaveState(int min, int max) {
        MainActivity.min = min;
        MainActivity.max = max;
    }

    @Override
    public void secondFragmentSaveState(int previous) {
        MainActivity.previous = previous;
    }

    @Override
    public void openSecondFragment(int min, int max) {
        currentFragment = OpenedFragment.SECOND;
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment);
        transaction.commit();
    }

    @Override
    public void openFirstFragment(int previous) {
        currentFragment = OpenedFragment.FIRST;
        final Fragment firstFragment = FirstFragment.newInstance(previous);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (currentFragment == OpenedFragment.SECOND) {
            openFirstFragment(previous);
        } else {
            super.onBackPressed();
        }
    }

    private enum OpenedFragment {
        FIRST, SECOND
    }
}
