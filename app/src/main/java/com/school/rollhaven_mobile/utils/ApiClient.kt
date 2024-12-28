package com.school.rollhaven_mobile.utils

import com.school.rollhaven_mobile.repositories.IRollHavenRepository

object ApiClient {
    val apiService: IRollHavenRepository by lazy {
        RetrofitFactory.retrofit.create(IRollHavenRepository::class.java)
    }

}