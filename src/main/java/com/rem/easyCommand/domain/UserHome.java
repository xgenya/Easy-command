package com.rem.easyCommand.domain;

/**
 * User home entity.
 *
 * @author REM
 * @date 2022/01/22
 */
public class UserHome {

  /** HomeName */
  public String homeName;

  /** X coordinate */
  public double x;

  /** Y coordinate */
  public double y;

  /** Z coordinate */
  public double z;

  /** Dimension */
  public String world;

  public String getHomeName() {
    return homeName;
  }

  public void setHomeName(String homeName) {
    this.homeName = homeName;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getZ() {
    return z;
  }

  public void setZ(double z) {
    this.z = z;
  }

  public String getWorld() {
    return world;
  }

  public void setWorld(String world) {
    this.world = world;
  }
}
