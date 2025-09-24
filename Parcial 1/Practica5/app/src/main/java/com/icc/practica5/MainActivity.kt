package com.icc.practica5

import android.media.browse.MediaBrowser
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.icc.practica5.databinding.ActivityMainBinding
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private var audioPlayer : ExoPlayer? = null
    private var videoPlayer : ExoPlayer? = null
    private var options = listOf("Imagen","Audio","Video","Texto")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            options).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(options[position]){
                    "Imagen" -> loadMedia("image")
                    "Audio" -> loadMedia("audio")
                    "Video" -> loadMedia("video")
                    "Texto" -> loadMedia("text")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
    }

    private fun setStatus(msg: String){ binding.tvStatus.text = msg}

    private fun showOnly(viewToShow: View?){
        binding.ivImage.visibility = View.GONE
        binding.playerViewVideo.visibility = View.GONE
        binding.scrollText.visibility = View.GONE
        viewToShow?.visibility = View.VISIBLE
    }

    private fun loadMedia(type: String){
        relasePlayers()
        setStatus("Buscando '$type' en Back4app")

        val query = ParseQuery.getQuery<ParseObject>("Media")
        query.whereEqualTo("type", type).setLimit(1)
        query.getFirstInBackground { obj, e ->
            if(e != null || obj == null){
                setStatus("No se encontro nada")
                return@getFirstInBackground
            }
            val file = obj.getParseFile("file")

            if(file == null){
                setStatus("Campo file vacio")
                return@getFirstInBackground
            }

            val url = file.url
            when(type){
                "image" -> showImage(url)
                "audio" -> playAudio(url)
                "video" -> playVideo(url)
                "text" -> loadText(file)
            }
        }
    }

    private fun showImage(url: String?){
        if(url == null){
            setStatus("URL vacia")
            return
        }
        showOnly(binding.ivImage)
        Glide.with(this).load(url).into(binding.ivImage)
        setStatus("Imagen cargada")
    }

    private fun playVideo(url: String?){
        if(url == null){
            setStatus("URL Vacia.")
            return
        }
        showOnly(binding.playerViewVideo)
        videoPlayer = ExoPlayer.Builder(this).build().also{ p ->
            binding.playerViewVideo.player = p
            p.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
            p.prepare()
            p.playWhenReady = true
        }
        setStatus("Reproduciendo video")
    }

    private fun playAudio(url: String?){
        if(url == null){
            setStatus("URL Vacia.")
            return
        }
        showOnly(null)
        audioPlayer = ExoPlayer.Builder(this).build().also { p ->
            p.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
            p.prepare()
            p.playWhenReady = true
        }
        setStatus("Reproduciendo audio")
    }

    private fun relasePlayers(){
        audioPlayer?.release(); audioPlayer = null
        videoPlayer?.release(); videoPlayer = null
        binding.playerViewVideo.player = null
    }

    private fun loadText(file: ParseFile){
        setStatus("Descargando texto")
        file.getDataInBackground { data, e ->
            if(e != null || data == null){
                setStatus("Error al cargar")
                return@getDataInBackground
            }
            binding.tvText.text = data.toString(Charsets.UTF_8)
            showOnly(binding.scrollText)
            setStatus("Texto cargado")
        }
    }

    override fun onStop() {
        super.onStop()
        audioPlayer?.playWhenReady = false
        videoPlayer?.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        relasePlayers()
    }
}