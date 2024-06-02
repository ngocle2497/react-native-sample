package com.sample.common

open class BaseListView<T>(context: Context) : RecyclerView(context) where T : BaseEntity {
    private val adapter = BaseAdapter<T>()
    var listItems: ArrayList<T> = arrayOf()

    init {
        layoutManager = LinearLayoutManager(context)
        setAdapter(adapter)
    }

    fun notifiyDataChanged() {
        runOnUiThread { adapter.notifyDataSetChanged() }
    }

    fun setData(items: List<T>) {
        listItems.clear()
        listItems.addAll(items)
        notifiyDataChanged()
    }
}
