package id.poncoe.crudsqlite.kotlin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

import java.util.ArrayList
import java.util.HashMap

import id.poncoe.crudsqlite.R

class MainActivity : AppCompatActivity() {

    internal var listView: ListView? = null
    internal var dialog: AlertDialog.Builder? = null
    internal var itemList: MutableList<Data> = ArrayList()
    internal var adapter: Adapter? = null
    internal var SQLite = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        SQLite = DatabaseHelper(applicationContext)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        listView = findViewById<View>(R.id.list_view) as ListView

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEdit::class.java)
            startActivity(intent)
        }

        adapter = Adapter(this@MainActivity, itemList)
        listView!!.adapter = adapter

        // long press listview to show edit and delete
        listView!!.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            // TODO Auto-generated method stub
            val idx = itemList[position].id
            val name = itemList[position].name
            val address = itemList[position].address

            val dialogitem = arrayOf<CharSequence>("Edit", "Delete")
            dialog = AlertDialog.Builder(this@MainActivity)
            dialog!!.setCancelable(true)
            dialog!!.setItems(dialogitem) { dialog, which ->
                // TODO Auto-generated method stub
                when (which) {
                    0 -> {
                        val intent = Intent(this@MainActivity, AddEdit::class.java)
                        intent.putExtra(TAG_ID, idx)
                        intent.putExtra(TAG_NAME, name)
                        intent.putExtra(TAG_ADDRESS, address)
                        startActivity(intent)
                    }
                    1 -> {
                        SQLite.delete(Integer.parseInt(idx))
                        itemList.clear()
                        getAllData()
                    }
                }
            }.show()
            false
        }

        getAllData()

    }

    private fun getAllData() {
        val row = SQLite.allData

        for (i in row.indices) {
            val id = row[i][TAG_ID]
            val poster = row[i][TAG_NAME]
            val title = row[i][TAG_ADDRESS]

            val data = Data()

            data.id = id
            data.name = poster
            data.address = title

            itemList.add(data)
        }

        adapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        itemList.clear()
        getAllData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    companion object {

        val TAG_ID = "id"
        val TAG_NAME = "name"
        val TAG_ADDRESS = "address"
    }
}
