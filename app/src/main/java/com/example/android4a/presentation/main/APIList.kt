package com.example.android4a.presentation.main

import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android4a.R
import com.example.android4a.data.repository.Pokemon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_list.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class APIList : AppCompatActivity() {

    private val client = OkHttpClient()
    private var mpoke = listOf<Pokemon>()
    private lateinit var adapter: ListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "PokeAndroid"
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_list)

        linearLayoutManager = LinearLayoutManager(this)
        list_recycler_view.layoutManager = linearLayoutManager


        //showList();
        var pokeList = listOf<Pokemon>()
        pokeList= getDataFromCache()


        if (pokeList.isNullOrEmpty()) {
            mpoke = run("https://pokeapi.co/api/v2/pokemon/")


        } else {
            adapter = ListAdapter(pokeList)



            runOnUiThread { list_recycler_view.adapter = adapter }
        }

    }

    fun run(url: String): List<Pokemon> {
        var check = 0

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                var responses = client.newCall(request).execute();
                val jsonData = responses.body()!!.string()
                val Jobject = JSONObject(jsonData)
                val Jarray = Jobject.getJSONArray("results")
                var tempName : String
                var tempSpecie : String
                var mpoke = listOf<Pokemon>()
                for (i in 0 until Jarray.length()) {
                    tempName = Jarray.getJSONObject(i).getString("name")
                    tempSpecie = Jarray.getJSONObject(i).getString("url")
                    mpoke += Pokemon(tempName,tempSpecie)
                }

                adapter = ListAdapter(mpoke)

                println(mpoke.size)
                saveList(mpoke)
                runOnUiThread { list_recycler_view.adapter = adapter }
                // Stuff that updates the UI

            }
        })

        return mpoke
    }

    private fun saveList(pokemonList: List<Pokemon>) {
        var gson = Gson()
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val jsonString: String = gson.toJson(pokemonList)
        sharedPref
            .edit()
            .putString(PREF_NAME, jsonString)
            .apply()

    }
    private fun getDataFromCache(): List<Pokemon> {
        sharedPref  = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val jsonPokemon = sharedPref.getString(PREF_NAME, null)
        var gson = Gson()
        return if (jsonPokemon == null) {

            return listOf<Pokemon>()
        } else {
            val listType =
                object : TypeToken<List<Pokemon>>() {}.type
            return gson.fromJson(jsonPokemon, listType)
        }
    }
}