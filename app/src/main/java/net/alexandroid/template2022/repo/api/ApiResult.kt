package net.alexandroid.template2022.repo.api

import okhttp3.ResponseBody

sealed class ApiResult {
    data class Success(val responseBody: ResponseBody) : ApiResult()
    data class Error(val exception: Exception) : ApiResult()
}