package ru.kpfu.kazanguide.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectOutputCache {

    private Context context;
    private String fileName;

    public ObjectOutputCache(String fileName, Context context) {
        this.context = context;
        this.fileName = fileName;
    }

    public <T> void cacheWriter(List<T> list) {

        File cacheFile = new File(context.getCacheDir(), fileName);
        ObjectOutputStream writer;
        try {
            writer = new ObjectOutputStream(new FileOutputStream(cacheFile));
            writer.writeObject(list);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
