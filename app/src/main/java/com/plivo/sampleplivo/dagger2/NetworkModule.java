package com.plivo.sampleplivo.dagger2;

import com.plivo.sampleplivo.BuildConfig;
import com.plivo.sampleplivo.network.Api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides @Singleton
    public Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides @Singleton
    public Api createApiService() {
        return createRetrofit().create(Api.class);
    }


}
