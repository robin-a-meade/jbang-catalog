#!/bin/bash

# Initialize REPO_DIR to the directory containing this script
# (The use of `readlink -f` is to make this operation symlink-safe. See
# https://stackoverflow.com/a/51651602)
REPO_DIR=$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")
readonly REPO_dir

## deps-to-classpath ########################################################

IFS= read -r -d '' description <<'EOF'
resolve dependencies and print the classpath
`deps-to-classpath DEP...`
Alternative: `deps_to_classpath DEP...` [deps_to_classpath](https://gist.github.com/robin-a-meade/a1237d7ff7cbe6dc159fa32a81c12948) is a shell script wrapper around `jbang info classpath`
Alternative: `jbang jecho@robin-a-meade "%{deps:$(IFS=,; echo "${DEPS[*]}")}"`, where `DEPS` is an array of dependencies.
EOF
jbang alias add \
  --force \
  --name=deps-to-classpath \
  --description="$description" \
  "$REPO_DIR"/scripts/DepsToClasspath.java

## firefox-profile-dir ######################################################

IFS= read -r -d '' description <<'EOF'
print the absolute path to the user's firefox profile directory
`firefox-profile-dir`
EOF
jbang alias add \
  --force \
  --name=firefox-profile-dir \
  --description="$description" \
  "$REPO_DIR"/scripts/FirefoxProfileDir.java

## firefox-profile-dir-with-caching #########################################

IFS= read -r -d '' description <<'EOF'
print the absolute path of the user's firefox profile directory and cache the result for subsequent calls
`firefox-profile-dir-with-caching [--fresh] [-v]`
EOF
jbang alias add \
  --force \
  --name=firefox-profile-dir-with-caching \
  --description="$description" \
  "$REPO_DIR"/scripts/FirefoxProfileDirWithCaching.java

## jecho ####################################################################

IFS= read -r -d '' description <<'EOF'
print arguments
`jecho [ARG]...`
EOF
jbang alias add \
  --force \
  --description="$description" \
  "$REPO_DIR"/scripts/jecho.java

## jargs ####################################################################

IFS= read -r -d '' description <<'EOF'
print the argument count and print each argument enclosed in angle brackets
`jargs [ARG]...`
EOF
jbang alias add \
  --force \
  --description="$description" \
  "$REPO_DIR"/scripts/jargs.java

## kebab-case-demo ##########################################################

IFS= read -r -d '' description <<'EOF'
demo of jbang's support for scripts with kebab-case style name
`kebab-case-demo`
EOF
jbang alias add \
  --force \
  --description="$description" \
  "$REPO_DIR"/scripts/kebab-case-demo

## multi-source-file-demo ######################################################

IFS= read -r -d '' description <<'EOF'
demo of jbang's support for handling multi-source-file java programs
`multi-source-file-demo [NAME]`
EOF
jbang alias add \
  --force \
  --name=multi-source-file-demo \
  --description="$description" \
  "$REPO_DIR"/scripts/multi-source-file-demo/Main.java

## saxonhe ##################################################################

IFS= read -r -d '' description <<'EOF'
wrapper for launching Saxon-HE's command line interfaces for XSLT, XQuery, and the Gizmo utility
`saxonhe (transform|query|gizmo) OPTIONS`
This script uses the *HE* edition of [The Saxon XSLT and XQuery Processor](https://www.saxonica.com).
This script bundles two HTML SAX parsers. To use them, specify the `-x:org.ccil.cowan.tagsoup.Parser` or `-x:nu.validator.htmlparser.sax.HtmlParser` option when invoking the *transform* or *query* commands.
This variant uses the latest in the v12.x line of releases. See the `saxonhe11` variant for v11.x.
EOF
jbang alias add \
  --force \
  --description="$description" \
  "$REPO_DIR"/scripts/saxonhe.java

## saxonhe11 ################################################################

IFS= read -r -d '' description <<'EOF'
simple wrapper for launching Saxon-HE v11.x command line interfaces for XSLT, XQuery, and the Gizmo utility
`saxonhe11 (transform|query|gizmo) OPTIONS`
This script uses the *HE* edition of [The Saxon XSLT and XQuery Processor](https://www.saxonica.com).
This script bundles two HTML SAX parsers. To use them, specify the `-x:org.ccil.cowan.tagsoup.Parser` or `-x:nu.validator.htmlparser.sax.HtmlParser` option when invoking the *transform* or *query* commands.
This variant uses the latest in the v11.x line of releases. See the `saxonhe` variant for v12.x.
EOF
jbang alias add \
  --force \
  --description="$description" \
  "$REPO_DIR"/scripts/saxonhe11.java

## saxonheX #################################################################

IFS= read -r -d '' description <<'EOF'
experiment with tagsoup modifications
`saxonhe-exp (transform|query|gizmo) OPTIONS`
EOF
jbang alias add \
  --force \
  --description="$description" \
  "$REPO_DIR"/scripts/saxonheX.java

## sqlline-test ##############################################################

IFS= read -r -d '' description <<'EOF'
test of *renovate* dependency automation
`sqlline-test [options...] [properties files...]`
EOF
jbang alias add \
  --force \
  --name=sqlline-test \
  --description="$description" \
  --deps com.oracle.database.jdbc:ojdbc8:23.2.0.0 \
  --deps com.h2database:h2:2.1.214 \
  --deps org.postgresql:postgresql:42.3.1 \
  sqlline:sqlline:1.11.0

## tagsoup ##################################################################

IFS= read -r -d '' description <<'EOF'
convert HTML to well-formed XML (John Cowan's tagsoup 1.2.1)
`tagsoup [OPTIONS] [FILES]`
EOF
jbang alias add \
  --force \
  --description="$description" \
  org.ccil.cowan.tagsoup:tagsoup:1.2.1
