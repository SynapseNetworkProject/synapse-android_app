package tech.synapsenetwork.app.dagger;

import android.content.Context;

import com.google.gson.Gson;
import tech.synapsenetwork.app.Application;
import tech.synapsenetwork.app.repository.PasswordStore;
import tech.synapsenetwork.app.repository.TrustPasswordStore;
import tech.synapsenetwork.app.util.LogInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
class ToolsModule {
	@Provides
	Context provideContext(Application application) {
		return application.getApplicationContext();
	}

	@Singleton
	@Provides
	Gson provideGson() {
		return new Gson();
	}

	@Singleton
	@Provides
	OkHttpClient okHttpClient() {
		return new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .build();
	}

	@Singleton
	@Provides
	PasswordStore passwordStore(Context context) {
		return new TrustPasswordStore(context);
	}
}
