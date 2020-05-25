package mx.gob.fondofuturo.siscambpmovil.presenter.implementation

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mx.gob.fondofuturo.siscambpmovil.model.repository.ArrendatarioRepository
import mx.gob.fondofuturo.siscambpmovil.presenter.callback.ArrendatarioCallback

class ArrendatarioPresenter(context: Context, private val mCallback: ArrendatarioCallback?) :
    BasePresenter(context) {

    fun getArrendatarios(manzana: String) {
        disposable =
            Observable.fromCallable { ArrendatarioRepository.getArrendatarios(context!!, manzana) }
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { mCallback!!.onLoading("Cargando arrendatarios") }
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