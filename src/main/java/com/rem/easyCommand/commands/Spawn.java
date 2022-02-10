package com.rem.easyCommand.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.rem.easyCommand.domain.Result;
import com.rem.easyCommand.enmus.DimensionEnums;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Set;

/**
 * Spawn Command.
 *
 * @author REM
 * @date 2022/02/09
 */
public class Spawn {

  /**
   * Register command.
   *
   * @param dispatcher dispatcher
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(
        CommandManager.literal("spawn")
            .executes(
                (context) -> {
                  ServerPlayerEntity player = context.getSource().getPlayer();
                  World world = context.getSource().getWorld();
                  int exp = player.experienceLevel;
                  Set<RegistryKey<World>> worldKeys = context.getSource().getWorldKeys();
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
                }));
  }
}
