package mx.gob.fondofuturo.siscambpmovil.viewmodel

import androidx.lifecycle.ViewModel
import mx.gob.fondofuturo.siscambpmovil.support.interfaces.ISessionHelper

open class BaseViewModel(val sessionHelper: ISessionHelper) : ViewModel() {
}