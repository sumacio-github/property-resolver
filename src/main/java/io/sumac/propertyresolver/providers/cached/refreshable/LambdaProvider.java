package io.sumac.propertyresolver.providers.cached.refreshable;

import java.util.Properties;
import java.util.function.Supplier;

public class LambdaProvider extends RefreshableProvider {

	private final Supplier<Properties> supplier;

	public LambdaProvider(Supplier<Properties> supplier) {
		this.supplier = supplier;
		refresh();
	}

	@Override
	protected Properties fetchAll() {
		return supplier.get();
	}

}
