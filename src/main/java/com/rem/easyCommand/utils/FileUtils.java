package com.rem.easyCommand.utils;

import java.io.File;

/**
 * FileUtils.
 *
 * @author REM
 * @date 2022/02/09
 */
public class FileUtils {

  /** checkPath. */
  public static void checkPath(String path) {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  /**
   * Get all files in the directory.
   *
   * @param path path
   * @return {@link File[]}
   */
  public static File[] getFiles(String path) {
    File file = new File(path);
    return file.listFiles();
  }

  /**
   * Read json file.
   *
   * @param file file
   * @return {@link String}
   */
  public static String readFile(File file) {
    StringBuilder sb = new StringBuilder();
    try {
      java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file));
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  /**
   * Write file.
   *
   * @param path path
   * @param content content
   */
  public static void writeFile(String path, String content) {
    try {
      File file = new File(path);
      if (!file.exists()) {
        file.createNewFile();
      }
      java.io.FileWriter fw = new java.io.FileWriter(file.getAbsoluteFile());
      java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
      bw.write(content);
      bw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
