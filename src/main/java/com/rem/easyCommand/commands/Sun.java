package com.rem.easyCommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.rem.easyCommand.domain.Result;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

/**
 * Sun command.
 *
 * @author REM
 * @date 2022/02/09
 */
public class Sun {

  /**
   * Registers command.
   *
   * @param dispatcher dispatcher
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(CommandManager.literal("sun").executes(Sun::sun));
  }

  /**
   * Set weather to sunnyDay.
   *
   * @param context context
   * @return int
   */
  public static int sun(CommandContext<ServerCommandSource> context) {
    context.getSource().getWorld().setWeather(10000, 0, false, false);
    context.getSource().sendFeedback(new TranslatableText("sun"), false);
    return Result.SINGLE_SUCCESS;
  }
}
