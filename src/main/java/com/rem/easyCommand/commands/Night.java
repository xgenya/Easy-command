package com.rem.easyCommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.rem.easyCommand.domain.Result;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;

/**
 * Night command.
 *
 * @author REM
 * @date 2022/02/09
 */
public class Night {

  /**
   * Register command.
   *
   * @param dispatcher dispatcher
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(CommandManager.literal("night").executes(Night::setNight));
  }

  /**
   * Set time to night.
   *
   * @param context context
   * @return int
   */
  public static int setNight(CommandContext<ServerCommandSource> context) {
    ServerWorld world = context.getSource().getWorld();
    world.setTimeOfDay(12000);
    context.getSource().sendFeedback(new TranslatableText("night"), false);
    return Result.SINGLE_SUCCESS;
  }
}
