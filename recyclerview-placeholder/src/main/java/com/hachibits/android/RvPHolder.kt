package com.hachibits.android

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hachibits.android.recyclerviewplaceholder.R
import kotlinx.android.synthetic.main.empty_view_placeholder.view.*

open class RvPHolder : ConstraintLayout {
    private lateinit var recyclerView: RecyclerView
    private var emptyListText: String? = null
    private var loadListText: String? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.empty_view_placeholder, this, true)
        recyclerView = listView;
        recyclerView.layoutManager = LinearLayoutManager(context)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RvPHolder)
        emptyListText = attributes.getString(R.styleable.RvPHolder_emptyText)
        loadListText = attributes.getString(R.styleable.RvPHolder_loadText)
        imageView.setImageDrawable(attributes.getDrawable(R.styleable.RvPHolder_src))
        textView.text = if (loadListText.isNullOrEmpty()) emptyListText else loadListText
        attributes.recycle()
    }

    /**
     * Update the adapter of the adapter and the visibility of the placeholders.
     * Works in conjunction with [setAdapter] method.
     */
    fun notifyDataSetChanged() {
        recyclerView.adapter?.notifyDataSetChanged()
        if (recyclerView.adapter != null)
            dataChanged(recyclerView.adapter!!.itemCount)
    }

    /**
     * Set the adapter of the RecyclerView
     */
    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

    /**
    * Changes the loading text.
    * This would take effect on the next shown text.
    */
    fun setLoad(text: String) {
        loadListText = text
    }

    /**
     * Changes the empty list text.
     * This would take effect on the next shown text.
     */
    fun setEmpty(text: String) {
        emptyListText = text
    }

    /**
     * Changes the visibility of the image and text placeholders to either be shown or hidden
     * depending on the count of the item(s) in the list.
     */
    private fun dataChanged(count: Int) {
        if (count == 0) {
            if (recyclerView.visibility == VISIBLE) {
                imageView.visibility = VISIBLE
                textView.visibility = VISIBLE
                recyclerView.setVisibility(INVISIBLE)
            }
        } else {
            if (recyclerView.visibility == INVISIBLE) {
                imageView.visibility = GONE
                textView.visibility = GONE
                recyclerView.setVisibility(VISIBLE)
            }
        }
    }
}