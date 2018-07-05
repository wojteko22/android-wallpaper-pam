package com.example.wojtek.wallpaper

import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.service.wallpaper.WallpaperService

class ExampleWallpaperService : WallpaperService() {

    private val handler = Handler()

    override fun onCreateEngine() = TheEngine()

    inner class TheEngine : WallpaperService.Engine() {
        private val runnable = Runnable { drawFrame() }
        private val x = 540f
        private val textXStart = -600f
        private val yStart = 450
        private val eyeOffset = 20f
        private val eyeSize = 10f
        private val mouthSize = 7f


        private var y = yStart
        private var textX = textXStart
        private var visible: Boolean = false
        private var direction = 1

        private fun drawFrame() {
            y += 10 * direction
            textX += 10

            val holder = surfaceHolder
            val paint = preparePaint()
            if (visible) {
                val canvas = holder.lockCanvas()
                canvas.drawARGB(255, 0, 50, 0)
                canvas.drawCircle(x, y.toFloat(), 50f, paint)
                canvas.drawCircle(x - eyeOffset, y - 20f, eyeSize, blackPaint)
                canvas.drawCircle(x + eyeOffset, y - 20f, eyeSize, blackPaint)

                canvas.drawCircle(x, y + 130f, 100f, paint)
                canvas.drawCircle(x, y + 90f, eyeSize, blackPaint)
                canvas.drawCircle(x, y + 150f, eyeSize, blackPaint)

                canvas.drawCircle(x, y + 340f, 150f, paint)
                canvas.drawCircle(x, y + 290f, eyeSize, blackPaint)
                canvas.drawCircle(x, y + 340f, eyeSize, blackPaint)
                canvas.drawCircle(x, y + 390f, eyeSize, blackPaint)

                canvas.drawCircle(x - eyeOffset, y + 15f, mouthSize, blackPaint)
                canvas.drawCircle(x + eyeOffset, y + 15f, mouthSize, blackPaint)
                canvas.drawCircle(x - eyeOffset / 2, y + 22f, mouthSize, blackPaint)
                canvas.drawCircle(x + eyeOffset / 2, y + 22f, mouthSize, blackPaint)
                canvas.drawCircle(x, y + 25f, mouthSize, blackPaint)

                canvas.drawText("Wesołych świąt!", textX, 350f, paint)
                if (y !in yStart..1050)
                    direction *= -1
                if (textX > 1100)
                    textX = textXStart
                holder.unlockCanvasAndPost(canvas)
                handler.postDelayed(runnable, 20)
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (this.visible)
                handler.post(runnable)
            else
                handler.removeCallbacks(runnable)

        }
    }
}

private fun preparePaint(): Paint {
    val paint = Paint()
    paint.color = Color.WHITE
    paint.isAntiAlias = true
    paint.strokeWidth = 2f
    paint.strokeCap = Paint.Cap.ROUND
    paint.style = Paint.Style.FILL
    paint.textSize = 80f
    return paint
}

private val blackPaint: Paint by lazy {
    val paint = Paint()
    paint.color = Color.BLACK
    paint.isAntiAlias = true
    paint.strokeWidth = 2f
    paint.strokeCap = Paint.Cap.ROUND
    paint.style = Paint.Style.FILL
    paint.textSize = 80f
    paint
}