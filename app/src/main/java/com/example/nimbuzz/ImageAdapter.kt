package com.example.nimbuzz


import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(context: Context, images: List<Uri>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private var context: Context? = context
    private var images: List<Uri>? = images
    private var layoutInflater: LayoutInflater? = null


    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater!!.inflate(R.layout.layout_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        images?.let {
            val path = it[position]

            context?.let {

                Glide.with(it)
                    .load(path)
                    .centerCrop()
                    .into(holder.ivImage)

                holder.indexview.text = "${position+1}"

            }

        }
    }

    override fun getItemCount(): Int {
        return images!!.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        internal var ivImage: ImageView
        internal var indexview: TextView

        init {
            ivImage = itemView.findViewById(R.id.image)
            indexview = itemView.findViewById(R.id.ind)
        }
    }
}