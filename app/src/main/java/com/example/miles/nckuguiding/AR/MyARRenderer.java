package com.example.miles.nckuguiding.AR;

import android.content.Context;
import android.util.Log;

import com.example.miles.nckuguiding.LoaderNCalculater.VectorCal;
import com.example.miles.nckuguiding.MainActivity;
import com.example.miles.nckuguiding.Models.AllCampusData;
import com.example.miles.nckuguiding.Models.Campus;
import com.example.miles.nckuguiding.Models.Model;

import org.artoolkit.ar.base.ARToolKit;
import org.artoolkit.ar.base.rendering.Cube;
import org.artoolkit.ar.base.rendering.RenderUtils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * A very simple Renderer that adds a marker and draws a cube on it.
 */
public class MyARRenderer extends org.artoolkit.ar.base.rendering.ARRenderer {

    private Context mContext;

    //Marker
    private int markerID = -1;


    //light 0
    private float[] ambientLight = new float[]{0f, 0f, 0f, 1.0f};
    private float[] diffuseLight = new float[]{0.2f, 0.2f, 0.2f, 1.0f};
    private float[] lightPos = new float[]{150.0f, -200.0f, 200, 0.0f};
    //light 1
    private float[] lightPos1 = new float[]{-150.0f, +200.0f, -200, 0.0f};

    //matrix
    private float[] aRMatrix = new float[16];
    private float[] per_posi = new float[]{0, 0};

    /**
     * Markers can be configured here.
     */
    public MyARRenderer(Context context) {
        super();
        mContext = context;
        Log.d("Render", "Build");
    }

    @Override
    public boolean configureARScene() {
        markerID = ARToolKit.getInstance().addMarker("single;Data/marker16.pat;240");
        if (markerID < 0) return false;
        return true;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        super.onSurfaceChanged(gl, w, h);

        FloatBuffer ambientBuffer = RenderUtils.buildFloatBuffer(ambientLight);
        FloatBuffer diffuseBuffer = RenderUtils.buildFloatBuffer(diffuseLight);
        FloatBuffer lightPosBuffer = RenderUtils.buildFloatBuffer(lightPos);

        // Apply the ARToolKit projection matrix
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glFrontFace(GL10.GL_CCW);

        //light 0, 1
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambientBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, diffuseBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosBuffer);

        lightPosBuffer = RenderUtils.buildFloatBuffer(lightPos1);
        gl.glEnable(GL10.GL_LIGHT1);
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambientBuffer);
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, diffuseBuffer);
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseBuffer);
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosBuffer);

        gl.glEnable(GL10.GL_COLOR_MATERIAL);

        //
    }

    @Override
    public void draw(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadMatrixf(ARToolKit.getInstance().getProjectionMatrix(), 0);

        gl.glMatrixMode(GL10.GL_MODELVIEW);

        if (ARToolKit.getInstance().queryMarkerVisible(markerID)) {
            gl.glLoadIdentity();

            aRMatrix = ARToolKit.getInstance().queryMarkerTransformation(markerID);
            gl.glMultMatrixf(aRMatrix, 0);
            AllCampusData.loco.draw(gl);

            per_posi = VectorCal.getMapPosition(AllCampusData.gpsTracker.getLatitude(), AllCampusData.gpsTracker.getLongitude());
            gl.glTranslatef(-per_posi[0] - AllCampusData.map_zero[0], -per_posi[1] - AllCampusData.map_zero[1], 0);
            if (AllCampusData.test.isLoaded()) {
                AllCampusData.test.draw(gl);
            }
            if (AllCampusData.ck.isLoaded()) {
                AllCampusData.ck.draw(gl);
            }
            if (AllCampusData.sl.isLoaded()) {
                AllCampusData.sl.draw(gl);
            }
            if (AllCampusData.kf.isLoaded()) {
                AllCampusData.kf.draw(gl);
            }
            if(MainActivity.stop_info){
                AllCampusData.stop.draw(gl);
                AllCampusData.stop_sign.draw(gl);
            }
        }
    }
}