package mx.gob.fondofuturo.siscambpmovil.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import mx.gob.fondofuturo.siscambpmovil.R
import mx.gob.fondofuturo.siscambpmovil.model.data.Arrendatario
import mx.gob.fondofuturo.siscambpmovil.model.data.User
import mx.gob.fondofuturo.siscambpmovil.presenter.callback.ArrendatarioCallback
import mx.gob.fondofuturo.siscambpmovil.presenter.implementation.ArrendatarioPresenter
import mx.gob.fondofuturo.siscambpmovil.view.adapter.ArrendatarioAdapter
import mx.gob.fondofuturo.siscambpmovil.view.dialog.ArrendatarioFilterDialog
import mx.gob.fondofuturo.siscambpmovil.view.dialog.CustomDialogs
import java.text.SimpleDateFormat
import java.util.*

class ArrendatarioActivity : BaseActivity(),
    ArrendatarioFilterDialog.ArrendatarioFilterDialogListener, ArrendatarioCallback,
    ArrendatarioAdapter.ArrendatarioAdapterListener {

    private var mAdapter: ArrendatarioAdapter? = null
    private var mPresenter: ArrendatarioPresenter? = null
    private var mProgress: SweetAlertDialog? = null
    private var mUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUser = intent.extras!!.getParcelable<User>("user")
        supportActionBar!!.title = mUser!!.mLogin
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        supportActionBar!!.subtitle = simpleDateFormat.format(Date())
        mPresenter = ArrendatarioPresenter(this, this)
        mPresenter!!.getArrendatarios("")
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
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
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onFilterClicked(manzana: String) {
        mPresenter!!.getArrendatarios(manzana)
    }

    override fun onLoading(message: String) {
        mProgress = CustomDialogs.sweetLoading(this, message)
        mProgress!!.show()
    }

    override fun onSuccess(arrayListArrendatario: ArrayList<Arrendatario>) {
        mProgress!!.dismissWithAnimation()
        if (mAdapter == null) {
            initRecyclerView(arrayListArrendatario)
        } else {
            mAdapter!!.updateArrendatarios(arrayListArrendatario)
        }
    }

    override fun onError(message: String) {
        mProgress!!.dismissWithAnimation()
        CustomDialogs.sweetError(this, message)
    }

    override fun onArrendatarioClicked(arrendatario: Arrendatario) {
        val i = Intent(this, LecturaActivity::class.java)
        i.putExtra("arrendatario", arrendatario)
        i.putExtra("user", mUser)
        startActivity(i)
    }

}
