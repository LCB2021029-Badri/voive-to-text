package com.example.voice_text

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import com.example.voice_text.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    //request code constant
    private val RQ_SPEECH_REC = 69

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //
        binding.btnButton.setOnClickListener {
            askSpeechInput()
        }
    }

    //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.tvText.text = result?.get(0).toString()
        }
    }

    //
    private fun askSpeechInput(){
        if(!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this,"speech recognition is not available",Toast.LENGTH_SHORT).show()
        }
        else{
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())   // select any language here(here we used mobile default language)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Listening...")
            startActivityForResult(intent,RQ_SPEECH_REC)

        }
    }
}