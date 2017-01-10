package com.bhuvanesh.talenthive.util;


import android.content.res.AssetManager;

import com.bhuvanesh.talenthive.THApplication;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class FileUtil {

    public  static <T> T getFromAssetsFolder(String filename, Class<T> clazz, Type type) {
        AssetManager manager = THApplication.getInstance().getAssets();
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        T object = null;
        try {
            // 1. Open a file, with reader object
            reader = new BufferedReader(new InputStreamReader(manager.open(filename)));

            // 2. Read the file, & get json string
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line);

            // 3. using json string, parse to desired type
            if (type != null)
                object = new Gson().fromJson(String.valueOf(builder), type);
            else
                object = new Gson().fromJson(String.valueOf(builder), clazz);

        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        } finally {
            // 4. close the resource if any
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }
}
