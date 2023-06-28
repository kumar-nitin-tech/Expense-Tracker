package com.example.expensetracker.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.localdatabase.Expense
import org.w3c.dom.Text
import java.util.concurrent.TimeoutException

class DailyTransactionAdapter( private val listener:ButtonClicked, private val itemListener: ItemClicked): RecyclerView.Adapter<DailyTransactionAdapter.ExpenseViewHolder>() {

    private var dailyTransaction = ArrayList<Expense>()

    inner class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val amount: TextView = itemView.findViewById(R.id.Amount)
        val category: TextView = itemView.findViewById(R.id.category)
        val deleteButton:ImageView = itemView.findViewById(R.id.deleteButton)
        val timeStamp:TextView = itemView.findViewById(R.id.timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val viewHolder = ExpenseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.daily_transaction_recylcer_layout, parent,false))
        viewHolder.deleteButton.setOnClickListener{
            listener.onClicked(dailyTransaction[viewHolder.adapterPosition])
        }
        viewHolder.itemView.setOnClickListener {
            itemListener.onItemClicked(dailyTransaction[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dailyTransaction.size
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current = dailyTransaction[position]
        holder.amount.text = "₹ ${current.amount}"
        holder.category.text = current.category
        holder.timeStamp.text = current.timeStamp
    }

    fun setExpense(newExpense: List<Expense>){
        dailyTransaction.clear()
        dailyTransaction.addAll(newExpense)
        notifyDataSetChanged()
    }
}

interface ButtonClicked{
    fun onClicked(expense: Expense)
}

interface ItemClicked{
    fun onItemClicked(expense: Expense)
}