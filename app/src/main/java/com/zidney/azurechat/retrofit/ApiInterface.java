package com.zidney.azurechat.retrofit;

import com.zidney.azurechat.model.RootModel;
import com.zidney.azurechat.model.ResponeModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers({"Authorization: key=AAAAdxn1Z0M:APA91bGhRVhNgiroJWyUuNtUiyKcs8liszrPvRhZQY964HsF-7sHG3diiEP8e_lUF4MMGsAk4EnOp5M87D2FaUOx9I6vwS38BBA-nxUgwyt78H5J0YOfvVEio-f0bb9STc_o6KYr61ia",
            "Content-Type:application/json"})
    @POST("send")
    Call<ResponeModel> sendNotification(@Body RootModel rootModel);

}
