package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.presenter.callback.LoginCallback
import mx.gob.fondofuturo.siscambpmovil.presenter.implementation.LoginPresenter
import mx.gob.fondofuturo.siscambpmovil.view.dialog.CustomDialogs

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

    override fun onLoading(message: String) {
        mProgress = CustomDialogs.sweetLoading(this, message)
        mProgress!!.show()
    }

    override fun onSuccess(user: User) {
        mProgress!!.dismissWithAnimation()
        val intent = Intent(this, ArrendatarioActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
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
