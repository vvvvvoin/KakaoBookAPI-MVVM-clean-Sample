package com.example.bosungproject.domain.model

data class SearchData(
    var meta : Meta,
    var documents : ArrayList<Documents>
){
    data class Meta(
        var total_count : Int,
        var pageable_count : Int,
        var is_end : Boolean
    )
    data class Documents(
        var authors : ArrayList<String>?,
        var contents : String?,
        var datetime : String?,
        var isbn : String?,
        var price : Int?,
        var publisher : String?,
        var sale_price : Int?,
        var status : String?,
        var thumbnail : String?,
        var title : String?,
        var translators : ArrayList<String>?,
        var url : String?
    )
}

