package com.example.wojtek.wallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Marcin on 2017-01-08.
 */

public class ExampleWallpaperService extends WallpaperService {

    private final Handler handler = new Handler();


    @Override
    public Engine onCreateEngine() {
        return new TheEngine();
    }

    public class TheEngine extends WallpaperService.Engine {

        private int circleY = 100;

        private final Runnable mDrawFrame = new Runnable() {
            public void run() {
                drawFrame();
            }
        };
        private boolean isVisible;

        private int currentFrame = 0;
        private int[] frames = {
//                R.drawable.easter_animated_gif_bunny_behind_basket_1,
//                R.drawable.easter_animated_gif_bunny_behind_basket_2,
//                R.drawable.easter_animated_gif_bunny_behind_basket_3,
//                R.drawable.easter_animated_gif_bunny_behind_basket_4,
//                R.drawable.easter_animated_gif_bunny_behind_basket_5,
//                R.drawable.easter_animated_gif_bunny_behind_basket_6,
//                R.drawable.easter_animated_gif_bunny_behind_basket_7
        };
        private Bitmap mainBitmap;
        private float scaleX;
        private float scaleY;

        private void drawFrame() {
            final SurfaceHolder holder = getSurfaceHolder();

            mainBitmap = BitmapFactory.decodeResource(getResources(), frames[currentFrame]);
//             -- Napis

            final Paint paint = new Paint();
            paint.setColor(0xff99ff99);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(2);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            paint.setTextSize(80f);
            if (isVisible) {
                Canvas canvas = holder.lockCanvas();
                canvas.save();


                currentFrame++;
                if (currentFrame == 7){
                    currentFrame = 0;
                }
                Matrix matrix = new Matrix();
                canvas.scale(scaleX, scaleY);
//                canvas.drawBitmap(mainBitmap, matrix, null);

//                 Napis
                //wyrysuj coś prostego
                //zaysuj stare
                canvas.drawARGB(255,0,50,0);
                //canvas.getH
                circleY+=10;
                canvas.drawCircle(100,circleY,50,paint);
                canvas.drawText("FRANEK",100,circleY,paint);
                if(circleY>1000) circleY=100;

                canvas.restore();
                holder.unlockCanvasAndPost(canvas);
                //przygotowanie następnej ramki
                int frameDuration = 200;
                handler.postDelayed(mDrawFrame, frameDuration);
                //handler.post(mDrawFrame);
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.isVisible = visible;
            if (isVisible) {
                handler.post(mDrawFrame);
            } else {
                handler.removeCallbacks(mDrawFrame);
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height){
            super.onSurfaceChanged(holder, format, width, height);
            drawFrame();
            scaleX = width / (1f * mainBitmap.getWidth());
            scaleY = height / (1f * mainBitmap.getHeight());
        }

    }
}
