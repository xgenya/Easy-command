package com.rem.easyCommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.rem.easyCommand.domain.Result;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;

/**
 * Day command.
 *
 * @author REM
 * @date 2022/02/09
 */
public class Day {

  /**
   * Register command.
   *
   * @param dispatcher dispatcher
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(CommandManager.literal("day").executes(Day::setDay));
  }

  /**
   * Set time to daytime.
   *
   * @param context context
   * @return int
   */
  public static int setDay(CommandContext<ServerCommandSource> context) {
    ServerWorld world = context.getSource().getWorld();
    world.setTimeOfDay(2000);
    context.getSource().sendFeedback(new TranslatableText("day"), false);
    return Result.SINGLE_SUCCESS;
  }
}
