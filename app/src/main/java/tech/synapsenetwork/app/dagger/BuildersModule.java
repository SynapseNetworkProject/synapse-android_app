package tech.synapsenetwork.app.dagger;

import tech.synapsenetwork.app.ui.AddTokenActivity;
import tech.synapsenetwork.app.ui.ConfirmationActivity;
import tech.synapsenetwork.app.ui.GasSettingsActivity;
import tech.synapsenetwork.app.ui.HomeActivity;
import tech.synapsenetwork.app.ui.ImportWalletActivity;
import tech.synapsenetwork.app.ui.MyAddressActivity;
import tech.synapsenetwork.app.ui.SendActivity;
import tech.synapsenetwork.app.ui.SettingsActivity;
import tech.synapsenetwork.app.ui.SplashActivity;
import tech.synapsenetwork.app.ui.TokensActivity;
import tech.synapsenetwork.app.ui.TransactionDetailActivity;
import tech.synapsenetwork.app.ui.TransactionsActivity;
import tech.synapsenetwork.app.ui.WalletsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {
	@ActivityScope
	@ContributesAndroidInjector(modules = SplashModule.class)
	abstract SplashActivity bindSplashModule();

	@ActivityScope
	@ContributesAndroidInjector(modules = AccountsManageModule.class)
	abstract WalletsActivity bindManageWalletsModule();

	@ActivityScope
	@ContributesAndroidInjector(modules = ImportModule.class)
	abstract ImportWalletActivity bindImportWalletModule();

	@ActivityScope
	@ContributesAndroidInjector(modules = TransactionsModule.class)
	abstract TransactionsActivity bindTransactionsModule();

	@ActivityScope
	@ContributesAndroidInjector(modules = TransactionsModule.class)
	abstract HomeActivity bindHomeModule();

    @ActivityScope
    @ContributesAndroidInjector(modules = TransactionDetailModule.class)
    abstract TransactionDetailActivity bindTransactionDetailModule();

	@ActivityScope
	@ContributesAndroidInjector(modules = SettingsModule.class)
	abstract SettingsActivity bindSettingsModule();

	@ActivityScope
	@ContributesAndroidInjector(modules = SendModule.class)
	abstract SendActivity bindSendModule();

	@ActivityScope
	@ContributesAndroidInjector(modules = ConfirmationModule.class)
	abstract ConfirmationActivity bindConfirmationModule();
    @ContributesAndroidInjector
	abstract MyAddressActivity bindMyAddressModule();

	@ActivityScope
    @ContributesAndroidInjector(modules = TokensModule.class)
	abstract TokensActivity bindTokensModule();

	@ActivityScope
	@ContributesAndroidInjector(modules = GasSettingsModule.class)
	abstract GasSettingsActivity bindGasSettingsModule();

	@ActivityScope
    @ContributesAndroidInjector(modules = AddTokenModule.class)
	abstract AddTokenActivity bindAddTokenActivity();
}
