package mx.gob.fondofuturo.siscambpmovil.presenter.implementation

import android.app.Activity
import android.content.Context
import io.reactivex.disposables.Disposable

open class BasePresenter(context: Context) {

    var disposable: Disposable? = null
    var context: Context? = null

    init {
        this.context = context
    }

    fun onDispose() {
        disposable?.dispose()
    }
}