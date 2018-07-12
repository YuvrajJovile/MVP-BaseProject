package com.baseproject.webservice;


import com.baseproject.common.Constants;
import com.baseproject.model.dto.request.PostApiRequest;
import com.baseproject.model.dto.response.GetApiResponse;
import com.baseproject.model.dto.response.PostApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {


    @Headers(Constants.Header.HEADER_CONTENT_TYPE)
    @GET("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
    Call<GetApiResponse> performGetRequest();


    @Headers(Constants.Header.HEADER_CONTENT_TYPE)
    @POST("http://httpbin.org/post")
    Call<PostApiResponse> performPostRequest(@Body PostApiRequest lPostApiRequest);





}