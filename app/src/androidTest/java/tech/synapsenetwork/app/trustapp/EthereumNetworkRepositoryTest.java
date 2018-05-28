package tech.synapsenetwork.app.trustapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import tech.synapsenetwork.app.repository.EthereumNetworkRepository;
import tech.synapsenetwork.app.repository.EthereumNetworkRepositoryType;
import tech.synapsenetwork.app.repository.PreferenceRepositoryType;
import tech.synapsenetwork.app.repository.SharedPreferenceRepository;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EthereumNetworkRepositoryTest {

	private EthereumNetworkRepositoryType networkRepository;

	@Before
	public void setUp() {
		Context context = InstrumentationRegistry.getTargetContext();
		PreferenceRepositoryType preferenceRepositoryType = new SharedPreferenceRepository(context);
		networkRepository = new EthereumNetworkRepository(preferenceRepositoryType);
	}

}
