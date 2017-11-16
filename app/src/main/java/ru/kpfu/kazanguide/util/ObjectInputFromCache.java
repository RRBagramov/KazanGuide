package ru.kpfu.kazanguide.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputFromCache {
    private Context context;
    private String fileName;

    public ObjectInputFromCache(String fileName, Context context) {
        this.context = context;
        this.fileName = fileName;
    }

    public Object cacheReader() {
        Object object;
        File cachePath = context.getCacheDir();
        File cacheFile = new File(cachePath, fileName);

        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(cacheFile));
            object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
