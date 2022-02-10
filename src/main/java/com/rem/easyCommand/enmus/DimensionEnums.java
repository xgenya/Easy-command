package com.rem.easyCommand.enmus;

/**
 * Dimension namespace.
 *
 * @author REM
 * @date 2022/01/23
 */
public enum DimensionEnums {
  OVER_WORLD("minecraft:overworld"),
  THE_NETHER("minecraft:the_nether"),
  THE_END("minecraft:the_end");

  private final String identifier;

  private DimensionEnums(String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }
}
