package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_lectura.*
import kotlinx.android.synthetic.main.toolbar.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.databinding.ActivityLecturaBinding
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.model.data.FotoLectura
import mx.gob.fondofuturo.siscambpmovil.model.data.Lectura
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult
import mx.gob.fondofuturo.siscambpmovil.support.*
import mx.gob.fondofuturo.siscambpmovil.view.dialog.CustomDialogs
import mx.gob.fondofuturo.siscambpmovil.viewmodel.LecturaViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class LecturaActivity : AppCompatActivity()/* BaseActivity(), LecturaCallback*/ {

    private val lecturaViewModel: LecturaViewModel by viewModel()

    private var mProgress: SweetAlertDialog? = null
    private var photoLectura: FotoLectura? = null
    private var photoFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLecturaBinding>(
            this,
            R.layout.activity_lectura
        )
        binding.lifecycleOwner = this
        binding.lecturaViewModel = lecturaViewModel
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Captura de lectura"
        val user = intent.extras!!.getParcelable<User>("user")
        val arrendatario = intent.extras!!.getParcelable<Arrendatario>("arrendatario")

        savedInstanceState?.run {
            photoFile = getString("currentPhotoPath")?.let {
                File(it)
            }
        }
        initComponents(user!!, arrendatario!!)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        photoFile?.run {
            outState.putString("photoFile", absolutePath)
        }
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
                user.idUser,
                photoLectura
            )
            lecturaViewModel.setLectura(lectura)
            lecturaViewModel.lectura.observe(this, Observer {
                when (it) {
                    is LecturaResult.Loading -> {
                        mProgress = CustomDialogs.sweetLoading(this, it.message)
                        mProgress!!.show()
                    }
                    is LecturaResult.Success -> {
                        mProgress!!.dismissWithAnimation()
                        if (it.data.response!!.isEmpty()) {
                            CustomDialogs.sweetSuccessCloseActivity(
                                this,
                                "Lectura enviada correctamente",
                                this
                            )
                        } else {
                            CustomDialogs.sweetError(this, it.data.response!!)
                        }
                    }
                    is LecturaResult.Error -> {
                        mProgress!!.dismissWithAnimation()
                        CustomDialogs.sweetError(this, it.error)
                    }
                }
            })
        }
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
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                photoFile = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile.also {
                    val photoURI = FileProvider.getUriForFile(
                        this,
                        "mx.gob.fondofuturo.siscambpmovil.fileprovider",
                        it!!
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, PHOTOS_REQUEST)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(Date())
        val storageDir: File? =
            getExternalFilesDir("${Environment.DIRECTORY_PICTURES}/Lecturas")
        storageDir!!.mkdirs()
        return File.createTempFile(
            "lecturas_movil_$timeStamp",
            ".jpeg",
            storageDir
        )
    }

    private fun savePhoto() {
        try {
            val fileInputStream =
                FileInputStream(photoFile!!.absolutePath)
            val bytesArray =
                ByteArray(photoFile!!.length().toInt())
            fileInputStream.read(bytesArray)
            fileInputStream.close()
            val photoStr: String = Convertions.convertByteToString(bytesArray)
            photoLectura = FotoLectura(
                photoStr,
                photoFile!!.name,
                photoFile!!.absolutePath
            )
            Glide.with(this)
                .load(photoFile!!.absolutePath).into(imageLectura)
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
