package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import mx.gob.fondofuturo.siscambpmovil.R

class LecturaActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Captura de lectura"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_lectura
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
            /*R.id.btnCamera -> {
                if (Permissions.cameraPermission(this, this, CAMERA_REQUEST)) {
                    if (Permissions.storagePermission(this, this, STORAGE_REQUEST)) {
                        openCamera()
                    }
                }
                true
            }*/

            else -> super.onContextItemSelected(item)
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            /*val timeStamp =
                SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                    .format(Date())
            nombreFoto = "seguimiento_movil_$timeStamp"
            try {
                rutaArchivo = createImageFile(nombreFoto)
                if (rutaArchivo != null) {
                    val photoURI = FileProvider.getUriForFile(
                        this,
                        "com.example.stock1.cobranza_sitio.fileprovider",
                        rutaArchivo
                    )
                    SharedQuery.getInstance()
                        .setPrefer(this, "file", rutaArchivo.getAbsolutePath(), "string")
                    SharedQuery.getInstance().setPrefer(this, "namephoto", nombreFoto, "string")
                    takePictureIntent.putExtra(
                        MediaStore.EXTRA_SCREEN_ORIENTATION,
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, PHOTOS_REQUEST)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }*/
        }
    }

}
