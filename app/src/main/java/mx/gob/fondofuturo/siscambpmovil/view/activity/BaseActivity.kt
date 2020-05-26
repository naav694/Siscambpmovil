package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : AppCompatActivity() {

    val STORAGE_REQUEST = 1000
    val PHOTOS_REQUEST = 100
    val CAMERA_REQUEST = 10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setSupportActionBar(toolbar)
    }

    abstract fun getLayoutId(): Int
}