//DEPS org.seleniumhq.selenium:selenium-java:4.17.0

/**
 * This script determines the location of your firefox profile directory.
 *
 * <h2>How it works</h2>
 * <p>It launches an instance of firefox and searches its "about:profiles" page.</p>
 *
 * <p>Q. Why not just extract it from `~/.mozilla/firefox/profiles.ini`?</p>
 * <p>A. Because interpretting that file has become increasingly complex. It's now
 * necessary to also consider `~/.mozilla/firefox/installs.ini`. I couldn't
 * find an cleaer explanation. I therefore decided it is best to query firefox
 * itself for this.</p>
 *
 * <h2>Caching</h2>
 *
 * <p>The answer will be cached, because launching firefox is an expensive operation.</p>
 *
 * <p>The answer will be cached at:</p>
 * 
 * <pre>{@code
 *    <user cache dir>/firefoxprofdir/<hashcode>/location.txt
 * }</pre>
 * 
 * <p>where <user cache dir> is ${XDG_CACHE_HOME:-~/.cache}
 * and <hashcode> is the sha256 hash of `cat ~/.mozilla/{profiles,installs}.ini`</p>
 *
 */
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class firefoxprofdir {

  static boolean fresh = false;
  static boolean verbose = false;

  static void log(String s) {
    if (verbose) {
      System.err.println(s);
    }
  }

  /////////////////////////////////////////////////////////////
  // main logic
  /////////////////////////////////////////////////////////////

  static String queryFirefoxForProfileDir() {
    FirefoxOptions options = new FirefoxOptions();
    options.addArguments("-headless");
    WebDriver driver = new FirefoxDriver(options);
    try {
      driver.get("about:profiles");
      WebElement elem = driver.findElement(By.cssSelector("tr:has(td[data-l10n-id='profiles-yes']) + tr > td"));
      JavascriptExecutor js = (JavascriptExecutor) driver;
      String profileName = (String) js.executeScript("return arguments[0].childNodes[0].textContent;", elem);
      return profileName;
    } finally {
      System.out.println("finally");
      driver.quit();
    }
  }

  /**
   * Produces same sha-256 hash value as doing:
   * cat ~/.mozilla/firefox/{profiles,installs}.ini | sha256sum
   */
  static String calculateHashKey() {
    String homeDir = System.getProperty("user.home");
    String[] filePaths = {
        homeDir + "/.mozilla/firefox/profiles.ini",
        homeDir + "/.mozilla/firefox/installs.ini"
    };
    try {
      return calculateSha256(filePaths);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error finding SHA-256 algorithm", e);
    } catch (IOException e) {
      throw new RuntimeException("Error reading Firefox configuration files", e);
    }
  }

  /**
   * Returns path to the user's cache home.
   * 
   * This will return the value of environment variable XDG_CACHE_HOME
   * with fallback to `$HOME/.cache`.
   */
  Path pathToCacheHome() {
    // Check for XDG_CACHE_HOME environment variable
    String xdgCacheHome = System.getenv("XDG_CACHE_HOME");
    if (xdgCacheHome != null) {
      return Paths.get(xdgCacheHome);
    }

    // Fallback to default location on most systems
    String userHome = System.getProperty("user.home");
    return Paths.get(userHome, ".cache");
  }

  static String pathToCachedFile() {

  }

  static String lookupInCache(String hash) {
    return "answerFromCache";
  }

  public static void main(String[] args) {
    String usage = "usage: firefoxprofdir [-v] [--fresh]";
    log("main");

    for (String arg : args) {
      switch (arg) {
        case "--fresh":
          fresh = true;
          break;
        case "-v":
          verbose = true;
          break;
        default:
          System.err.println("Invalid option: " + arg);
          System.err.println(usage);
          System.exit(1);
      }
    }

    String hashKey = calculateHashKey();
    log("hashKey: " + hashKey);
    String cachedAnswer;
    if ((cachedAnswer = lookupInCache(hashKey)) != null) {
      log("Cache hit occurred. Returning cached value");
      System.out.println(cachedAnswer);
      return;
    }
    // System.err.println("Cache hit: false");
    // String answer = queryFirefoxForProfileDir();
    // storeInCache(hashKey, answer);
    System.out.println("answer");
  }

  /////////////////////////////////////////////////////////////
  // General utility functions
  /////////////////////////////////////////////////////////////

  static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (byte b : hash) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

  static String calculateSha256(String[] filePaths) throws IOException, NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");

    for (String filePath : filePaths) {
      try (FileInputStream fis = new FileInputStream(filePath);
          BufferedInputStream bis = new BufferedInputStream(fis)) {
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) != -1) {
          digest.update(buffer, 0, bytesRead);
        }
      }
    }

    byte[] hash = digest.digest();
    return bytesToHex(hash);
  }

}
