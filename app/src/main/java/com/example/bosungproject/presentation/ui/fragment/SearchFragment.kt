package com.example.bosungproject.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.bosungproject.R
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.presentation.ui.customView.SearchBar02CustomView
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private lateinit var inputMethodManager : InputMethodManager
    private lateinit var  searchEditText : EditText

    private lateinit var viewModel: SearchViewModel

    override fun onResume() {
        searchEditText.requestFocus()
        inputMethodManager.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        inputMethodManager = container?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        viewModel = ViewModelProvider(activity!!).get(SearchViewModel::class.java)

        val searchCustom02 = view.findViewById<SearchBar02CustomView>(R.id.searchCustom02)
        searchEditText = searchCustom02.getSearchTextView()
        searchEditText.setOnEditorActionListener { textView, action, keyEvent ->
            when (action){
                EditorInfo.IME_ACTION_SEARCH -> let{
                    if(searchEditText.text.toString().isNotEmpty()) {
                        viewModel.search(SearchQuery(searchEditText.text.toString(), KakaoSearchSortEnum.Accuracy,null,null))
                        inputMethodManager.hideSoftInputFromWindow(searchEditText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
                        activity?.onBackPressed()
                    }
                    return@let true
                }
                else -> return@setOnEditorActionListener false
            }
        }
        searchCustom02.getBackArrowButton().setOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(searchEditText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
            activity?.onBackPressed()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {
//                    put(ARG_PARAM1, param1)
                }
            }
    }
}