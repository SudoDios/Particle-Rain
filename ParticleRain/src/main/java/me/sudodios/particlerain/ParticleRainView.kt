package me.sudodios.particlerain

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

class ParticleRainView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val particleEffect = ParticleEffect(context)

    var color : Int = Color.WHITE
        set(value) {
            field = value
            particleEffect.color = field
        }

    var maxCount : Int = 100
        set(value) {
            field = value
            particleEffect.maxCount = field
        }

    var speed : Float = 1f
        set(value) {
            field = value
            particleEffect.speed = field
        }

    fun setParticleDrawableResId (resId : Int) {
        particleEffect.drawableId = resId
    }

    init {
        attrs?.let {
            with(context.obtainStyledAttributes(it, R.styleable.ParticleRainView)) {
                color = getColor(R.styleable.ParticleRainView_prv_color,Color.WHITE)
                maxCount = getInt(R.styleable.ParticleRainView_prv_maxCount,100)
                speed = getFloat(R.styleable.ParticleRainView_prv_speed,1f)
                setParticleDrawableResId(getResourceId(R.styleable.ParticleRainView_prv_res,R.drawable.snow))
                recycle()
            }
        }
        particleEffect.initiate()
    }

    override fun onDraw(canvas: Canvas) {
        particleEffect.onDraw(this,canvas)
    }

}