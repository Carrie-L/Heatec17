package com.adsale.HEATEC.util.network;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Carrie on 2017/9/25.
 */

public interface NetworkClient {

    /**
     * in LoadingActivity
     */
    @Headers(NetworkHelper.HEADER_SOAP_ACTION_MASTER)
    @POST(NetworkHelper.GetMaster)
    Observable<Response<ResponseBody>> getMaster(@Body RequestBody requestBody);

    @GET(Configure.FTP_URL)
    Observable<Response<ResponseBody>> downTxt(@Path("fileName") String fileName);

    @GET(NetworkHelper.DOWN_WEBCONTENT_URL)
    Observable<Response<ResponseBody>> downWebContent(@Path("fileName") String fileName);


    /**
     * in SubscribeActivity
     * @param langType eng||trad||simp
     */
    @POST(Configure.Subscribe_LAST_URL)
    Observable<Response<ResponseBody>> onSubscribe(@Query("lang") String lang, @Body RequestBody requestBody);


}
