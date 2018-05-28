package tech.synapsenetwork.app.repository;

import tech.synapsenetwork.app.entity.NetworkInfo;

public interface OnNetworkChangeListener {
	void onNetworkChanged(NetworkInfo networkInfo);
}
