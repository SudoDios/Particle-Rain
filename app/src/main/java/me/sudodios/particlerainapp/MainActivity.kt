package me.sudodios.particlerainapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePaddingRelative
import me.sudodios.particlerainapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.laySeeks.updatePaddingRelative(bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED
        }


        binding.colorSeekbar.onColorChanged = {
            binding.particleView.color = it
        }
        binding.speedSeekbar.addOnChangeListener { slider, fl, b ->
            binding.particleView.speed = fl
        }
        binding.maxCountSeekbar.addOnChangeListener { slider, fl, b ->
            binding.particleView.maxCount = fl.toInt()
        }
    }
}