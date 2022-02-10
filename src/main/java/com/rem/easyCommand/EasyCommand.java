package com.rem.easyCommand;

import com.rem.easyCommand.commands.*;
import com.rem.easyCommand.utils.UserHomeUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasyCommand implements ModInitializer {

  public static final Logger logger = LoggerFactory.getLogger("easy-command");

  @Override
  public void onInitialize() {
    UserHomeUtils.init();
    CommandRegistrationCallback.EVENT.register((dispatcher, b) -> SetHome.register(dispatcher));
    CommandRegistrationCallback.EVENT.register((dispatcher, b) -> Go.register(dispatcher));
    CommandRegistrationCallback.EVENT.register((dispatcher, b) -> Home.register(dispatcher));
    CommandRegistrationCallback.EVENT.register((dispatcher, b) -> T.register(dispatcher));
    CommandRegistrationCallback.EVENT.register((dispatcher, b) -> Spawn.register(dispatcher));
    CommandRegistrationCallback.EVENT.register((dispatcher, b) -> Day.register(dispatcher));
    CommandRegistrationCallback.EVENT.register((dispatcher, b) -> Sun.register(dispatcher));
    CommandRegistrationCallback.EVENT.register((dispatcher, b) -> Rain.register(dispatcher));
    CommandRegistrationCallback.EVENT.register((dispatcher, b) -> Night.register(dispatcher));
  }
}
