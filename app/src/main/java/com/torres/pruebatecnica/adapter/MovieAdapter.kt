package com.torres.pruebatecnica.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.torres.pruebatecnica.data.model.MovieDao
import com.torres.pruebatecnica.databinding.RowMovieBinding
import com.torres.pruebatecnica.util.Constants
import com.torres.pruebatecnica.util.OnClickAdapter
import com.torres.pruebatecnica.vo.BaseViewHolder

class MovieAdapter(var context: Context,var items:ArrayList<MovieDao>,var clickAdapter: OnClickAdapter): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private lateinit var binding:RowMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
       binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MovieViewHolder ->{
                holder.bind(items[position],position)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MovieViewHolder(var context: Context):BaseViewHolder<MovieDao>(binding.root){

        override fun bind(item: MovieDao, position: Int) {
            Glide.with(context)
                .asBitmap()
                .load(Constants.URL_IMG + item.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgMovie)

            itemView.setOnClickListener {
                clickAdapter.onClick(item)
            }
        }

    }
}

