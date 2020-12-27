package com.example.bosungproject.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.example.bosungproject.R
import com.example.bosungproject.databinding.FragmentSearchBinding
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.presentation.ui.customView.SearchBar02CustomView
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel
import com.example.bosungproject.presentation.util.EventObserver
import kotlinx.android.synthetic.main.custom_search_bar02.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private val TAG = "SearchFragment"
    private lateinit var inputMethodManager : InputMethodManager

    private lateinit var binding : FragmentSearchBinding
    private lateinit var  searchEditText : EditText

    override fun onResume() {
        searchEditText.requestFocus()
        inputMethodManager.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.apply {
            viewModel = ViewModelProvider(activity!!).get(SearchViewModel::class.java)
            lifecycleOwner = this@SearchFragment
        }
        searchEditText = binding.searchEditText

        inputMethodManager = container?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.searchBackArrowButton.setOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
            activity?.onBackPressed()
        }

        return binding.root
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