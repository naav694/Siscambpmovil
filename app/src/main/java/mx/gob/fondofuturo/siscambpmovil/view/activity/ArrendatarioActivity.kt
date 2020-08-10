package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.model.response.LecturaResult
import mx.gob.fondofuturo.siscambpmovil.util.interfaces.ISessionHelper
import mx.gob.fondofuturo.siscambpmovil.view.adapter.ArrendatarioAdapter
import mx.gob.fondofuturo.siscambpmovil.view.dialog.ArrendatarioFilterDialog
import mx.gob.fondofuturo.siscambpmovil.view.dialog.CustomDialogs
import mx.gob.fondofuturo.siscambpmovil.viewmodel.ArrendatarioViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ArrendatarioActivity : AppCompatActivity(),
    ArrendatarioFilterDialog.ArrendatarioFilterDialogListener,
    ArrendatarioAdapter.ArrendatarioAdapterListener {

    private val arrendatarioViewModel: ArrendatarioViewModel by viewModel()
    private val sessionHelper: ISessionHelper by inject()

    private var mAdapter: ArrendatarioAdapter? = null
    private var mProgress: SweetAlertDialog? = null
    private var mUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mUser = intent.extras!!.getParcelable("user")
        supportActionBar!!.title = mUser!!.mLogin
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        supportActionBar!!.subtitle = simpleDateFormat.format(Date())

        arrendatarioViewModel.arrendatario.observe(this, Observer {
            when (it) {
                is LecturaResult.Loading -> {
                    mProgress = CustomDialogs.sweetLoading(this, it.message)
                    mProgress!!.show()
                }
                is LecturaResult.Success -> {
                    mProgress!!.dismissWithAnimation()
                    if (it.data.response!!.isEmpty()) {
                        if (mAdapter == null) {
                            initRecyclerView(it.data.data!!)
                        } else {
                            mAdapter!!.updateArrendatarios(it.data.data!!)
                        }
                    } else {
                        CustomDialogs.sweetWarning(this, it.data.response!!)
                    }
                }
                is LecturaResult.Error -> {
                    mProgress!!.dismissWithAnimation()
                    CustomDialogs.sweetError(this, it.error)
                }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mAdapter!!.filter.filter(newText)
                return false
            }

        })

        arrendatarioViewModel.setManzana("")
    }

    private fun initRecyclerView(arrayList: ArrayList<Arrendatario>) {
        val layoutManager = LinearLayoutManager(this)
        mainRecycler.layoutManager = layoutManager
        mAdapter = ArrendatarioAdapter(arrayList, this)
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
            R.id.btnLogOut -> {
                sessionHelper.setRememberSession(false)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onFilterClicked(manzana: String) {
        arrendatarioViewModel.setManzana(manzana)
    }

    override fun onArrendatarioClicked(arrendatario: Arrendatario) {
        val i = Intent(this, LecturaActivity::class.java)
        i.putExtra("arrendatario", arrendatario)
        i.putExtra("user", mUser)
        startActivity(i)
    }

}
