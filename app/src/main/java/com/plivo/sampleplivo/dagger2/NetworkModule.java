package com.plivo.sampleplivo.dagger2;

import android.util.Base64;

import com.plivo.sampleplivo.BuildConfig;
import com.plivo.sampleplivo.network.Api;
import com.plivo.sampleplivo.utils.Constants;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides @Singleton
    OkHttpClient getOKHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request authRequest = chain.request()
                                .newBuilder()
                                .header("Authorization", Credentials.basic(Constants.AUTH_ID, Constants.AUTH_TOKEN))
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/json")
                                .build();
                        return chain.proceed(authRequest);
                    }
                })
                .build();
    }

    @Provides @Singleton
    Retrofit createRetrofit(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    @Provides @Singleton
    public Api createApiService() {
        return createRetrofit(getOKHttpClient()).create(Api.class);
    }


}
