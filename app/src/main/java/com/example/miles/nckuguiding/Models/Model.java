package com.example.miles.nckuguiding.Models;

import android.content.Context;
import android.util.Log;

import com.example.miles.nckuguiding.LoaderNCalculater.FileReader;
import com.example.miles.nckuguiding.LoaderNCalculater.VectorCal;

import org.artoolkit.ar.base.rendering.RenderUtils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by miles on 2015/11/17.
 */
public class Model {

    FloatBuffer vertexBuffer;
    FloatBuffer colorBuffer;
    private FloatBuffer normalBuffer;

    //STL and render data
    float[] vertex;
    private float color[];

    //Loaded flag
    private boolean Loaded = false;

    public Model(String string, float alpha, Context context) {
        FileReader fileReader = new FileReader();
        vertex = fileReader.ReadStlBinary(string, context);

        if(!Float.isNaN(vertex[0])) {
            float[] normals = VectorCal.getNormByPtArray(vertex);
            setColor(new float[]{1.0f, 1.0f, 1.0f}, alpha);
            Log.v(string + " loaded: ", "Loaded");
            Loaded = true;

            vertexBuffer = RenderUtils.buildFloatBuffer(vertex);
            normalBuffer = RenderUtils.buildFloatBuffer(normals);
            colorBuffer = RenderUtils.buildFloatBuffer(color);

        }else {
            Log.v(string + "loaded E*: ", "UnLoaded");
        }
    }

    public Model(String string, float[] in_color, float alpha, Context context) {
        this(string, alpha, context);
        setColor(in_color, alpha);
    }

    public boolean isLoaded(){
        return Loaded;
    }

    protected void setColor(float[] in_color, float alpha){
        in_color = new float[]{in_color[0], in_color[1], in_color[2], alpha};
        color = new float[vertex.length/3*4];

        for (int i = 0; i < color.length; i++) {
            color[i] = in_color[i % 4];

        }

        colorBuffer = RenderUtils.buildFloatBuffer(color);
    }

    public void draw(GL10 gl) {

        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertex.length / 3);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

    }
}
