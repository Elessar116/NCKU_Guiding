package com.example.miles.nckuguiding.GL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;

import com.example.miles.nckuguiding.LoaderNCalculater.VectorCal;
import com.example.miles.nckuguiding.MainActivity;
import com.example.miles.nckuguiding.Models.AllCampusData;
import com.example.miles.nckuguiding.Models.Campus;
import com.example.miles.nckuguiding.Models.Model;

import org.artoolkit.ar.base.rendering.RenderUtils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by miles on 2015/7/21.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    //controls
    private float range = 1200;

    //light 0
    private float[] ambientLight = new float[]{0f, 0f, 0f, 1.0f};
    private float[] diffuseLight = new float[]{0.35f, 0.35f, 0.35f, 1.0f};
    private float[] lightPos = new float[]{250, -300, 600, 0.0f};
    //light 1
    private float[] lightPos1 = new float[]{-250, +300, -600, 0.0f};

    private float mAngleX = 0;
    private float mAngleY = 0;
    private float mDistX = 0;
    private float mDistY = 0;
    private float mDistZ = 0;

    //personal posi
    private float[] per_posi = new float[]{0, 0};

    public MyGLRenderer() {
        super();
        Log.d("Render", "Build");
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //Render settings
        gl.glEnable(GL10.GL_CULL_FACE);
        //gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glFrontFace(GL10.GL_CCW);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        //Light setting and bind buffer
        FloatBuffer ambientBuffer = RenderUtils.buildFloatBuffer(ambientLight);
        FloatBuffer diffuseBuffer = RenderUtils.buildFloatBuffer(diffuseLight);
        FloatBuffer lightPosBuffer = RenderUtils.buildFloatBuffer(lightPos);

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

    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float rate = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 30, rate, 1f, 10000);
    }


    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(223 / 255.0f, 1, 1, 1.0f);
        //
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();
        load_matrix(gl);
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
        if (MainActivity.stop_info) {
            AllCampusData.stop.draw(gl);
            AllCampusData.stop_sign.draw(gl);
        }

        per_posi = VectorCal.getMapPosition(AllCampusData.gpsTracker.getLatitude(), AllCampusData.gpsTracker.getLongitude());
        gl.glTranslatef(per_posi[0] + AllCampusData.map_zero[0], per_posi[1] + AllCampusData.map_zero[1], 0);
        AllCampusData.loco.draw(gl);

        gl.glPopMatrix();

        load_matrix(gl);
        if (MainActivity.navi) {
            gl.glTranslatef(AllCampusData.test.loactions[2 * MainActivity.navi_num], AllCampusData.test.loactions[2 * MainActivity.navi_num + 1], 0);
            AllCampusData.navi.draw(gl);
        }

    }

    private void load_matrix(GL10 gl) {
        //drawing Matrix
        //World
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, mDistZ);

        //Model
        gl.glTranslatef(0, 0, -range);
        gl.glRotatef(-50, 1, 0, 0);
        gl.glRotatef(mAngleY, 0, 0, 1);
        gl.glTranslatef(mDistX, mDistY, 0);
        gl.glTranslatef(-AllCampusData.map_zero[0], -AllCampusData.map_zero[1], 0);
    }

    void setUserPosition(float[] position) {
        per_posi = position;
        mDistX = -per_posi[0];
        mDistY = -per_posi[1];
    }

    void setTIdentity() {
        mDistX = 0;
        mDistY = 0;
        mDistZ = 0;
    }

    void setRIdendity() {
        mAngleX = 0;
        mAngleY = 0;
    }

    void setAngleX(float angle) {
        mAngleX += angle;

    }

    void setAngleY(float angle) {
        mAngleY += angle;
        if (Math.abs(mAngleY) > 360) {
            mAngleY -= 360;
        }

        if (Math.abs(mAngleY) <= 0) {
            mAngleY += 360;
        }

    }

    void setDist(float in_x, float in_y) {
        float[] temp = new float[16];
        Matrix.setIdentityM(temp, 0);
        Matrix.rotateM(temp, 0, -mAngleY, 0, 0, 1);
        float[] result = new float[]{in_x, in_y, 0, 1};
        Matrix.multiplyMV(result, 0, temp, 0, result, 0);

        mDistX += result[0];
        mDistY += result[1];
    }

    void setZoom(float in_z) {
        mDistZ += in_z;
    }
}
