package mx.gob.fondofuturo.siscambpmovil.util.interfaces

import mx.gob.fondofuturo.siscambpmovil.model.data.User

interface ISessionHelper {
    fun getUserSession(): User
    fun setUserSession(user: User)
    fun deleteUserSession()
    fun setRememberSession(remember: Boolean)
    fun getRememberSession(): Boolean
    fun setBaseURL(server: String)
    fun getBaseURL(): String
}