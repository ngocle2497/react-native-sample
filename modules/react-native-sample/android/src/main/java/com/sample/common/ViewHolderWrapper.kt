package com.sample.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder

open class ViewHolderWrapper<T>(itemView: View): ViewHolder(itemView) {

    open fun bindingData(data: T) {

    }

    open fun animatePrice(data: T) {
        bindingData(data)
    }

}