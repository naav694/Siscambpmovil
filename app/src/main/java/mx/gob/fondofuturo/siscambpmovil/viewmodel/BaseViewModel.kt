package mx.gob.fondofuturo.siscambpmovil.viewmodel

import androidx.lifecycle.ViewModel
import mx.gob.fondofuturo.siscambpmovil.util.interfaces.ISessionHelper

open class BaseViewModel(val sessionHelper: ISessionHelper) : ViewModel() {
}