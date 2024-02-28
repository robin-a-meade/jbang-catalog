//DEPS org.seleniumhq.selenium:selenium-java:4.17.0

/**
 * This script determines the location of your firefox profile directory.
 *
 * How it works
 * ============
 * It launches an instance of firefox and searches its "about:profiles" page.
 *
 * Why not just figure it out by accessing `~/.mozilla/profiles.ini`? Because
 * interpretting this file has become increasingly complex. It may now be
 * necessary to also access `~/.mozilla/installs.ini`. I couldn't find a clear
 * explanation. I decided it was better to query firefox itself for this.
 *
 * Caching
 * =======
 * The answer will be cached, because launching firefox is an expensive operation.
 *
 * The answer will be cached at:
 *
 *    <user cache dir>/firefoxprofdir/<hashcode>/location.txt
 *
 * where <user cache dir> is ${XDG_CACHE_HOME:-~/.cache}
 * and <hashcode> is the sha256 hash of `cat ~/.mozilla/{profiles,installs}.ini`
 *
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class FirefoxProfileDirectory {
    public static void main(String[] args) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        WebDriver driver = new FirefoxDriver(options);
        try {
            driver.get("about:profiles");
            WebElement elem = driver.findElement(By.cssSelector("tr:has(td[data-l10n-id='profiles-yes']) + tr > td"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String profileName = (String) js.executeScript("return arguments[0].childNodes[0].textContent;", elem);
            System.out.println(profileName);
        } finally {
            driver.quit();
        }
    }
}
