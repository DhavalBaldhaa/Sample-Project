package com.devstree.basesample.model

import com.google.gson.annotations.SerializedName

class ListBaseModel<T>(var data: List<T>) : BaseModel(false, "Something went wrong") {
    @SerializedName("current_page")
    val currentPage: Int = 0

    @SerializedName("total_records")
    val totalRecords: Int = 0

    @SerializedName("total_page")
    val totalPage: Int = 0
}
