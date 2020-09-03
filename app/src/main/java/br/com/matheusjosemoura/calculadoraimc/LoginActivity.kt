package br.com.matheusjosemoura.calculadoraimc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.buttonAddRegister
import kotlinx.android.synthetic.main.activity_register.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Listener (ouvinte) do botão Entrar
        buttonLogin.setOnClickListener {
            // Obtendo os dados digitados peleo usuário
            val email = editTextTextEmailAddress.text.toString().trim()
            val password = editTextTextPassword.text.toString().trim()

            // Verificando se os campos estão vazios
            if (email.isEmpty()) {
                editTextTextEmailAddress.error = getString(R.string.required_field)
                editTextTextEmailAddress.requestFocus()
            }
            else if (password.isEmpty()) {
                editTextTextPassword.error = getString(R.string.required_field)
                editTextTextPassword.requestFocus()
            }
            else {
                // Acessar o arquivo Shared Preferences
                val sharedPrefs = getSharedPreferences("cadastro_$email", Context.MODE_PRIVATE)

                //Recuperar os dados no arquivo Shared Preferences
                val emailPrefs = sharedPrefs.getString("EMAIL", "")
                val passwordPrefs = sharedPrefs.getString("SENHA", "")

                if (email == emailPrefs && password == passwordPrefs ) {
                    // Caso a senha e o email esteja correta

                    // Exibindo o Toast (alerta curto) para o usuário logado
                    Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_LONG).show()

                    // Criar a intent para ir para a Tela Main
                    val mIntent = Intent(this, MainActivity::class.java)

                    // Iniciar activity
                    startActivity(mIntent)

                    // Encerrar a Activity
                    finish()
                }
                else {
                    // Caso a senha ou o email esteja errado
                    Toast.makeText(this, getString(R.string.email_password_wrong), Toast.LENGTH_LONG).show()

                }
            }
        }

        // Listener (ouvinte) do botão Registrar
        buttonAddRegister.setOnClickListener {
            // Abrir a tela de cadastro
            val mIntent = Intent(this, RegisterActivity::class.java)
            startActivity(mIntent)
        }
    }
}