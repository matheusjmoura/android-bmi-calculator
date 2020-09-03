package br.com.matheusjosemoura.calculadoraimc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var bmi: Float? = null

        //Set Enable_False para o compo de exibicao do resultado
        val mEdit = findViewById<EditText>(R.id.editTextPersonBMI)
        mEdit.isEnabled = false

        // Obter o dado passado pela intent
        val email = intent.getStringExtra("INTENT_EMAIL")

        // Abrindo o arquivo de Shared Preferences
        val sharedPrefs = getSharedPreferences("cadastro_$email", Context.MODE_PRIVATE)

        // Recuperar os dados do Shared Preferences
        val height = sharedPrefs.getString("HEIGHT", "")?.toFloat()
        val weight = sharedPrefs.getString("WEIGHT", "")?.toFloat()

        //String to Editable
        fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

        // Calcular o BMI
        fun calcBMI(height: Float, weight: Float) {
            bmi = (weight/(height*height))
            editTextPersonBMI.text = bmi.toString().toEditable()
        }
        if (height != null && weight != null) {
            calcBMI(height, weight)
        }

        // Listener (Ouvinte) do botão salvar
        buttonSave.setOnClickListener {

            // Obter os dados
            val currentTime: Date = Calendar.getInstance().getTime()

            // Obtendo o StringSet
            val hs = sharedPrefs.getStringSet("STRING_SET", HashSet())
            hs!!.add(currentTime.toString())

            // Obtendo a referencia de edição de arquivo
            val editPrefs = sharedPrefs.edit()

            // Preparar os dados a serem salvos
            editPrefs.clear()
            editPrefs.putStringSet("STRING_SET", hs)
            editPrefs.putString("BMI", bmi.toString())

            // Salvar os dados no arquivo de Shared Preferences
            editPrefs.apply()

            // Abrir a tela MainActivity
            val mIntent = Intent(this, MainActivity::class.java)

            // Passando dados através de Intents
            mIntent.putExtra("INTENT_EMAIL", email)

            startActivity(mIntent)

            finish()
        }

        // Botão descartar IMC
        buttonDiscard.setOnClickListener {
            //Criando um alerta
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.attention))
                .setMessage(getString(R.string.discard_confirmation))
                .setPositiveButton(getString(R.string.yes)){ _, _ ->
                    //Executando o clique do botão Sim
                    val mIntent = Intent(this, MainActivity::class.java)
                    mIntent.putExtra("INTENT_EMAIL", email)
                    startActivity(mIntent)
                    finish()
                }
                .setNeutralButton(getString(R.string.cancel)){ _, _ ->}
                .setCancelable(false)
                .create()
                .show()
        }

    }
}