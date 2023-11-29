package com.example.rutastecmadero

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.rutastecmadero.databinding.ActivityPrincipalBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class ChatbotFragment : Fragment() {

    private lateinit var mic: FloatingActionButton
    private lateinit var entrada: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatbot, container, false)
    }

    override fun onStart() {
        super.onStart()
        entrada = requireView().findViewById(R.id.entradaTexto) as EditText
        mic = requireView().findViewById(R.id.mic) as FloatingActionButton
        mic.setOnClickListener{
            recognizeVoice()
        }
    }
    private fun recognizeVoice(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        if(intent.resolveActivity(requireActivity().packageManager) != null){
            startActivityForResult(intent,1)
        } else{
            Toast.makeText(requireActivity().baseContext,"Su dispostivo no admite entrada por voz",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            entrada.setText(result?.get(0).toString())
        }
    }
}