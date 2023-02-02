package me.sudodios.particlerainapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
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
            binding.laySeeks.updatePadding(left = 0, right = 0, top = 0, bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED
        }


        binding.colorSeekbar.onColorChanged = {
            binding.particleView.color = it
        }
        binding.speedSeekbar.addOnChangeListener { _, fl, _ ->
            binding.particleView.speed = fl
        }
        binding.maxCountSeekbar.addOnChangeListener { _, fl, _ ->
            binding.particleView.maxCount = fl.toInt()
        }
    }
}