package me.sudodios.particlerain

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import java.security.SecureRandom
import java.util.*
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class ParticleEffect(private var context: Context) {

    var color : Int = Color.WHITE
        set(value) {
            field = value
            bitmapPaint.color = field
            bitmapPaint.colorFilter = PorterDuffColorFilter(field, PorterDuff.Mode.SRC_ATOP)
        }

    var maxCount : Int = 100
        set(value) {
            if (value in 20..300) {
                field = value
            }
        }

    var speed : Float = 500f
        set(value) {
            if (value in 0.02f..1f) {
                field = value * 500f
            }
        }

    var drawableId : Int = 0
        set(value) {
            if (field != value) {
                field = value
                particleBitmap = drawableToBitmap(ContextCompat.getDrawable(context,drawableId)!!)
            }
        }

    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = this@ParticleEffect.color
        colorFilter = PorterDuffColorFilter(this@ParticleEffect.color, PorterDuff.Mode.SRC_ATOP)
    }

    private var particleBitmap: Bitmap? = null
    private var lastAnimationTime: Long = 0

    private inner class Particle {
        var x = 0f
        var y = 0f
        var vx = 0f
        var vy = 0f
        var velocity = 0f
        var alpha = 0f
        var lifeTime = 0f
        var currentTime = 0f
        var scale = 0f
        fun draw(canvas: Canvas) {
            bitmapPaint.alpha = (255 * alpha).toInt()
            canvas.save()
            canvas.scale(scale, scale, x, y)
            canvas.drawBitmap(particleBitmap!!, x, y, bitmapPaint)
            canvas.restore()
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        var width = drawable.intrinsicWidth
        width = if (width > 0) width else 1
        var height = drawable.intrinsicHeight
        height = if (height > 0) height else 1
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private val particles = ArrayList<Particle>()
    private val freeParticles = ArrayList<Particle>()

    fun initiate () {
        for (a in 0..20) {
            freeParticles.add(Particle())
        }
    }

    private fun updateParticles(dt: Long) {
        var count = particles.size
        var a = 0
        while (a < count) {
            val particle = particles[a]
            if (particle.currentTime >= particle.lifeTime) {
                if (freeParticles.size < 40) {
                    freeParticles.add(particle)
                }
                particles.removeAt(a)
                a--
                count--
                a++
                continue
            }
            if (particle.currentTime < 200.0f) {
                particle.alpha = accelerateInterpolator.getInterpolation(particle.currentTime / 200.0f)
            } else {
                particle.alpha = 1.0f - decelerateInterpolator.getInterpolation((particle.currentTime - 200.0f) / (particle.lifeTime - 200.0f))
            }
            particle.x += particle.vx * particle.velocity * dt / speed
            particle.y += particle.vy * particle.velocity * dt / speed
            particle.currentTime += dt.toFloat()
            a++
        }
    }

    fun onDraw(prv: ParticleRainView, canvas: Canvas) {
        val count = particles.size
        for (a in 0 until count) {
            val particle = particles[a]
            particle.draw(canvas)
        }
        if (particles.size < maxCount && random.nextFloat() > 0.7f) {
            val cx = random.nextFloat() * prv.measuredWidth
            val cy = random.nextFloat() * (prv.measuredHeight - 20f.dp())
            val angle = random.nextInt(40) - 20 + 90
            val vx = cos(Math.PI / 180.0 * angle).toFloat()
            val vy = sin(Math.PI / 180.0 * angle).toFloat()
            val newParticle: Particle
            if (freeParticles.isNotEmpty()) {
                newParticle = freeParticles[0]
                freeParticles.removeAt(0)
            } else {
                newParticle = Particle()
            }
            newParticle.x = cx
            newParticle.y = cy
            newParticle.vx = vx
            newParticle.vy = vy
            newParticle.alpha = 0.0f
            newParticle.currentTime = 0f
            newParticle.scale = random.nextFloat() * 1.2f
            newParticle.lifeTime = 2000 + random.nextInt(100).toFloat()
            newParticle.velocity = 20.0f + random.nextFloat() * 4.0f
            particles.add(newParticle)
        }
        val newTime = System.currentTimeMillis()
        val dt = min(17, newTime - lastAnimationTime)
        updateParticles(dt)
        lastAnimationTime = newTime
        prv.invalidate()
    }
    
    companion object {
        var accelerateInterpolator = AccelerateInterpolator()
        var decelerateInterpolator = DecelerateInterpolator()
        var random = SecureRandom()

        private fun Float.dp() : Int = if(this == 0f) 0 else ceil((Resources.getSystem().displayMetrics.density * this).toDouble()).toInt()
    }
    
}