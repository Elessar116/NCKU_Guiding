package com.example.miles.nckuguiding;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.miles.nckuguiding.AR.MyARRenderer;

import org.artoolkit.ar.base.ARActivity;
import org.artoolkit.ar.base.rendering.ARRenderer;

public class ARMAPActivity extends ARActivity {

    FrameLayout arlayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armap);
        arlayout = (FrameLayout) findViewById(R.id.aRLayout);
    }

    @Override
    protected ARRenderer supplyRenderer() {
        return new MyARRenderer(ARMAPActivity.this);
    }

    @Override
    protected FrameLayout supplyFrameLayout() {
        return arlayout;
    }
}
