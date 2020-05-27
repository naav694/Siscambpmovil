package mx.gob.fondofuturo.siscambpmovil.presenter.implementation

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mx.gob.fondofuturo.siscambpmovil.model.data.Lectura
import mx.gob.fondofuturo.siscambpmovil.model.repository.LecturaRepository
import mx.gob.fondofuturo.siscambpmovil.presenter.callback.LecturaCallback

class LecturaPresenter(context: Context, private val mCallback: LecturaCallback?) :
    BasePresenter(context) {

    fun sendLecturaRx(lectura: Lectura) {
        disposable =
            Observable.fromCallable { LecturaRepository.sendLecturaToWeb(context!!, lectura) }
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { mCallback!!.onLoading("Enviando lectura") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        mCallback!!.onSuccess()
                    } else {
                        mCallback!!.onError(it)
                    }
                }
                ) {
                    mCallback!!.onError(it.message!!)
                }
    }
}