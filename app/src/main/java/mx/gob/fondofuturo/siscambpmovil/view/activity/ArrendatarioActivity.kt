package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.view.adapter.ArrendatarioAdapter
import mx.gob.fondofuturo.siscambpmovil.view.dialog.ArrendatarioFilterDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ArrendatarioActivity : BaseActivity(),
    ArrendatarioFilterDialog.ArrendatarioFilterDialogListener {

    private var mAdapter: ArrendatarioAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = intent.extras!!.getParcelable<User>("user")
        supportActionBar!!.title = user!!.mUser
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        supportActionBar!!.subtitle = simpleDateFormat.format(Date())
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private fun initRecyclerView(arrayList: ArrayList<Arrendatario>) {
        mainRecycler.layoutManager = LinearLayoutManager(this)
        mAdapter = ArrendatarioAdapter(arrayList)
        mainRecycler.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.arrendatario_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnFilterArrend -> {
                val fragmentManager = supportFragmentManager
                ArrendatarioFilterDialog.show(fragmentManager, "filter_arrendatario")
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onFilterClicked(manzana: String) {
        Toast.makeText(this, "manzana: $manzana", Toast.LENGTH_LONG).show()
    }

}
