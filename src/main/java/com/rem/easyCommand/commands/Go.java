package com.rem.easyCommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rem.easyCommand.domain.Result;
import com.rem.easyCommand.domain.UserHome;
import com.rem.easyCommand.enmus.DimensionEnums;
import com.rem.easyCommand.utils.UserHomeUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static net.minecraft.server.command.CommandManager.argument;

/**
 * Go command.
 *
 * @author REM @Date 2022/01/22
 */
public class Go {

  private static final String SPAWN = "spawn";

  /**
   * Register command.
   *
   * @param dispatcher dispatcher
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(
        CommandManager.literal("go")
            .then(
                argument("HomeName", StringArgumentType.word())
                    .suggests(
                        (context, builder) -> {
                          ServerPlayerEntity player = context.getSource().getPlayer();
                          List<String> list =
                              UserHomeUtils.findHomeListById(player.getUuidAsString());
                          list.forEach(builder::suggest);
                          builder.suggest(SPAWN);
                          return builder.buildFuture();
                        })
                    .executes(Go::teleport)));
  }

  /**
   * Teleport.
   *
   * @param context ctx
   * @return int
   * @throws CommandSyntaxException cse
   */
  public static int teleport(CommandContext<ServerCommandSource> context)
      throws CommandSyntaxException {
    String homeName = StringArgumentType.getString(context, "HomeName");
    ServerPlayerEntity player = context.getSource().getPlayer();
    World world = context.getSource().getWorld();
    Set<RegistryKey<World>> worldKeys = context.getSource().getWorldKeys();
    int exp = player.experienceLevel;
    if (SPAWN.equals(homeName)) {
      worldKeys.forEach(
          (registryKey) -> {
            if (Objects.equals(
                registryKey.getValue(),
                new Identifier(DimensionEnums.OVER_WORLD.getIdentifier()))) {
              ServerWorld overWorld =
                  Objects.requireNonNull(player.getServer()).getWorld(registryKey);
              player.teleport(
                  overWorld,
                  world.getLevelProperties().getSpawnX(),
                  world.getLevelProperties().getSpawnY(),
                  world.getLevelProperties().getSpawnZ(),
                  player.getYaw(),
                  player.getPitch());
            }
          });
      player.setExperienceLevel(exp);
      context.getSource().sendFeedback(new TranslatableText("tp.success"), false);
      return Result.SINGLE_SUCCESS;
    }

    String userId = String.valueOf(player.getUuid());
    UserHome homeInfo = UserHomeUtils.findByHomeName(homeName, userId);
    if (null == homeInfo) {
      context.getSource().sendError(new TranslatableText("home.does.not.exist"));
      return Result.SINGLE_FAIL;
    }

    worldKeys.forEach(
        (registryKey) -> {
          if (Objects.equals(registryKey.getValue(), new Identifier(homeInfo.getWorld()))) {
            ServerWorld worldInfo =
                Objects.requireNonNull(player.getServer()).getWorld(registryKey);
            player.teleport(
                worldInfo,
                homeInfo.getX(),
                homeInfo.getY(),
                homeInfo.getZ(),
                player.getYaw(),
                player.getPitch());
          }
        });
    player.setExperienceLevel(exp);
    context.getSource().sendFeedback(new TranslatableText("tp.success"), false);
    return Result.SINGLE_SUCCESS;
  }
}
