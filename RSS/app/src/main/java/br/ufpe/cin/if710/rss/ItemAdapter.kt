package br.ufpe.cin.if710.rss

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.itemlista.view.*

class ItemAdapter (private val itens: List<ItemRSS>, private val c : Context) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    var link = ""

    override fun getItemCount(): Int = itens.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.itemlista, parent, false)
        return ViewHolder(view, c)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val i = itens[position]
        holder.title.text = i.title
        holder.pubDate.text = i.pubDate
        holder.itemView.tag = link
    }

    class ViewHolder (item : View, c : Context) : RecyclerView.ViewHolder(item), View.OnClickListener {
        val title = item.item_titulo!!
        val pubDate = item.item_data!!
        val ctx = c

        init {
            item.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val uri = Uri.parse(v.tag.toString())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            ctx.startActivity(intent)
        }
    }
}