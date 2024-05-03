package com.example.pharmamate.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmamate.EditScreen
import com.example.pharmamate.ManageInventory
import com.example.pharmamate.R

class MedicineAdapter(
    private val activity: Activity,
    private val context: Context,
    private val fragment : String,
    private val uniqueIdList:ArrayList<String>,
    private val nameList: ArrayList<String>,
    private val priceList: ArrayList<Int>,
    private val quantityList: ArrayList<Int>,
    private val mfgList: ArrayList<String>,
    private val expList: ArrayList<String>
) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    inner class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val uniqueId : TextView = itemView.findViewById(R.id.uniqueIdValue)
        val name : TextView = itemView.findViewById(R.id.name)
        val price : TextView = itemView.findViewById(R.id.price)
        val quantity : TextView = itemView.findViewById(R.id.quantity)
        val mfg : TextView = itemView.findViewById(R.id.mfg)
        val exp : TextView = itemView.findViewById(R.id.exp)
        val itemLayout : LinearLayout = itemView.findViewById(R.id.itemLayout)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view =LayoutInflater.from(context).inflate(R.layout.inventory_item,parent,false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.uniqueId.text = uniqueIdList[position]
        holder.name.text = nameList[position]
        holder.price.text = priceList[position].toString()
        holder.quantity.text = quantityList[position].toString()
        holder.mfg.text = mfgList[position]
        holder.exp.text = expList[position]

        holder.itemLayout.setOnClickListener{
            if(fragment == "add"){
                val intent = Intent(context, EditScreen::class.java)
                intent.putExtra("uniqueId",uniqueIdList[position])
                intent.putExtra("name",nameList[position])
                intent.putExtra("price",priceList[position])
                intent.putExtra("quantity",quantityList[position])
                intent.putExtra("mfg",mfgList[position])
                intent.putExtra("exp",expList[position])

                activity.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return uniqueIdList.size
    }
}