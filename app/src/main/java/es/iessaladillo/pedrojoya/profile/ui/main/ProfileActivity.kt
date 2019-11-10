package es.iessaladillo.pedrojoya.profile.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.profile.R
import es.iessaladillo.pedrojoya.profile.data.local.Database
import es.iessaladillo.pedrojoya.profile.data.local.entity.Avatar
import es.iessaladillo.pedrojoya.profile.ui.avatar.AvatarActivity
import es.iessaladillo.pedrojoya.profile.utils.*
import kotlinx.android.synthetic.main.profile_activity.*
//import kotlinx.android.synthetic.main.profile_avatar.*
import kotlinx.android.synthetic.main.profile_form.*
import es.iessaladillo.pedrojoya.profile.utils.*

private const val RC_AVATAR = 1

class ProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        viewModel = ViewModelProvider(this).get(ProfileActivityViewModel::class.java)
        setupViews();
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSave) {
            save()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun save() {
        txtName.hideSoftKeyboard()
        if (!validate()) {
            toast(getString(R.string.main_error_saving))
        } else {
            toast(getString(R.string.main_saved_succesfully))
        }
    }

    private fun setupViews() {
        defaultAvatar()
        imgAvatar.setOnClickListener { changeImg() }
        lblAvatar.setOnClickListener { changeImg() }
        imgEmail.setOnClickListener { sendEmail() }
        imgPhonenumber.setOnClickListener { dialPhoneNumber() }
        imgAddress.setOnClickListener { searchInMap() }
        imgWeb.setOnClickListener { webSearch() }
    }

    private fun changeImg() {
        AvatarActivity.startForResult(this, viewModel.avatar!!, RC_AVATAR)
    }

    private fun defaultAvatar() {
        if (viewModel.avatar == null) {
            viewModel.avatar = Database.queryDefaultAvatar()
        }
        imgAvatar.setImageResource(viewModel.avatar!!.imageResId)
        lblAvatar.setText(viewModel.avatar!!.name)
    }

    private fun sendEmail() {
        val intent: Intent
        if (validateEmail()) {
            intent = newEmailIntent(txtEmail.text.toString())
            sendIntent(intent)
        }
    }

    private fun dialPhoneNumber() {
        val intent: Intent
        if (validatePhonenumber()) {
            intent = newDialIntent(txtPhonenumber.text.toString())
            sendIntent(intent)
        }
    }

    private fun searchInMap() {
        val intent: Intent
        if (validateAddress()) {
            intent = newSearchInMapIntent(txtAddress.text.toString())
            sendIntent(intent)
        }
    }

    private fun webSearch() {
        val intent: Intent
        if (validateWeb()) {
            intent = newViewUriIntent(Uri.parse(txtWeb.text.toString()))
            sendIntent(intent)
        }
    }

    private fun validateName(): Boolean {
        if (txtName.text.toString().isEmpty()) {
            txtName.error = getString(R.string.main_invalid_data)
            lblName.isEnabled = false
            return false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        if (!txtEmail.text.toString().isValidEmail()) {
            invalidData(txtEmail, lblEmail, imgEmail)
            return false
        }
        imgEmail.isEnabled = true
        return true
    }

    private fun validatePhonenumber(): Boolean {
        if (!txtPhonenumber.text.toString().isValidPhone()) {
            invalidData(txtPhonenumber, lblPhonenumber, imgPhonenumber)
            return false
        }
        imgPhonenumber.isEnabled = true
        return true
    }

    private fun validateAddress(): Boolean {
        if (txtAddress.text.toString().isEmpty()) {
            invalidData(txtAddress, lblAddress, imgAddress)
            return false
        }
        imgAddress.isEnabled = true
        return true
    }

    private fun validateWeb(): Boolean {
        if (!txtWeb.text.toString().isValidUrl()) {
            invalidData(txtWeb, lblWeb, imgWeb)
            return false
        }
        imgWeb.isEnabled = true
        return true
    }

    private fun invalidData(txt: EditText, lbl: TextView, imageView: ImageView) {
        txt.error = getString(R.string.main_invalid_data)
        lbl.isEnabled = false
        imageView.isEnabled = false
    }

    private fun validate(): Boolean {
        return validateName() && validateEmail() && validatePhonenumber() && validateAddress() && validateWeb()
    }

    private fun sendIntent(intent: Intent) {
        if (isActivityAvailable(this, intent)) {
            startActivity(intent)
        } else {
            txtName.hideSoftKeyboard()
            toast("Can not find an application to perform this action")
        }
    }
}
