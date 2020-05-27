package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.presenter.callback.LoginCallback
import mx.gob.fondofuturo.siscambpmovil.presenter.implementation.LoginPresenter
import mx.gob.fondofuturo.siscambpmovil.support.Permissions
import mx.gob.fondofuturo.siscambpmovil.view.dialog.ArrendatarioFilterDialog
import mx.gob.fondofuturo.siscambpmovil.view.dialog.CustomDialogs
import mx.gob.fondofuturo.siscambpmovil.view.dialog.ServerConfigDialog

class LoginActivity : BaseActivity(), LoginCallback {

    private var mPresenter: LoginPresenter? = null
    private var mProgress: SweetAlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = LoginPresenter(this, this)
        loginButton.setOnClickListener {
            mPresenter!!.onLoginRx(
                editInputUser.text.toString(),
                editInputPassword.text.toString()
            )
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
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

            else -> super.onContextItemSelected(item)
        }
    }

    override fun onLoading(message: String) {
        mProgress = CustomDialogs.sweetLoading(this, message)
        mProgress!!.show()
    }

    override fun onSuccess(user: User) {
        mProgress!!.dismissWithAnimation()
        val intent = Intent(this, ArrendatarioActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }

    override fun onError(message: String) {
        mProgress!!.dismissWithAnimation()
        CustomDialogs.sweetError(this, message)
    }

    override fun onDestroy() {
        mPresenter!!.onDispose()
        super.onDestroy()
    }
}
