//DEPS org.seleniumhq.selenium:selenium-java:4.17.0

/**
 * This script determines the location of your firefox profile directory.
 *
 * <h2>How it works</h2>
 * <p>It uses Selenium to launch an instance of firefox, navigate to
 * "about:profiles" page, and uses a CSS selector to extract the default
 * profile path from that page.</p>
 *
 * <p>Q. Why not simply extract the default profile location from
 * `~/.mozilla/firefox/profiles.ini`?</p>
 * <p>A. Because interpretting that file has become increasingly complex. It's
 * now necessary to also consider `~/.mozilla/firefox/installs.ini`. I couldn't
 * find an clear explanation. I therefore decided it is best to query firefox
 * itself for this. This way, no error-prone guesswork is involved.</p>
 *
 * <p>Caveat: The launching of firefox is an expensive operation; it takes a
 * few seconds. Ideally caching would be utilized. (See
 * <code>FirefoxProfileDirWithCaching.java</code> for my first attempt at
 * adding caching.  I wasn't happy with the complexity it added. More lines of
 * code were devoted to supporting caching, than for the essential
 * functionality. I suspect the caching functionality can be factored out into
 * a general purpose helper class. TODO)</p>
 */
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxProfileDir {

  public static void main(String[] args) {
    FirefoxOptions options = new FirefoxOptions();
    options.addArguments("-headless");
    WebDriver driver = new FirefoxDriver(options);
    try {
      driver.get("about:profiles");
      WebElement elem = driver.findElement(By.cssSelector("tr:has(td[data-l10n-id='profiles-yes']) + tr > td"));
      JavascriptExecutor js = (JavascriptExecutor) driver;
      String profileDir = (String) js.executeScript("return arguments[0].childNodes[0].textContent;", elem);
      System.out.println(profileDir);
    } finally {
      driver.quit();
    }
  }
}
