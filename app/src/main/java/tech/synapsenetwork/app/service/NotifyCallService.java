package tech.synapsenetwork.app.service;

import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tech.synapsenetwork.app.Constants;


public class NotifyCallService implements NotifyService {


    private final OkHttpClient httpClient;
    private final Gson gson;
    private ApiClient apiClient;

    public NotifyCallService(
            OkHttpClient httpClient,
            Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
        buildApiClient(Constants.API_URL);
    }

    private void buildApiClient(String baseUrl) {
        apiClient = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiClient.class);
    }

    @Override
    public Observable<String> notify(String fromAddress, String toAddress, String notifyType) {
        return apiClient
                .updateToken(fromAddress, toAddress, notifyType)
                .lift(apiError())
                .map(r -> r.response)
                .subscribeOn(Schedulers.io());
    }

    private static @NonNull
    <T> ApiErrorOperator<T> apiError() {
        return new ApiErrorOperator<>();
    }

    public interface ApiClient {
        @FormUrlEncoded
        @POST("/notify.php")
        Observable<Response<NotifyResponse>> updateToken(@Field("from_address") String fromAddress, @Field("to_address") String toAddress, @Field("notify_type") String notifyType);
    }

    private static class NotifyResponse {
        String response;
    }

    private final static class ApiErrorOperator<T> implements ObservableOperator<T, Response<T>> {

        @Override
        public Observer<? super Response<T>> apply(Observer<? super T> observer) throws Exception {
            return new DisposableObserver<Response<T>>() {
                @Override
                public void onNext(Response<T> response) {
                    //observer.onNext(response.body());
                    observer.onComplete();
                }

                @Override
                public void onError(Throwable e) {
                    observer.onError(e);
                }

                @Override
                public void onComplete() {
                    observer.onComplete();
                }
            };
        }
    }
}
