package com.example.calculadoraimc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculadoraimc.databinding.ItemHistoryRvBinding

class ItemAdapter(private val items: ArrayList<ImcEntity>,
                  private val deleteListener:(id:Int)->Unit
):RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    class ViewHolder(binding: ItemHistoryRvBinding) :
        RecyclerView.ViewHolder(binding.root){
        val llHistoryItems = binding.llHistoryItems
        val tvDate = binding.tvDateItem
        val tvWeight = binding.tvWeightItem
        val tvHeight = binding.tvHeightItem
        val tvImc = binding.tvImcItem
        val ivDelete = binding.ivDelete
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvDate.text = item.date
        holder.tvHeight.text = "${item.altura} cm"
        holder.tvWeight.text = "${item.peso} kg"
        holder.tvImc.text = item.imc


        holder.ivDelete.setOnClickListener{
            deleteListener.invoke(item.id)
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

}