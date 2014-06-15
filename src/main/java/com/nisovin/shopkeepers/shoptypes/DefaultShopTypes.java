package com.nisovin.shopkeepers.shoptypes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.nisovin.shopkeepers.Log;
import com.nisovin.shopkeepers.Settings;
import com.nisovin.shopkeepers.ShopCreationData;
import com.nisovin.shopkeepers.ShopType;
import com.nisovin.shopkeepers.Shopkeeper;

public class DefaultShopTypes {

	public static List<ShopType<?>> getAll() {
		List<ShopType<?>> defaults = new ArrayList<ShopType<?>>();
		defaults.add(ADMIN);
		defaults.add(PLAYER_BOOK);
		defaults.add(PLAYER_BUY);
		defaults.add(PLAYER_NORMAL);
		defaults.add(PLAYER_TRADE);
		return defaults;
	}

	private static abstract class DefaultShopType<T extends Shopkeeper> extends ShopType<T> {

		protected DefaultShopType(String identifier, String permission) {
			super(identifier, permission);
		}

		// TODO instead of returning null: throw (illegal argument) exceptions and catch those?
		/*
		 * Returns false if some check fails.
		 */
		protected boolean commonPreChecks(ShopCreationData data) {
			// common null checks:
			if (data == null || data.location == null || data.objectType == null) {
				Log.debug("Couldn't create shopkeeper: null");
				return false;
			}
			return true;
		}

		protected boolean commonPlayerPreChecks(ShopCreationData data) {
			if (!this.commonPreChecks(data)) return false;
			if (data.creator == null || data.chest == null) {
				Log.debug("Couldn't create shopkeeper: null");
				return false;
			}

			return true;
		}

		protected boolean commonPreChecks(ConfigurationSection section) {
			// common null checks:
			if (section == null) {
				Log.debug("Couldn't create shopkeeper: null");
				return false;
			}
			return true;
		}
	}

	// DEFAULT SHOP TYPES:

	// ADMIN SHOP
	public final static ShopType<AdminShopkeeper> ADMIN = new DefaultShopType<AdminShopkeeper>("admin", "shopkeeper.admin") {

		@Override
		public AdminShopkeeper loadShopkeeper(ConfigurationSection config) {
			if (this.commonPreChecks(config)) {
				AdminShopkeeper shopkeeper = new AdminShopkeeper(config);
				this.registerShopkeeper(shopkeeper);
				return shopkeeper;
			}
			return null;
		}

		@Override
		public AdminShopkeeper createShopkeeper(ShopCreationData data) {
			if (this.commonPreChecks(data)) {
				AdminShopkeeper shopkeeper = new AdminShopkeeper(data.location, data.objectType);
				this.registerShopkeeper(shopkeeper);
				return shopkeeper;
			}
			return null;
		}

		@Override
		public boolean isPlayerShopType() {
			return false;
		}

		@Override
		public String getCreatedMessage() {
			return Settings.msgAdminShopCreated;
		}

		@Override
		public void onSelect(Player player) {
			// currently can't be 'selected'
		}
	};

	// NORMAL PLAYER SHOP
	public final static ShopType<NormalPlayerShopkeeper> PLAYER_NORMAL = new DefaultShopType<NormalPlayerShopkeeper>("player", "shopkeeper.player.normal") {

		@Override
		public NormalPlayerShopkeeper loadShopkeeper(ConfigurationSection config) {
			if (this.commonPreChecks(config)) {
				NormalPlayerShopkeeper shopkeeper = new NormalPlayerShopkeeper(config);
				this.registerShopkeeper(shopkeeper);
				return shopkeeper;
			}
			return null;
		}

		@Override
		public NormalPlayerShopkeeper createShopkeeper(ShopCreationData data) {
			if (this.commonPlayerPreChecks(data)) {
				NormalPlayerShopkeeper shopkeeper = new NormalPlayerShopkeeper(data.creator, data.chest, data.location, data.objectType);
				this.registerShopkeeper(shopkeeper);
				return shopkeeper;
			}
			return null;
		}

		@Override
		public boolean isPlayerShopType() {
			return true;
		}

		@Override
		public String getCreatedMessage() {
			return Settings.msgPlayerShopCreated;
		}

		@Override
		public boolean matches(String identifier) {
			if (super.matches(identifier)) return true;
			String lower = identifier.toLowerCase();
			return lower.startsWith("norm") || lower.startsWith("sell");
		}

		@Override
		public void onSelect(Player player) {
			player.sendMessage(Settings.msgSelectedNormalShop);
		}
	};

