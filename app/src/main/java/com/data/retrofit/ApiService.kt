package com.data.retrofit

import com.data.response.DetailResponse
import com.data.response.GithubResponse
import com.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/users")
    fun searchUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username")username:String
    ):Call<DetailResponse>

    @GET("users/{username}/followers")
    fun Getdatafollower(
        @Path("username")username: String
    ):Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun Getdatafollowing(
        @Path("username")username: String
    ):Call<List<ItemsItem>>

//    @FormUrlEncoded
//    @Headers("Authorization: token 12345")
//    @POST("review")
//    fun postreview(
//        @Field("id") id:String,
//        @Field("name") name:String,
//        @Field("review") review:String
//    ): Call<PostReviewResponse>
}