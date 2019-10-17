package com.nisovin.shopkeepers.commands.lib.arguments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nisovin.shopkeepers.commands.lib.ArgumentParseException;
import com.nisovin.shopkeepers.commands.lib.CommandArgs;
import com.nisovin.shopkeepers.commands.lib.CommandArgument;
import com.nisovin.shopkeepers.commands.lib.CommandContext;
import com.nisovin.shopkeepers.commands.lib.CommandInput;
import com.nisovin.shopkeepers.util.ConversionUtils;
import com.nisovin.shopkeepers.util.Validate;

public class EnumArgument<T extends Enum<T>> extends CommandArgument<T> {

	private final Class<T> clazz;

	public EnumArgument(String name, Class<T> clazz) {
		super(name);
		Validate.notNull(clazz);
		this.clazz = clazz;
	}

	@Override
	public T parseValue(CommandInput input, CommandArgs args) throws ArgumentParseException {
		if (!args.hasNext()) {
			throw this.missingArgumentError();
		}
		String argument = args.next();
		T value = ConversionUtils.parseEnum(clazz, argument);
		if (value == null) {
			throw this.invalidArgumentError(argument);
		}
		return value;
	}

	@Override
	public List<String> complete(CommandInput input, CommandContext context, CommandArgs args) {
		if (args.getRemainingSize() == 1) {
			List<String> suggestions = new ArrayList<>();
			String partialArg = args.next().toUpperCase();
			for (T value : clazz.getEnumConstants()) {
				if (suggestions.size() >= MAX_SUGGESTIONS) break;
				if (value.name().toUpperCase().startsWith(partialArg)) {
					suggestions.add(value.name());
				}
			}
			return Collections.unmodifiableList(suggestions);
		}
		return Collections.emptyList();
	}
}
