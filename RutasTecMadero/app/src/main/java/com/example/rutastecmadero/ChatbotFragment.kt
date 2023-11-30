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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.BotResponse
import com.example.data.Constants.RECEIVE_ID
import com.example.data.Constants.SEND_ID
import com.example.data.Message
import com.example.data.MessagingAdapter
import com.example.data.Time
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_chatbot.entradaTexto
import kotlinx.android.synthetic.main.fragment_chatbot.rv_messages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class ChatbotFragment : Fragment() {
    private lateinit var adapter : MessagingAdapter
    private lateinit var mic: FloatingActionButton
    private lateinit var send: FloatingActionButton
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
        send = requireView().findViewById(R.id.send) as FloatingActionButton

        recyclerView()

        mic.setOnClickListener{
            recognizeVoice()
        }

        send.setOnClickListener{
            sendMessage()
        }

        entrada.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount-1)
                }
            }
        }

        customMessage("Bienvenido a la aplicación Rutas Tec. Madero, mi nombre es Victor, espero ser de su ayuda, sugerencias: comandos Ayuda o Información")

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount-1)
                }
            }
    }

    @Suppress("DEPRECATION")
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

    private fun recyclerView(){
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
    }

    private fun sendMessage(){
        val message = entrada.text.toString()
        val timeStamp = Time.timeStamp()
        if(message.isNotEmpty()){
            entrada.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)

            botResponse(message)
        }
    }

    private fun customMessage(message:String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message,RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

    private fun botResponse(message : String){
        val timeStamp = Time.timeStamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponses(message)
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
}