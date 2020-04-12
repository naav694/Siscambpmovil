package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.viewmodel.LoginViewModel

class LoginActivity : BaseActivity() {

    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.response.observe(this, Observer {
            println("DEBUG: $it")
        })

        loginButton.setOnClickListener(View.OnClickListener {
            loginViewModel.setUserPassword(User("sistema", "sistema"))
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.cancelJobs()
    }
}
