package com.rem.easyCommand.utils;

import com.google.gson.Gson;
import com.rem.easyCommand.domain.UserHome;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User home utils.
 *
 * @author REM
 * @date 2022/02/09
 */
public class UserHomeUtils {

  public static final Logger logger = LoggerFactory.getLogger("easy-command");

  /** Path */
  public static final String PATH = FabricLoader.getInstance().getConfigDir() + "/EasyCommand/";

  /** File type */
  public static final String FILE_TYPE = ".json";

  public static class UserData {

    public static HashMap<String, List<UserHome>> userData;

    public static HashMap<String, List<UserHome>> getUserData() {
      if (userData == null) {
        userData = new HashMap<>();
      }
      return userData;
    }

    public static void setUserData(List<UserHome> userHome, String userId) {
      getUserData().remove(userId);
      getUserData().put(userId, userHome);
    }

    public static List<UserHome> getUserData(String userId) {
      return getUserData().get(userId);
    }

    public static void deleteUserData(String userId) {
      getUserData().remove(userId);
    }
  }

  /** Initialize the user home data */
  public static void init() {
    FileUtils.checkPath(PATH);
    File[] files = FileUtils.getFiles(PATH);
    if (files != null) {
      for (File file : files) {
        if (file.getName().endsWith(FILE_TYPE)) {
          String userId = file.getName().replace(FILE_TYPE, "");
          String jsonString = FileUtils.readFile(file);
          List<UserHome> users =
              new Gson()
                  .fromJson(
                      jsonString,
                      new com.google.gson.reflect.TypeToken<List<UserHome>>() {}.getType());
          UserData.setUserData(users, userId);
        }
      }
    }
  }

  /**
   * Query coordinates based on username and home name
   *
   * @return {@link UserHome}
   */
  public static UserHome findByHomeName(String homeName, String userId) {
    List<UserHome> homeList = UserData.getUserData().get(userId);
    if (homeList != null) {
      for (UserHome userHome : homeList) {
        if (userHome.getHomeName().equals(homeName)) {
          return userHome;
        }
      }
    }
    return null;
  }

  /**
   * Query the home set by the user according to the user ID
   *
   * @param userId userId
   * @return {@link List}<{@link UserHome}>
   */
  public static List<String> findHomeListById(String userId) {
    List<String> list = new ArrayList<>();
    List<UserHome> userData = UserData.getUserData(userId);
    if (userData != null) {
      userData.forEach(userHome -> list.add(userHome.getHomeName()));
    }
    return list;
  }

  /**
   * Add a home.
   *
   * @param userHome userHome
   * @param userId userId
   */
  public static void addHome(String userId, UserHome userHome) {
    List<UserHome> userHomeList = UserData.getUserData(userId);
    if (userHomeList == null) {
      userHomeList = new ArrayList<>();
    }
    userHomeList.add(userHome);
    UserData.setUserData(userHomeList, userId);
    saveUserData(userId, userHomeList);
  }

  /**
   * Delete a home.
   *
   * @param homeName homeName
   * @param userId userId
   */
  public static void delHome(String userId, String homeName) {
    List<UserHome> userHomeList = UserData.getUserData(userId);
    if (userHomeList == null) {
      userHomeList = new ArrayList<>();
    } else {
      userHomeList.removeIf(userHome -> userHome.getHomeName().equals(homeName));
    }
    saveUserData(userId, userHomeList);
  }

  /**
   * Save user data.
   *
   * @param userId userId
   * @param list list
   */
  public static void saveUserData(String userId, List<UserHome> list) {

    // Check file does it exist. Create if it doesn't exist.
    File file = new File(getPath(userId));
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
        logger.error("Create file failed.", e);
      }
    }
    FileUtils.writeFile(getPath(userId), new Gson().toJson(list));
  }

  /**
   * Get path.
   *
   * @param userId userId
   * @return {@link String}
   */
  public static String getPath(String userId) {
    return PATH + userId + FILE_TYPE;
  }
}
