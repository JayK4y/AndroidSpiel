package com.basteldroid.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakob on 11.06.2015.
 */
public class Pool {

    public interface PoolObjectFactory {
        public T createObject();
    }

    private final List freeObjects;
    private final PoolObjectFactory factory;
    private final int maxSize;

    public Pool(PoolObjectFactory factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList(maxSize);
    }

    public T newObject() {
        T object = null;

        if (freeObjects.size() == 0)
            object = factory.createObject();
        else
            object = freeObjects.remove(freeObjects.size() - 1);

        return object;
    }

    public void free(T object) {
        if (freeObjects.size() &lt; maxSize)
        freeObjects.add(object);
    }
}
