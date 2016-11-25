package com.example.miles.nckuguiding.LoaderNCalculater;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by miles on 2015/9/23.
 */
public class FileReader {
    //STL binary reader
    public float[] ReadStlBinary(String fileName, Context context) {
        float[] ospVert = new float[0];

        AssetManager am = context.getAssets();
        InputStream inputStream;
        try {
            inputStream = am.open(fileName);
            int count;
            byte[] buffer = new byte[84];
            inputStream.read(buffer);
            count = ByteBuffer.wrap(buffer, 80, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();

            ospVert = new float[count * 9];
            buffer = new byte[50 * count];
            inputStream.read(buffer);
            int num1 = 0;
            int num2 = 0;

            for (int Line = 0; Line < count; Line++) {
                ByteBuffer temp = ByteBuffer.wrap(buffer, num2 + 12, 36).order(ByteOrder.LITTLE_ENDIAN);
                for (int jjj = 0; jjj < 9; jjj++) {
                    ospVert[num1 + jjj] = temp.getFloat();
                }
                num1 += 9;
                num2 += 50;
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new float[]{Float.NaN};
        } catch (IOException e) {
            e.printStackTrace();
            return new float[]{Float.NaN};
        }

        return ospVert;
    }
}
