package id.poncoe.crudsqlite.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import id.poncoe.crudsqlite.R

class AddEdit : AppCompatActivity() {

    internal var txt_id: EditText? = null
    internal var txt_name: EditText? = null
    internal var txt_address: EditText? = null
    internal var btn_submit: Button? = null
    internal var btn_cancel: Button? = null
    internal var SQLite = DatabaseHelper(this)
    internal var id: String? = null
    internal var name: String? = null
    internal var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        txt_id = findViewById<View>(R.id.txt_id) as EditText
        txt_name = findViewById<View>(R.id.txt_name) as EditText
        txt_address = findViewById<View>(R.id.txt_address) as EditText
        btn_submit = findViewById<View>(R.id.btn_submit) as Button
        btn_cancel = findViewById<View>(R.id.btn_cancel) as Button

        id = intent.getStringExtra(MainActivity.TAG_ID)
        name = intent.getStringExtra(MainActivity.TAG_NAME)
        address = intent.getStringExtra(MainActivity.TAG_ADDRESS)

        if (id == null || id === "") {
            title = "Add Data"
        } else {
            title = "Edit Data"
            txt_id!!.setText(id)
            txt_name!!.setText(name)
            txt_address!!.setText(address)
        }

        btn_submit!!.setOnClickListener {
            try {
                if (txt_id!!.text.toString() == "") {
                    save()
                } else {
                    edit()
                }
            } catch (e: Exception) {
                Log.e("Submit", e.toString())
            }
        }

        btn_cancel!!.setOnClickListener {
            blank()
            finish()
        }

    }

    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                blank()
                this.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // Make blank all Edit Text
    private fun blank() {
        txt_name!!.requestFocus()
        txt_id!!.text = null
        txt_name!!.text = null
        txt_address!!.text = null
    }

    // Save data to SQLite database
    private fun save() {
        if (txt_name!!.text.toString() == null || txt_name!!.text.toString() == "" ||
                txt_address!!.text.toString() == null || txt_address!!.text.toString() == "") {
            Toast.makeText(applicationContext,
                    "Please input name or address ...", Toast.LENGTH_SHORT).show()
        } else {
            SQLite.insert(txt_name!!.text.toString().trim { it <= ' ' }, txt_address!!.text.toString().trim { it <= ' ' })
            blank()
            finish()
        }
    }

    // Update data in SQLite database
    private fun edit() {
        if (txt_name!!.text.toString() == null || txt_name!!.text.toString() == "" ||
                txt_address!!.text.toString() == null || txt_address!!.text.toString() == "") {
            Toast.makeText(applicationContext,
                    "Please input name or address ...", Toast.LENGTH_SHORT).show()
        } else {
            SQLite.update(Integer.parseInt(txt_id!!.text.toString().trim { it <= ' ' }), txt_name!!.text.toString().trim { it <= ' ' },
                    txt_address!!.text.toString().trim { it <= ' ' })
            blank()
            finish()
        }
    }

}