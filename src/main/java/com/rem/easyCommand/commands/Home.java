package com.rem.easyCommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rem.easyCommand.domain.Result;
import com.rem.easyCommand.domain.UserHome;
import com.rem.easyCommand.utils.UserHomeUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;

/**
 * Home
 *
 * @author REM
 * @date 2022/01/23
 */
public class Home {

  /**
   * Register command.
   *
   * @param dispatcher dispatcher
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(
        CommandManager.literal("home")
            .then(
                argument("HomeName", StringArgumentType.word())
                    .suggests(
                        (context, builder) -> {
                          ServerPlayerEntity player = context.getSource().getPlayer();
                          List<String> list =
                              UserHomeUtils.findHomeListById(player.getUuidAsString());
                          list.forEach(builder::suggest);
                          return builder.buildFuture();
                        })
                    .then(CommandManager.literal("remove").executes(Home::remove))));
  }

  /**
   * Remove home.
   *
   * @param context ctx
   * @return int
   * @throws CommandSyntaxException cse
   */
  public static int remove(CommandContext<ServerCommandSource> context)
      throws CommandSyntaxException {
    String homeName = StringArgumentType.getString(context, "HomeName");
    ServerPlayerEntity player = context.getSource().getPlayer();
    String userId = String.valueOf(player.getUuid());
    UserHome homeInfo = UserHomeUtils.findByHomeName(homeName, userId);
    if (null == homeInfo) {
      context.getSource().sendError(new TranslatableText("delete.error"));
      return Result.SINGLE_FAIL;
    }
    UserHomeUtils.delHome(userId, homeName);
    context.getSource().sendFeedback(new TranslatableText("delete.success"), false);
    return Result.SINGLE_SUCCESS;
  }
}
