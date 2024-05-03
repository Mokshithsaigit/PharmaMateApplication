package com.example.pharmamate

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmamate.MedicineItem

class BillingAdapter(
    private val activity: Activity,
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val uniqueIdList: ArrayList<String>,
    private val nameList: ArrayList<String>,
    private val priceList: ArrayList<Int>,
    private val quantityList: ArrayList<Int>,
    private val mfgList: ArrayList<String>,
    private val expList: ArrayList<String>
) : RecyclerView.Adapter<BillingAdapter.BillingViewHolder>() {

    inner class BillingViewHolder(itemView: View, private val adapter: BillingAdapter) : RecyclerView.ViewHolder(itemView) {
        val uniqueId: TextView = itemView.findViewById(R.id.unqId)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
        val mfg: TextView = itemView.findViewById(R.id.mfg)
        val exp: TextView = itemView.findViewById(R.id.exp)
        val amount: TextView = itemView.findViewById(R.id.amount)

        val itemLayout: LinearLayout = itemView.findViewById(R.id.billingLayout)

        val plus: ImageButton
        val minus: ImageButton
        val qty: EditText

        init {
            plus = itemView.findViewById(R.id.plus)
            qty = itemView.findViewById(R.id.editText)
            minus = itemView.findViewById(R.id.minus)

            plus.setOnClickListener {
                val currentQuantity = qty.text.toString().toInt()
                val newQuantity = currentQuantity + 1
                amount.text = (newQuantity * price.text.toString().toInt()).toString()
                qty.setText(newQuantity.toString())
            }

            minus.setOnClickListener {
                val currentQuantity = qty.text.toString().toInt()
                if (currentQuantity > 1) {
                    val newQuantity = currentQuantity - 1
                    qty.setText(newQuantity.toString())
                    amount.text = (newQuantity * price.text.toString().toInt()).toString()
                }
            }

            qty.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Not needed
                }

                override fun afterTextChanged(s: Editable?) {
                    // Check if the EditText is not empty
                    if (!s.isNullOrBlank()) {
                        val newQuantity = s.toString().toIntOrNull() ?: 1
                        amount.text = (newQuantity * price.text.toString().toInt()).toString()
                    }
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.billing_item, parent, false)
        return BillingViewHolder(view, this)
    }

    override fun getItemCount(): Int {
        return uniqueIdList.size
    }

    override fun onBindViewHolder(holder: BillingViewHolder, position: Int) {
        holder.uniqueId.text = uniqueIdList[position]
        holder.name.text = nameList[position]
        holder.price.text = priceList[position].toString()
        holder.quantity.text = quantityList[position].toString()
        holder.mfg.text = mfgList[position]
        holder.exp.text = expList[position]
        holder.amount.text = priceList[position].toString() // Assuming initial amount
    }

    fun getMedicineItem(position: Int): MedicineItem {
        val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position) as? BillingViewHolder
            ?: return MedicineItem("", "", 0, 0, 0, "", "", 0)

        return MedicineItem(
            uniqueIdList[position],
            nameList[position],
            priceList[position],
            quantityList[position],
            viewHolder.qty.text.toString().toInt(), // Use user-modified quantity
            mfgList[position],
            expList[position],
            viewHolder.amount.text.toString().toInt() // Use calculated amount
        )
    }
}
