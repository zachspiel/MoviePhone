package com.moviephone.android.mainactivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.*

class AmcParseAdapter(
    private val parseItems: ArrayList<ParseItem>,
    private val context: Context
) : RecyclerView.Adapter<AmcParseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.parse_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val parseItem = parseItems[position]
        holder.textView.text = parseItem.title
        Picasso.get().load(parseItem.imgUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return parseItems.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var imageView: ImageView = view.findViewById(R.id.imageView)
        var textView: TextView = view.findViewById(R.id.textView)

        override fun onClick(view: View) {
            val position = adapterPosition
            val parseItem = parseItems[position]
            val intent = Intent(context, AmcDetailActivity::class.java)
            intent.putExtra("title", parseItem.title)
            intent.putExtra("image", parseItem.imgUrl)
            intent.putExtra("detailUrl", parseItem.detailUrl)
            context.startActivity(intent)
        }

        init {
            view.setOnClickListener(this)
        }
    }


}