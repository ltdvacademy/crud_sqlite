package id.poncoe.crudsqlite.kotlin

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import id.poncoe.crudsqlite.R

class Adapter(private val activity: Activity, private val items: List<Data>) : BaseAdapter() {
    private var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(location: Int): Any {
        return items[location]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        if (inflater == null)
            inflater = activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView == null)
            convertView = inflater!!.inflate(R.layout.list_row, null)

        val id = convertView!!.findViewById<View>(R.id.id) as TextView
        val name = convertView.findViewById<View>(R.id.name) as TextView
        val address = convertView.findViewById<View>(R.id.address) as TextView

        val data = items[position]

        id.text = data.id
        name.text = data.name
        address.text = data.address

        return convertView
    }
}