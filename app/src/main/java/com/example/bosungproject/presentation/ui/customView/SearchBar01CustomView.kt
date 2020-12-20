package com.example.bosungproject.presentation.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import com.example.bosungproject.R

class SearchBar01CustomView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs){
    val TAG = "SearchCustom"
    private var search_textView: TextView
    private var hamburger_button : ImageView
    private var search_layout : LinearLayout
    private var search_x_button : ImageView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_search_bar01, this, true)

        search_textView = view.findViewById(R.id.search_textView)
        search_layout = view.findViewById(R.id.search_layout)
        hamburger_button = view.findViewById(R.id.search_hamburger_button)
        search_x_button = view.findViewById(R.id.search_x_button)

    }

    fun getSearchXbtn() : ImageView {
        return this.search_x_button
    }

    fun getSearchTextView() : TextView{
        return this.search_textView
    }

    fun getHamburgerButton() : ImageView{
        return this.hamburger_button
    }
}
