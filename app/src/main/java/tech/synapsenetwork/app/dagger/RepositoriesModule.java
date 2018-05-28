package tech.synapsenetwork.app.dagger;

import android.content.Context;

import com.google.gson.Gson;
import tech.synapsenetwork.app.repository.EthereumNetworkRepository;
import tech.synapsenetwork.app.repository.EthereumNetworkRepositoryType;
import tech.synapsenetwork.app.repository.PreferenceRepositoryType;
import tech.synapsenetwork.app.repository.RealmTokenSource;
import tech.synapsenetwork.app.repository.SharedPreferenceRepository;
import tech.synapsenetwork.app.repository.TokenLocalSource;
import tech.synapsenetwork.app.repository.TokenRepository;
import tech.synapsenetwork.app.repository.TokenRepositoryType;
import tech.synapsenetwork.app.repository.TransactionInMemorySource;
import tech.synapsenetwork.app.repository.TransactionLocalSource;
import tech.synapsenetwork.app.repository.TransactionRepository;
import tech.synapsenetwork.app.repository.TransactionRepositoryType;
import tech.synapsenetwork.app.repository.WalletRepository;
import tech.synapsenetwork.app.repository.WalletRepositoryType;
import tech.synapsenetwork.app.service.AccountKeystoreService;
import tech.synapsenetwork.app.service.BlockExplorerClient;
import tech.synapsenetwork.app.service.BlockExplorerClientType;
import tech.synapsenetwork.app.service.EthplorerTokenService;
import tech.synapsenetwork.app.service.GethKeystoreAccountService;
import tech.synapsenetwork.app.service.TickerService;
import tech.synapsenetwork.app.service.TokenExplorerClientType;
import tech.synapsenetwork.app.service.TrustWalletTickerService;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class RepositoriesModule {
	@Singleton
	@Provides
	PreferenceRepositoryType providePreferenceRepository(Context context) {
		return new SharedPreferenceRepository(context);
	}

	@Singleton
	@Provides
	AccountKeystoreService provideAccountKeyStoreService(Context context) {
        File file = new File(context.getFilesDir(), "keystore/keystore");
		return new GethKeystoreAccountService(file);
	}

	@Singleton
    @Provides
    TickerService provideTickerService(OkHttpClient httpClient, Gson gson) {
	    return new TrustWalletTickerService(httpClient, gson);
    }

	@Singleton
	@Provides
	EthereumNetworkRepositoryType provideEthereumNetworkRepository(
            PreferenceRepositoryType preferenceRepository,
            TickerService tickerService) {
		return new EthereumNetworkRepository(preferenceRepository, tickerService);
	}

	@Singleton
	@Provides
    WalletRepositoryType provideWalletRepository(
            OkHttpClient okHttpClient,
			PreferenceRepositoryType preferenceRepositoryType,
			AccountKeystoreService accountKeystoreService,
			EthereumNetworkRepositoryType networkRepository) {
		return new WalletRepository(
		        okHttpClient, preferenceRepositoryType, accountKeystoreService, networkRepository);
	}

	@Singleton
	@Provides
	TransactionRepositoryType provideTransactionRepository(
			EthereumNetworkRepositoryType networkRepository,
			AccountKeystoreService accountKeystoreService,
			BlockExplorerClientType blockExplorerClient) {
		TransactionLocalSource inMemoryCache = new TransactionInMemorySource();
		TransactionLocalSource inDiskCache = null;
		return new TransactionRepository(
				networkRepository,
				accountKeystoreService,
				inMemoryCache,
				inDiskCache,
				blockExplorerClient);
	}

	@Singleton
	@Provides
	BlockExplorerClientType provideBlockExplorerClient(
			OkHttpClient httpClient,
			Gson gson,
			EthereumNetworkRepositoryType ethereumNetworkRepository) {
		return new BlockExplorerClient(httpClient, gson, ethereumNetworkRepository);
	}

	@Singleton
    @Provides
    TokenRepositoryType provideTokenRepository(
            OkHttpClient okHttpClient,
            EthereumNetworkRepositoryType ethereumNetworkRepository,
            TokenExplorerClientType tokenExplorerClientType,
            TokenLocalSource tokenLocalSource) {
	    return new TokenRepository(
	            okHttpClient,
	            ethereumNetworkRepository,
	            tokenExplorerClientType,
                tokenLocalSource);
    }

	@Singleton
    @Provides
    TokenExplorerClientType provideTokenService(OkHttpClient okHttpClient, Gson gson) {
	    return new EthplorerTokenService(okHttpClient, gson);
    }

    @Singleton
    @Provides
    TokenLocalSource provideRealmTokenSource() {
	    return new RealmTokenSource();
    }
}
