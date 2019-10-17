package com.nisovin.shopkeepers.commands.arguments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.nisovin.shopkeepers.Settings;
import com.nisovin.shopkeepers.api.ShopkeepersPlugin;
import com.nisovin.shopkeepers.api.shopobjects.ShopObjectType;
import com.nisovin.shopkeepers.commands.lib.ArgumentParseException;
import com.nisovin.shopkeepers.commands.lib.CommandArgs;
import com.nisovin.shopkeepers.commands.lib.CommandArgument;
import com.nisovin.shopkeepers.commands.lib.CommandContext;
import com.nisovin.shopkeepers.commands.lib.CommandInput;
import com.nisovin.shopkeepers.util.StringUtils;
import com.nisovin.shopkeepers.util.TextUtils;
import com.nisovin.shopkeepers.util.Utils;

public class ShopObjectTypeArgument extends CommandArgument<ShopObjectType<?>> {

	public ShopObjectTypeArgument(String name) {
		super(name);
	}

	@Override
	public String getInvalidArgumentErrorMsg(String argumentInput) {
		if (argumentInput == null) argumentInput = "";
		String[] defaultArgs = this.getDefaultErrorMsgArgs();
		return TextUtils.replaceArgs(Settings.msgCommandShopObjectTypeArgumentInvalid,
				Utils.concat(defaultArgs, "{argument}", argumentInput));
	}

	@Override
	public ShopObjectType<?> parseValue(CommandInput input, CommandArgs args) throws ArgumentParseException {
		if (!args.hasNext()) {
			throw this.missingArgumentError();
		}
		String argument = args.next();
		ShopObjectType<?> value = ShopkeepersPlugin.getInstance().getShopObjectTypeRegistry().match(argument);
		if (value == null) {
			throw this.invalidArgumentError(argument);
		}
		return value;
	}

	@Override
	public List<String> complete(CommandInput input, CommandContext context, CommandArgs args) {
		if (args.getRemainingSize() == 1) {
			List<String> suggestions = new ArrayList<>();
			String partialArg = StringUtils.normalize(args.next());
			for (ShopObjectType<?> shopObjectType : ShopkeepersPlugin.getInstance().getShopObjectTypeRegistry().getRegisteredTypes()) {
				if (suggestions.size() >= MAX_SUGGESTIONS) break;
				String displayName = shopObjectType.getDisplayName();
				displayName = StringUtils.normalizeKeepCase(displayName);
				String displayNameNorm = displayName.toLowerCase(Locale.ROOT);
				if (displayNameNorm.startsWith(partialArg)) {
					suggestions.add(displayName);
				} else {
					String identifier = shopObjectType.getIdentifier();
					if (identifier.startsWith(partialArg)) {
						suggestions.add(identifier);
					}
				}
			}
			return Collections.unmodifiableList(suggestions);
		}
		return Collections.emptyList();
	}
}
