package com.example.semana09dpafirebase.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semana09dpafirebase.R
import com.example.semana09dpafirebase.adapter.PokemonAdapter
import com.example.semana09dpafirebase.model.PokemonModel
import com.example.semana09dpafirebase.model.PokemonModelDetails
import com.example.semana09dpafirebase.model.PokemonModelResponse
import com.example.semana09dpafirebase.service.ApiService
import com.example.semana09dpafirebase.service.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonFragment : Fragment() {
    private lateinit var pokemonAdapter: PokemonAdapter
    private var pokemonList: MutableList<PokemonModel> = mutableListOf()
    private var filteredList: MutableList<PokemonModel> = mutableListOf()
    private var OFFSET: Int = 0
    private var TOTAL_ITEMS: Int = 0
    private var LIMIT_ROWS: Int = 20
    private lateinit var loadingDialog: ProgressDialog
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_pokemon, container, false)

        val etSearchPokemon: EditText = view.findViewById(R.id.etSearchPokemon)
        val btnSearchPokemon: Button = view.findViewById(R.id.btnSearchPokemon)
        val rvPokemon: RecyclerView = view.findViewById(R.id.rvPokemon)

        rvPokemon.layoutManager = LinearLayoutManager(requireContext())
        pokemonAdapter = PokemonAdapter(pokemonList)
        rvPokemon.adapter = pokemonAdapter

        val retrofit = Retrofit
                        .Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val pokeApiService = retrofit.create(PokeApiService::class.java)

        fetchPokemonList(pokeApiService)

        return view
    }

    fun fetchPokemonList(pokeApiService: PokeApiService){
        pokeApiService.getPokemonList(OFFSET,LIMIT_ROWS)
            .enqueue(object : Callback<PokemonModelResponse>{
                override fun onResponse(
                    call: Call<PokemonModelResponse>,
                    response: Response<PokemonModelResponse>
                ) {
                    if(response.isSuccessful){
                        val pokemonResponse = response.body()
                        val newPokemonList = pokemonResponse?.results ?: emptyList()
                        TOTAL_ITEMS = pokemonResponse?.count ?: 0

                        newPokemonList.forEach{pokemon ->
                            pokeApiService.getPokemonDetails(pokemon.name)
                                .enqueue(object: Callback<PokemonModelDetails>{
                                    override fun onResponse(
                                        call: Call<PokemonModelDetails>,
                                        response: Response<PokemonModelDetails>
                                    ) {
                                        if(response.isSuccessful){
                                            val pokemonDetails = response.body()
                                            val imageUrl = pokemonDetails?.sprites?.frontDefault ?: ""
                                            val abilities = pokemonDetails?.abilities?.map{it.ability.name}?.toList() ?: emptyList()

                                            val pokemonModel = PokemonModel(pokemonDetails?.name ?: "", imageUrl, abilities)

                                            pokemonList.add(pokemonModel)
                                            pokemonAdapter.notifyDataSetChanged()
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<PokemonModelDetails>,
                                        t: Throwable
                                    ) {
                                        TODO("Not yet implemented")
                                    }

                                })
                        }
                        pokemonAdapter.setData(pokemonList)
                    }
                }

                override fun onFailure(call: Call<PokemonModelResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }



}