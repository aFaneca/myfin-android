package com.afaneca.myfin.closed.budgets.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afaneca.myfin.R
import com.afaneca.myfin.base.objects.MyFinBudget
import com.afaneca.myfin.databinding.BudgetsListItemBinding
import com.afaneca.myfin.utils.formatMoney
import com.afaneca.myfin.utils.setupBalanceStyle
import java.time.Month
import java.time.format.TextStyle
import java.util.*

class BudgetsListAdapter(
    private val context: Context,
    private val dataset: List<MyFinBudget>,
    private val clickListener: BudgetsListItemClickListener
) : RecyclerView.Adapter<BudgetsListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: BudgetsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindListener(clickListener: BudgetsListItemClickListener, item: MyFinBudget) {
            binding.root.setOnClickListener { clickListener.onBudgetClick(item) }
        }

    }

    interface BudgetsListItemClickListener {
        fun onBudgetClick(trx: MyFinBudget)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            BudgetsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.apply {
            val currentDate = Calendar.getInstance()
            val isCurrentMonth =
                (item.month.toIntOrNull() ?: 0) == currentDate.get(Calendar.MONTH) + 1
                        && (item.year.toIntOrNull() ?: 0) == currentDate.get(Calendar.YEAR)

            mainWrapperCv.setBackgroundColor(
                context.getColor(
                    if (isCurrentMonth) R.color.colorAccent
                    else android.R.color.transparent
                )
            )

            dateMonth.text = Month.of(item.month.toIntOrNull() ?: 1).getDisplayName(
                TextStyle.SHORT_STANDALONE,
                Locale.getDefault()
            )
            dateYear.text = item.year
            budgetDescriptionTv.text = item.observations
            budgetBalanceTv.text = formatMoney(item.balanceValue)
            if (!isCurrentMonth)
                setupBalanceStyle(item.balanceValue, budgetBalanceTv)
            else
                budgetBalanceTv.setTextColor(context.getColor(R.color.design_default_color_on_primary))

            dateMonth.setTextColor(
                context.getColor(
                    if (isCurrentMonth) android.R.color.white
                    else R.color.colorAccent
                )
            )

            dateYear.setTextColor(
                context.getColor(
                    if (isCurrentMonth) android.R.color.white
                    else R.color.colorAccent
                )
            )

            dateIconIv.setColorFilter(
                context.getColor(
                    if (isCurrentMonth) android.R.color.white
                    else R.color.colorAccent
                )
            )
        }
    }

    override fun getItemCount(): Int = dataset.size
}
