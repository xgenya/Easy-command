package com.rem.easyCommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rem.easyCommand.domain.Result;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Back command.
 *
 * @author REM
 * @date 2022/02/04
 */
public class Back {

  /**
   * Register command.
   *
   * @param dispatcher dispatcher
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(CommandManager.literal("back").executes(Back::back));
  }

  /**
   * Back previous location.
   *
   * @param context context
   * @return int
   * @throws CommandSyntaxException exception
   */
  public static int back(CommandContext<ServerCommandSource> context)
      throws CommandSyntaxException {
    ServerPlayerEntity player = context.getSource().getPlayer();
    return Result.SINGLE_SUCCESS;
  }
}
