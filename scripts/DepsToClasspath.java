//DEPS dev.jbang:jbang-cli:0.119.0
//DEPS org.apache.maven:maven-model:3.9.6

// NAME
//      deps-to-classpath - resolve dependencies and print classpath
//
// SYNOPSIS
//      deps-to-classpath DEP...
//
//      (Assuming alias 'deps-to-classpath' was added and installed
//      on the PATH)
//
// SEE ALSO
//      gist for deps_to_path, which is a wrapper


// IMPLEMENTATION NOTES
//
// NOTE: I had to explicitly add the "maven-model:3.9.6" dependency, or I'd get
// this exception:
//
/*
Exception in thread "main" java.lang.NoSuchMethodError: 'boolean org.apache.maven.model.Model.isChildProjectUrlInheritAppendPath()'
        at org.apache.maven.model.merge.MavenModelMerger.mergeModel_Url(MavenModelMerger.java:94)
        at org.apache.maven.model.merge.ModelMerger.mergeModel(ModelMerger.java:136)
        at org.apache.maven.model.merge.MavenModelMerger.mergeModel(MavenModelMerger.java:72)
        at org.apache.maven.model.merge.ModelMerger.merge(ModelMerger.java:121)
        at org.apache.maven.model.inheritance.DefaultInheritanceAssembler.assembleModelInheritance(DefaultInheritanceAssembler.java:66)
        at org.apache.maven.model.building.DefaultModelBuilder.assembleInheritance(DefaultModelBuilder.java:709)
        at org.apache.maven.model.building.DefaultModelBuilder.build(DefaultModelBuilder.java:369)
        at org.apache.maven.model.building.DefaultModelBuilder.build(DefaultModelBuilder.java:243)
        at org.apache.maven.repository.internal.DefaultArtifactDescriptorReader.loadPom(DefaultArtifactDescriptorReader.java:284)
        at org.apache.maven.repository.internal.DefaultArtifactDescriptorReader.readArtifactDescriptor(DefaultArtifactDescriptorReader.java:175)
        at org.eclipse.aether.internal.impl.collect.df.DfDependencyCollector.resolveCachedArtifactDescriptor(DfDependencyCollector.java:382)
        at org.eclipse.aether.internal.impl.collect.df.DfDependencyCollector.getArtifactDescriptorResult(DfDependencyCollector.java:368)
        at org.eclipse.aether.internal.impl.collect.df.DfDependencyCollector.processDependency(DfDependencyCollector.java:218)
        at org.eclipse.aether.internal.impl.collect.df.DfDependencyCollector.processDependency(DfDependencyCollector.java:156)
        at org.eclipse.aether.internal.impl.collect.df.DfDependencyCollector.process(DfDependencyCollector.java:138)
        at org.eclipse.aether.internal.impl.collect.df.DfDependencyCollector.doCollectDependencies(DfDependencyCollector.java:108)
        at org.eclipse.aether.internal.impl.collect.DependencyCollectorDelegate.collectDependencies(DependencyCollectorDelegate.java:222)
        at org.eclipse.aether.internal.impl.collect.DefaultDependencyCollector.collectDependencies(DefaultDependencyCollector.java:87)
        at org.eclipse.aether.internal.impl.DefaultRepositorySystem.resolveDependencies(DefaultRepositorySystem.java:328)
        at dev.jbang.dependencies.ArtifactResolver.resolve(ArtifactResolver.java:200)
        at dev.jbang.dependencies.DependencyUtil.resolveDependencies(DependencyUtil.java:109)
        at dev.jbang.dependencies.DependencyResolver.resolve(DependencyResolver.java:66)
        at DepsToClasspath.main(DepsToClasspath.java:18)
*/

// To gain insights into what is going on, I executed:
//
//     jbang info classpath dev.jbang:jbang-cli:0.115.0
//
// The resulting classpath included org.apache.maven:maven-model:3.5.3 and,
// subsequently, org.apache.maven:maven-model-builder:3.9.6 (which transitively
// depends on org.apache.maven:maven-model:3.9.6).
//
// Compare the 3.5.3 and 3.9.6 API docs for org.apache.maven.model.Model:
// https://maven.apache.org/ref/3.5.3/maven-model/apidocs/org/apache/maven/model/Model.html
// https://maven.apache.org/ref/3.9.6/maven-model/apidocs/org/apache/maven/model/Model.html
//
// You'll notice that the 3.5.3 version of this class does not include the
// `isChildProjectUrlInheritAppendPath()` method.
//
// This suggests that the exception is due to java picking up the older version
// of this class that doesn't have the expected method. I'm not sure why. I
// thought Maven version conflict resolution picks the latest version.  I need
// to look into this resolution algorithm more. TODO.
//
// Explicitly declaring a dependency on org.apache.maven:maven-model:3.9.6
// fixes this situation. The older org.apache.maven:maven-model:3.5.3 is no
// longer present on the classpath. I confirmed this by executing `jbang info
// classpath` on the alias:
//
//     jbang info classpath scripts/DepsToClasspath.java
//


import java.util.Arrays;
import java.util.List;

import dev.jbang.dependencies.DependencyResolver;
import dev.jbang.dependencies.ModularClassPath;

public class DepsToClasspath {
  public static void main(String[] args) {
    // Based on commit:
    // https://github.com/quintesse/jbang/commit/7e6f4b387f279c458210217cb391a8ca83c45cd7
    // for issue:
    // Support generating classpaths embedded on command line · Issue #1703 · jbangdev/jbang
    // https://github.com/jbangdev/jbang/issues/1703

    List<String> deps = Arrays.asList(args);
    DependencyResolver resolver = new DependencyResolver();
    resolver.addDependencies(deps);
    ModularClassPath mcp = resolver.resolve();
    System.out.println(mcp.getClassPath());
  }
}
