package com.example.practicaroom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaroom.singeltoon.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log
import com.example.practicaroom.databinding.ActivityPokemonApiBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.withContext

class PokemonAPI : AppCompatActivity() {
    private lateinit var binding: ActivityPokemonApiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPokemonApiBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.txtName.setText("")
        binding.txtId.setText("")

        binding.btnBuscar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val pokemonName = RetrofitInstance.api.getPokemons(binding.editText.text.toString().toLowerCase())
                    withContext(Dispatchers.Main) {
                        Picasso.get().load(pokemonName.sprites.front_default).into(binding.imgView)
                        binding.txtName.text = pokemonName.name.toString().toUpperCase()
                        binding.txtId.text = "#" + pokemonName.id.toString()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }
}