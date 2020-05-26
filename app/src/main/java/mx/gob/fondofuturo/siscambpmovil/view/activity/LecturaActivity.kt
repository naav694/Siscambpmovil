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
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_lectura.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.FotoLectura
import mx.gob.fondofuturo.siscambpmovil.support.Convertions
import mx.gob.fondofuturo.siscambpmovil.support.Permissions
import mx.gob.fondofuturo.siscambpmovil.view.dialog.CustomDialogs
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LecturaActivity : BaseActivity() {

    private var nombreFoto: String? = null
    private var rutaArchivo: File? = null

    private var photoArrayList: ArrayList<FotoLectura> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Captura de lectura"
        initComponents()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_lectura
    }

    private fun initComponents() {

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
            val photo: String = Convertions.convertByteToString(bytesArray)
            photoArrayList.add(
                FotoLectura(
                    "$nombreFoto.jpeg",
                    photo,
                    rutaArchivo!!.absolutePath
                )
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
