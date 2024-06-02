package com.sample.viewholders

import android.view.View
import android.widget.LinearLayout
import androidx.core.view.size
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.facebook.react.views.view.ReactViewGroup
import java.lang.Exception

class HeaderFooterWrapperViewHolder(itemView: View, childView: View?, wrapperViewId: Int) :
    ViewHolder(itemView) {
    private val lnWrapper: LinearLayout = itemView.findViewById(wrapperViewId)

    init {
        addViewIfNeeded(childView)
    }

    // ===== Public fun ===== \\
    fun updateLayout(h: Int, view: View?) {
        addViewIfNeeded(view)

        lnWrapper.updateLayoutParams {
            height = h
            width = LayoutParams.MATCH_PARENT
        }
    }

    // ===== Private fun ===== \\
    private fun addViewIfNeeded(view: View?) {
        if (lnWrapper.size > 0 || view == null) {
            return
        }
        try {
            if (view.parent is ReactViewGroup) {
                (view.parent as ReactViewGroup).removeView(view)
            } else if (view.parent is LinearLayout) {
                (view.parent as LinearLayout).removeView(view)
            }

            lnWrapper.addView(view)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }


    }
}