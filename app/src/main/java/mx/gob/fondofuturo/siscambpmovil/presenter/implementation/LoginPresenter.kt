package mx.gob.fondofuturo.siscambpmovil.presenter.implementation

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mx.gob.fondofuturo.siscambpmovil.model.repository.LoginRepository
import mx.gob.fondofuturo.siscambpmovil.presenter.callback.LoginCallback

class LoginPresenter(context: Context, private val mCallback: LoginCallback?) :
    BasePresenter(context) {

    fun onLoginRx(user: String, password: String) {
        disposable =
            Observable.fromCallable { LoginRepository.onLogin(context!!, user, password) }
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { mCallback!!.onLoading("Iniciando sesión") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.response!!.isEmpty()) {
                        mCallback!!.onSuccess(it.data)
                    } else {
                        mCallback!!.onError(it.response!!)
                    }
                }
                ) {
                    mCallback!!.onError(it.message!!)
                }
    }
}