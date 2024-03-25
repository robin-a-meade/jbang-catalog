//DEPS dev.jbang:jbang-cli:0.115.0

import java.util.Arrays;
import java.util.List;

import dev.jbang.dependencies.DependencyResolver;
import dev.jbang.dependencies.ModularClassPath;

public class DepsToClasspath {
  public static void main(String[] args) {
    List<String> deps = Arrays.asList(args);
    DependencyResolver resolver = new DependencyResolver();
    resolver.addDependencies(deps);
    ModularClassPath mcp = resolver.resolve();
    System.out.println(mcp.getClassPath());
  }
}
