package com.example.bill.opengltest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private GLSurfaceView mEffectView;

    private TextureRenderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        renderer = new TextureRenderer();
        renderer.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.bill));
        renderer.setCurrentEffect(R.id.none);

        mEffectView = (GLSurfaceView) findViewById(R.id.effectsview);
        mEffectView.setEGLContextClientVersion(2);
        mEffectView.setRenderer(renderer);
        mEffectView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("info", "menu create");
        setMenuItemTextColorToWhite(MainActivity.this);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("info", "menu getItemId====" + item.getItemId());
        renderer.setCurrentEffect(item.getItemId());
        mEffectView.requestRender();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 修改 ActionBar 上的菜单字体颜色
     */
    public static void setMenuItemTextColorToWhite(final Activity activity){
        activity.getLayoutInflater().setFactory(new LayoutInflater.Factory() {

            @Override
            public View onCreateView(String name, Context context,
                                     AttributeSet attrs) {
                if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
                        || name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")) {
                    try {
                        LayoutInflater f = activity.getLayoutInflater();
                        final View view = f.createView(name, null, attrs);
                        System.out.println((view instanceof TextView));
                        if (view instanceof TextView) {
                            ((TextView) view).setTextColor(Color.WHITE/*这里修改颜色*/);
                        }
                        return view;
                    } catch (InflateException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

        });
    }
}