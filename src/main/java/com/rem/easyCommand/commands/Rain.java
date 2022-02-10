package com.rem.easyCommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.rem.easyCommand.domain.Result;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

/**
 * Rain command.
 *
 * @author REM
 * @date 2022/02/09
 */
public class Rain {
  /**
   * register command.
   *
   * @param dispatcher dispatcher
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(CommandManager.literal("rain").executes(Rain::rain));
  }

  /**
   * Set the weather to rain.
   *
   * @param context context
   * @return int
   */
  public static int rain(CommandContext<ServerCommandSource> context) {
    context.getSource().getWorld().setWeather(0, 6000, true, false);
    context.getSource().sendFeedback(new TranslatableText("rain"), false);
    return Result.SINGLE_SUCCESS;
  }
}
