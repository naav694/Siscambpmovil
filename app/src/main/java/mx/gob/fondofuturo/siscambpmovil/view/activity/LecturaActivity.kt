package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.FileProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_lectura.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.model.data.FotoLectura
import mx.gob.fondofuturo.siscambpmovil.model.data.Lectura
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.presenter.callback.LecturaCallback
import mx.gob.fondofuturo.siscambpmovil.presenter.implementation.LecturaPresenter
import mx.gob.fondofuturo.siscambpmovil.support.Convertions
import mx.gob.fondofuturo.siscambpmovil.support.Permissions
import mx.gob.fondofuturo.siscambpmovil.view.dialog.CustomDialogs
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class LecturaActivity : BaseActivity(), LecturaCallback {

    private var mPresenter: LecturaPresenter? = null
    private var mProgress: SweetAlertDialog? = null
    private var photoLectura: FotoLectura? = null
    private var nombreFoto: String? = null
    private var rutaArchivo: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Captura de lectura"
        val user = intent.extras!!.getParcelable<User>("user")
        val arrendatario = intent.extras!!.getParcelable<Arrendatario>("arrendatario")
        mPresenter = LecturaPresenter(this, this)
        initComponents(user!!, arrendatario!!)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_lectura
    }

    private fun initComponents(user: User, arrendatario: Arrendatario) {
        editLecturaAnt.setText(
            NumberFormat.getInstance(Locale.getDefault()).format(arrendatario.lecturaAnt)
        )
        btnEnviarLectura.setOnClickListener {
            val lecturaAct = editLecturaAct.text.toString()
            if (lecturaAct.trim().isEmpty()) {
                CustomDialogs.sweetWarning(this, "¡Debe ingresar una lectura!")
                return@setOnClickListener
            }
            if (photoLectura != null) {
                if (photoLectura!!.photoLectura.isEmpty()) {
                    CustomDialogs.sweetWarning(this, "¡Debe tomar una foto!")
                    return@setOnClickListener
                }
            } else {
                CustomDialogs.sweetWarning(this, "¡Debe tomar una foto!")
                return@setOnClickListener
            }
            val lectura = Lectura(
                arrendatario.idArrendatario,
                arrendatario.lecturaAnt,
                lecturaAct.toDouble(),
                editLecturaObservaciones.text.toString(),
                user.mLogin,
                photoLectura
            )
            mPresenter!!.sendLecturaRx(lectura)
        }
    }

    override fun onSuccess() {
        mProgress!!.dismissWithAnimation()
        CustomDialogs.sweetSuccessCloseActivity(this, "Lectura enviada correctamente", this)
    }

    override fun onLoading(message: String) {
        mProgress = CustomDialogs.sweetLoading(this, message)
        mProgress!!.show()
    }

    override fun onError(message: String) {
        mProgress!!.dismissWithAnimation()
        CustomDialogs.sweetError(this, message)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.lectura_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.btnCamera -> {
                if (Permissions.cameraPermission(this, this, CAMERA_REQUEST)) {
                    if (Permissions.storagePermission(this, this, STORAGE_REQUEST)) {
                        openCamera()
                    }
                }
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val timeStamp =
                SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                    .format(Date())
            nombreFoto = "lecturas_movil_$timeStamp"
            try {
                rutaArchivo = createImageFile()
                val photoURI = FileProvider.getUriForFile(
                    this,
                    "mx.gob.fondofuturo.siscambpmovil.fileprovider",
                    rutaArchivo!!
                )
                takePictureIntent.putExtra(
                    MediaStore.EXTRA_SCREEN_ORIENTATION,
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, PHOTOS_REQUEST)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val storage = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/Lecturas"
        )
        val file = File(storage.absolutePath + "/" + nombreFoto + ".jpeg")
        storage.mkdirs()
        return if (file.createNewFile()) file else null
    }

    private fun savePhoto() {
        try {
            val fileInputStream =
                FileInputStream(rutaArchivo!!.absolutePath)
            val bytesArray =
                ByteArray(rutaArchivo!!.length().toInt())
            fileInputStream.read(bytesArray)
            fileInputStream.close()
            val photoStr: String = Convertions.convertByteToString(bytesArray)
            photoLectura = FotoLectura(
                photoStr,
                "$nombreFoto.jpeg",
                rutaArchivo!!.absolutePath
            )
            Glide.with(this)
                .load(rutaArchivo!!.absolutePath).into(imageLectura)
            imageLectura.visibility = View.VISIBLE
            imageLectura.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PHOTOS_REQUEST -> if (resultCode == RESULT_OK) {
                savePhoto()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Permissions.storagePermission(this, this, STORAGE_REQUEST)
                } else {
                    CustomDialogs.sweetWarning(this, "Debe aceptar todos los permisos")
                }
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    CustomDialogs.sweetWarning(this, "Debe aceptar todos los permisos")
                }
            }
            STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    CustomDialogs.sweetWarning(this, "Debe aceptar todos los permisos")
                } else {
                    openCamera()
                }
            }
        }
    }

}
