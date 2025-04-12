package com.microinvestment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.microinvestment.data.models.Home
import com.microinvestment.data.models.Investment
import com.microinvestment.databinding.LayoutHomeItemBinding
import com.microinvestment.databinding.LayoutInvestmentBinding
import com.microinvestment.utils.Clicked
import java.util.stream.IntStream


class HomeAdapter(
    private var data: List<Home>, private var listener: Clicked
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): HomeAdapter.ViewHolder {
        val binding =
            LayoutHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        with(holder) {
            val data = data[position]
            with(binding) {
                titleText.text = data.title
                imageIcon.setImageDrawable(data.icon)
            }

            itemView.setOnClickListener { listener.onClick(data.title) }
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }
}
