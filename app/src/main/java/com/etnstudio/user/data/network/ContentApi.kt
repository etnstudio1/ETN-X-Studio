package com.etnstudio.user.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ContentApi {
    @GET("{username}/{repo}/{branch}/{path}")
    suspend fun fetchContent(
        @Path("username") username: String,
        @Path("repo") repo: String,
        @Path("branch") branch: String,
        @Path("path") path: String
    ): String
}
