package br.com.matheusjosemoura.calculadoraimc

import android.content.Context
import android.content.Intent
import android.graphics.Color.WHITE
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Criando uma lista de opções para o Spinner
        val genderList = arrayListOf(
            getString(R.string.select_gender), getString(R.string.female), getString(
                R.string.male
            )
        )

        // Criando um adaptador para o spinner
        val genderAdapter = ArrayAdapter(
            this, //Contexto
            android.R.layout.simple_spinner_dropdown_item, //Layout
            genderList // Dados
        )

        // Linkar o adaptador no Spinner
        spinnerGender.adapter = genderAdapter

        // Listener (ouvinte) do spinner / mudar cor itemSelected
        val listener: OnItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                (parent.getChildAt(0) as TextView).setTextColor(WHITE)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinnerGender.setOnItemSelectedListener(listener)

        // Listener (Ouvinte) do botão cadastrar
        buttonAddRegister.setOnClickListener {

            // Obter os dados digitados pelo usuário
            val name = editTextTextPersonNameRegister.text.toString().trim()
            val lastName = editTextTextPersonLastNameRegister.text.toString().trim()
            val email = editTextTextEmailAddressRegisterRegister.text.toString().trim().lowercase()
            val password = editTextTextPasswordRegister.text.toString().trim()
            val gender = spinnerGender.selectedItem.toString()

            // Validação dos campos
            if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                password.isEmpty() || gender == genderList[0]) {
                Toast.makeText(this, getString(R.string.is_empty), Toast.LENGTH_LONG)
                    .show()
            }
            else {
                // Neste ponto, todos os campos foram preenchidos corretamente
                val sharedPrefs = getSharedPreferences(
                    "cadastro_$email",
                    Context.MODE_PRIVATE
                )

                // Obtendo a referencia de edição de arquivo
                val editPrefs = sharedPrefs.edit()

                // Preparar os dados a serem salvos
                editPrefs.putString("NAME", name)
                editPrefs.putString("LAST_NAME", lastName)
                editPrefs.putString("EMAIL", email)
                editPrefs.putString("PASSWORD", password)
                editPrefs.putString("GENDER", gender)

                // Salvar os dados no arquivo de Shared Preferences
                editPrefs.apply()

                // Abrir a tela MainActivity
                val mIntent = Intent(this, MainActivity::class.java)

                // Passando dados através de Intents
                mIntent.putExtra("INTENT_EMAIL", email)

                startActivity(mIntent)

                // Tirar todas as telas emplihadas anteriormente desta
                finishAffinity()
            }

        }
    }
}