	// PLAYER BOOK SHOP
	public final static ShopType<WrittenBookPlayerShopkeeper> PLAYER_BOOK = new DefaultShopType<WrittenBookPlayerShopkeeper>("book", "shopkeeper.player.book") {

		@Override
		public WrittenBookPlayerShopkeeper loadShopkeeper(ConfigurationSection config) {
			if (this.commonPreChecks(config)) {
				WrittenBookPlayerShopkeeper shopkeeper = new WrittenBookPlayerShopkeeper(config);
				this.registerShopkeeper(shopkeeper);
				return shopkeeper;
			}
			return null;
		}

		@Override
		public WrittenBookPlayerShopkeeper createShopkeeper(ShopCreationData data) {
			if (this.commonPlayerPreChecks(data)) {
				WrittenBookPlayerShopkeeper shopkeeper = new WrittenBookPlayerShopkeeper(data.creator, data.chest, data.location, data.objectType);
				this.registerShopkeeper(shopkeeper);
				return shopkeeper;
			}
			return null;
		}

		@Override
		public boolean isPlayerShopType() {
			return true;
		}

		@Override
		public String getCreatedMessage() {
			return Settings.msgBookShopCreated;
		}

		@Override
		public boolean matches(String identifier) {
			if (super.matches(identifier)) return true;
			String lower = identifier.toLowerCase();
			return lower.startsWith("book");
		}

		@Override
		public void onSelect(Player player) {
			player.sendMessage(Settings.msgSelectedBookShop);
		}
	};

	// BUYING PLAYER SHOP
	public final static ShopType<BuyingPlayerShopkeeper> PLAYER_BUY = new DefaultShopType<BuyingPlayerShopkeeper>("buy", "shopkeeper.player.buy") {

		@Override
		public BuyingPlayerShopkeeper loadShopkeeper(ConfigurationSection config) {
			if (this.commonPreChecks(config)) {
				BuyingPlayerShopkeeper shopkeeper = new BuyingPlayerShopkeeper(config);
				this.registerShopkeeper(shopkeeper);
				return shopkeeper;
			}
			return null;
		}

		@Override
		public BuyingPlayerShopkeeper createShopkeeper(ShopCreationData data) {
			if (this.commonPlayerPreChecks(data)) {
				BuyingPlayerShopkeeper shopkeeper = new BuyingPlayerShopkeeper(data.creator, data.chest, data.location, data.objectType);
				this.registerShopkeeper(shopkeeper);
				return shopkeeper;
			}
			return null;
		}

		@Override
		public boolean isPlayerShopType() {
			return true;
		}

		@Override
		public String getCreatedMessage() {
			return Settings.msgBuyShopCreated;
		}

		@Override
		public boolean matches(String identifier) {
			if (super.matches(identifier)) return true;
			String lower = identifier.toLowerCase();
			return lower.startsWith("buy");
		}

		@Override
		public void onSelect(Player player) {
			player.sendMessage(Settings.msgSelectedBuyShop);
		}
	};

	// TRADING PLAYER SHOP
	public final static ShopType<TradingPlayerShopkeeper> PLAYER_TRADE = new DefaultShopType<TradingPlayerShopkeeper>("trade", "shopkeeper.player.trade") {

		@Override
		public TradingPlayerShopkeeper loadShopkeeper(ConfigurationSection config) {
			if (this.commonPreChecks(config)) {
				TradingPlayerShopkeeper shopkeeper = new TradingPlayerShopkeeper(config);
				this.registerShopkeeper(shopkeeper);
				return shopkeeper;
			}
			return null;
		}

		@Override
		public TradingPlayerShopkeeper createShopkeeper(ShopCreationData data) {
			if (this.commonPlayerPreChecks(data)) {
				TradingPlayerShopkeeper shopkeeper = new TradingPlayerShopkeeper(data.creator, data.chest, data.location, data.objectType);
				this.registerShopkeeper(shopkeeper);
				return shopkeeper;
			}
			return null;
		}

		@Override
		public boolean isPlayerShopType() {
			return true;
		}

		@Override
		public String getCreatedMessage() {
			return Settings.msgTradeShopCreated;
		}

		@Override
		public boolean matches(String identifier) {
			if (super.matches(identifier)) return true;
			String lower = identifier.toLowerCase();
			return lower.startsWith("trad");
		}

		@Override
		public void onSelect(Player player) {
			player.sendMessage(Settings.msgSelectedTradeShop);
		}
	};
}