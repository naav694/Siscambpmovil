package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.toolbar.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.databinding.ActivityLoginBinding
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult
import mx.gob.fondofuturo.siscambpmovil.view.dialog.CustomDialogs
import mx.gob.fondofuturo.siscambpmovil.view.dialog.ServerConfigDialog
import mx.gob.fondofuturo.siscambpmovil.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()
    private var mProgress: SweetAlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(
            this,
            R.layout.activity_login
        )
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel
        setSupportActionBar(toolbar)

        initObservers()
    }

    private fun initObservers() {
        loginViewModel.onLogin.observe(this, Observer {
            when (it) {
                is LecturaResult.Loading -> {
                    mProgress = CustomDialogs.sweetLoading(this, it.message)
                    mProgress!!.show()
                }
                is LecturaResult.Success -> {
                    mProgress!!.dismissWithAnimation()
                    if (it.data.response!!.isEmpty()) {
                        val intent = Intent(this, ArrendatarioActivity::class.java)
                        intent.putExtra("user", it.data.user)
                        startActivity(intent)
                        finish()
                    } else {
                        mProgress!!.dismissWithAnimation()
                        CustomDialogs.sweetError(this, it.data.response!!)
                    }

                }
                is LecturaResult.Error -> {
                    mProgress!!.dismissWithAnimation()
                    CustomDialogs.sweetError(this, it.error)
                }
            }
        })
        loginViewModel.onToast.observe(this, Observer {
            it.let {
                Toast.makeText(this, "¡Configure datos del servidor primero!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnServerConfig -> {
                val fragmentManager = supportFragmentManager
                ServerConfigDialog.show(fragmentManager, "server_config")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
