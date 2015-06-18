package com.basteldroid.framework.implementation;
import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.basteldroid.framework.Pool;
import com.basteldroid.framework.Input.TouchEvent;
import com.basteldroid.framework.Pool.PoolObjectFactory;

public class SingleTouchHandler implements TouchHandler {
    boolean isTouched;
    int touchX;
    int touchY;
    Pool touchEventPool;
    List touchEvents = new ArrayList();
    List touchEventsBuffer = new ArrayList();
    float scaleX;
    float scaleY;

    public SingleTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory factory = new PoolObjectFactory() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized(this) {
            TouchEvent touchEvent = touchEventPool.newObject();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchEvent.type = TouchEvent.TOUCH_DOWN;
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    touchEvent.type = TouchEvent.TOUCH_UP;
                    isTouched = false;
                    break;
            }

            touchEvent.x = touchX = (int)(event.getX() * scaleX);
            touchEvent.y = touchY = (int)(event.getY() * scaleY);
            touchEventsBuffer.add(touchEvent);

            return true;
        }
    }

    @Override
    public boolean isTouchDown(int pointer) {
        synchronized(this) {
            if(pointer == 0)
                return isTouched;
            else
                return false;
        }
    }

    @Override
    public int getTouchX(int pointer) {
        synchronized(this) {
            return touchX;
        }
    }

    @Override
    public int getTouchY(int pointer) {
        synchronized(this) {
            return touchY;
        }
    }

    @Override
    public List getTouchEvents() {
        synchronized(this) {
            int len = touchEvents.size();
            for( int i = 0; i &lt; len; i++ )
            touchEventPool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }
}
