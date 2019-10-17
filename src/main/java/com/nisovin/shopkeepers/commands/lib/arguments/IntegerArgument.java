package com.nisovin.shopkeepers.commands.lib.arguments;

import java.util.Collections;
import java.util.List;

import com.nisovin.shopkeepers.commands.lib.ArgumentParseException;
import com.nisovin.shopkeepers.commands.lib.CommandArgs;
import com.nisovin.shopkeepers.commands.lib.CommandArgument;
import com.nisovin.shopkeepers.commands.lib.CommandContext;
import com.nisovin.shopkeepers.commands.lib.CommandInput;
import com.nisovin.shopkeepers.util.ConversionUtils;

public class IntegerArgument extends CommandArgument<Integer> {

	public IntegerArgument(String name) {
		super(name);
	}

	@Override
	public Integer parseValue(CommandInput input, CommandArgs args) throws ArgumentParseException {
		if (!args.hasNext()) {
			throw this.missingArgumentError();
		}
		String argument = args.next();
		Integer value = ConversionUtils.parseInt(argument);
		if (value == null) {
			throw this.invalidArgumentError(argument);
		}
		return value;
	}

	@Override
	public List<String> complete(CommandInput input, CommandContext context, CommandArgs args) {
		return Collections.emptyList();
	}
}
