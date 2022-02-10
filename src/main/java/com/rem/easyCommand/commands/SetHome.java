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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.argument;

/**
 * Set home command.
 *
 * @author REM
 * @date 2022/01/23
 */
public class SetHome {

  public static final Logger logger = LoggerFactory.getLogger("easy-command");

  /**
   * Register command.
   *
   * @param dispatcher dispatcher
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(
        CommandManager.literal("setHome")
            .then(argument("HomeName", StringArgumentType.word()).executes(SetHome::set)));
  }

  /**
   * Set home.
   *
   * @param context context
   * @return int
   * @throws CommandSyntaxException exception
   */
  public static int set(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
    String homeName = StringArgumentType.getString(context, "HomeName");
    ServerPlayerEntity player = context.getSource().getPlayer();
    String userId = String.valueOf(player.getUuid());
    UserHome homeInfo = UserHomeUtils.findByHomeName(homeName, userId);
    if (null == homeInfo) {
      UserHome userHome = new UserHome();
      userHome.setHomeName(homeName);
      userHome.setX(player.getX());
      userHome.setY(player.getY());
      userHome.setZ(player.getZ());
      userHome.setWorld(player.getWorld().getRegistryKey().getValue().toString());
      UserHomeUtils.addHome(userId, userHome);
      context.getSource().sendFeedback(new TranslatableText("home.set.success"), false);
      return Result.SINGLE_SUCCESS;
    }
    context.getSource().sendError(new TranslatableText("home.existed"));
    return Result.SINGLE_FAIL;
  }
}
