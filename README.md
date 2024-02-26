# jbang-catalog

## NextActions

### Implement the `-r` option to the `generate_pom_files` bash script

This option should register the genereated pom files in `.github/dependabot.yml`

I created template file to use: `dependabot_stanza_template.yml`.

This process will apply the template file as it iterates over the list of genereated pom files, resulting in an appropriate `.github/dependabot.yml` file.

### Handle `//DEPS` dependency declarations

I initially thought that the alias metadata in `jbang-catalog.json` included
any dependencies declared in `//DEPS` comments in script files, but it turns
out that this metadata includes only "extra" dependencies (given as arguments
to `--deps` options to the `jbang alias add` command.

So, I'll need to way to determine dependencies from `//DEPS` comments.

Short term, I'll use `awk`, `sed`, or `grep` to extract the `//DEPS` comments
from script files.

The long term solution would be to have functionality for this need built into jbang.

Actually, the `jbang export` command might fit the bill. In my testing,
however, the pom it generates included transitive dependencies. It should only
include top-level dependencies.

For example, I tried this:

```
readonly TEMP_DIR=$(mktemp -d)
jbang export mavenrepo --group=com.example -O "$TEMP_DIR" scripts/saxonhe.java
```

The resulting pom included declarations for these dependencies:

- org.xmlresolver:xmlresolver
- org.apache.httpcomponents.client:httpclient5

but these are _transitive_ dependencies of `net.sf.saxon:Saxon-HE`.

## Aliases

### jargs

Prints its command line arguments. Useful for testing.

We added the alias to the catalog using:

```
jbang alias add ./scripts/jargs.java
```

Example execution of the alias:

```console
[user@laptop ~] jbang run jargs@robin-a-meade one two three
3 args: <one> <two> <three>
```

It can be installed as an app:

```console
[user@laptop ~] jbang app install jargs@robin-a-meade
[user@laptop ~] type -a jargs
jargs is /home/user/.jbang/bin/jargs
[user@laptop ~] jargs one two three
3 args: <one> <two> <three>
```

### sqlline_test

This is an alias for [`sqlline`](https://github.com/julianhyde/sqlline).

We added the alias to the catalog using:

```
jbang alias add \
  --name=sqlline_test \
  --deps com.oracle.database.jdbc:ojdbc8:23.2.0.0 \
  --deps com.h2database:h2:2.1.214 \
  sqlline:sqlline:1.11.0
```

We purposely didn't use the latest releases of dependencies because we want to
test the ability to be alerted to the availability of new releases of
dependencies.

### saxonhe

We added the alias to the catalog using:

```
jbang alias add ./scripts/saxonhe.java
```

## How to check for updates to dependencies?

Discussion:

- Ways to check for updates to alias dependencies
  https://github.com/orgs/jbangdev/discussions/1751

### Use renovate template

Mentioned in above discussion:  
Run `jbang init -t jbangcatalog@jbanghub mycatalog` and you'll get a project with a `renovate.json` that will find updates in //Deps and catalog script refs.  
TODO: Try this.

### Generate a pom.xml file for each alias and run the `display-dependency-updates` goal of the `versions-maven-plugin` maven plugin

To experiment with this approach, we wrote the two bash scripts:

- generate_pom_files
- display_dependency_updates

You can run them like this:

```
./generate_pom_files && ./display_dependency_updates
```

For example:

```console
[robin@laptop ~/src/jbang-catalog]$ ./generate_pom_files && ./display_dependency_updates
Generated 2 pom files:
- pom_files/jargs/pom.xml
- pom_files/sqlline_test/pom.xml
Available updates:
- jargs:
  (No dependency updates available)
- sqlline_test:
  com.h2database:h2 ................................. 2.1.214 -> 2.2.224
  com.oracle.database.jdbc:ojdbc8 ............. 23.2.0.0 -> 23.3.0.23.09
  sqlline:sqlline ..................................... 1.11.0 -> 1.12.0
```

#### dependabot integration

For dependabot integration:

- Add the `-r` option to the `generate_pom_files` invocation. This registers the pom files in `.github/dependabot.yml`.
- Check-in `.github/dependabot.yml` and the generated pom files into the repo and push up to github.
- Enable dependabot in the repo's web interface at github.com: **Settings > Code Security and analysis**

##### Miscellanous implementation notes regarding dependabot integration

dependabot supports only one pom file per directory, and it must be named `pom.xml`. See
[#2824](https://github.com/dependabot/dependabot-core/issues/2824)
and
[#3951](https://github.com/dependabot/dependabot-core/issues/3951).

For each generated pom file, we add a corresponding stanza to `.github/dependabot.yml`. The template for this stanza is [`dependabot_stanza_template.yml`](dependabot_stanza_template.yml).
