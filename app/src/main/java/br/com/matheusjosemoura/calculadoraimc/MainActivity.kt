package br.com.matheusjosemoura.calculadoraimc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obter o dado passado pela intent
        val email = intent.getStringExtra("INTENT_EMAIL")

        // Abrindo o arquivo de Shared Preferences
        val sharedPrefs = getSharedPreferences("cadastro_$email", Context.MODE_PRIVATE)

        // Recuperar os dados do Shared Preferences
        val name = sharedPrefs.getString("NAME", "")
        val lastName = sharedPrefs.getString("LAST_NAME", "")

        // Exibindo as informações obtidas para o usuário
        textViewHello.text = getString(R.string.hello) + ", " + name + " " + lastName

        // Botão sair da aplicação
        imageViewLogout.setOnClickListener {
            //Criando um alerta
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.attention))
                .setMessage(getString(R.string.logout_confirmation))
                .setPositiveButton(getString(R.string.yes)){ _, _ ->
                    //Executando o clique do botão Sim
                    val mIntent = Intent(this, LoginActivity::class.java)
                    startActivity(mIntent)
                    //Retirando todas as telas do empilhamento
                    finishAffinity()
                }
                .setNeutralButton(getString(R.string.cancel)){ _, _ ->}
                .setCancelable(false)
                .create()
                .show()
        }

        // Botão calcular IMC
        buttonCalculate.setOnClickListener {

            // Obter os dados
            val height = editTextNumberDecimalHeight.text.toString().trim()
            val weight = editTextNumberDecimalWeight.text.toString().trim()

            // Validação dos campos
            if (height.isEmpty() || weight.isEmpty()) {
                Toast.makeText(this, getString(R.string.is_empty), Toast.LENGTH_LONG).show()
            }
            else {
                // Obtendo a referencia de edição de arquivo
                val editPrefs = sharedPrefs.edit()

                // Preparar os dados a serem salvos
                editPrefs.putString("HEIGHT", height)
                editPrefs.putString("WEIGHT", weight)

                // Salvar os dados no arquivo de Shared Preferences
                editPrefs.apply()

                val mIntent = Intent(this, ResultActivity::class.java)

                // Passando dados através de Intents
                mIntent.putExtra("INTENT_EMAIL", email)

                startActivity(mIntent)

                // Tirar todas as telas emplihadas anteriormente desta
                finish()
            }
        }

        // Botão limpar dados
        fun clearData() {
            editTextNumberDecimalHeight.text = null;
            editTextNumberDecimalWeight.text = null;
            editTextNumberDecimalHeight.requestFocus();
            Toast.makeText(this, getString(R.string.fields_clear), Toast.LENGTH_LONG).show()
        }
        buttonClear.setOnClickListener {
            clearData();
        }

        //Mask EditText
        editTextNumberDecimalHeight.addTextChangedListener(MaskWatcher("#.##"));
        editTextNumberDecimalWeight.addTextChangedListener(MaskWatcher("###.##"));

    }
}