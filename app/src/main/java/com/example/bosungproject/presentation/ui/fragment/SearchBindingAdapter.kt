package com.example.bosungproject.presentation.ui.fragment

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel

@BindingAdapter("viewModel")
fun onEditText(editText: EditText, viewModel: SearchViewModel){
    editText.setOnEditorActionListener { textView, action, keyEvent ->
        when(action){
            EditorInfo.IME_ACTION_SEARCH -> {
                viewModel.search(SearchQuery(textView.text.toString(), KakaoSearchSortEnum.Accuracy, 1, 10))
                true
            }
            else -> false
        }
    }
}