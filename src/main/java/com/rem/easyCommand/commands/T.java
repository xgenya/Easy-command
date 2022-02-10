package com.rem.easyCommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rem.easyCommand.domain.Result;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import static net.minecraft.server.command.CommandManager.argument;

/**
 * T command.
 *
 * @author REM
 * @date 2022/02/09
 */
public class T {

  /**
   * Register command.
   *
   * @param dispatcher dispatcher
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(
        CommandManager.literal("t")
            .then(argument("playerName", EntityArgumentType.player()).executes(T::teleport)));
  }

  /**
   * Teleport.
   *
   * @param context ctx
   * @return int
   * @throws CommandSyntaxException exception
   */
  public static int teleport(CommandContext<ServerCommandSource> context)
      throws CommandSyntaxException {
    ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "playerName");
    int exp = player.experienceLevel;
    context
        .getSource()
        .getPlayer()
        .teleport(
            player.getWorld(),
            player.getX(),
            player.getY(),
            player.getZ(),
            player.getYaw(),
            player.getPitch());
    player.setExperienceLevel(exp);
    context.getSource().sendFeedback(new TranslatableText("tp.success"), false);
    return Result.SINGLE_SUCCESS;
  }
}
