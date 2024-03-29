#!/bin/bash

## deps-to-classpath ########################################################

IFS= read -r -d '' description <<'EOF'
resolve dependencies and print the classpath
`deps-to-classpath DEP...`
EOF
jbang alias add \
  --force \
  --name=deps-to-classpath \
  --description="$description" \
  scripts/DepsToClasspath.java


## firefox-profile-dir ######################################################

IFS= read -r -d '' description <<'EOF'
print the absolute path to the user's firefox profile directory
`firefox-profile-dir`
EOF
jbang alias add \
  --force \
  --name=firefox-profile-dir \
  --description="$description" \
  scripts/FirefoxProfileDir.java


## firefox-profile-dir-with-caching #########################################

IFS= read -r -d '' description <<'EOF'
print the absolute path of the user's firefox profile directory and cache the result for subsequent calls
`firefox-profile-dir [--fresh] [-v]`
EOF
jbang alias add \
  --force \
  --name=firefox-profile-dir-with-caching \
  --description="$description" \
  scripts/FirefoxProfileDirWithCaching.java


## jecho ####################################################################

IFS= read -r -d '' description <<'EOF'
print arguments
`jecho [ARG]...`
EOF
jbang alias add \
  --force \
  --description="$description" \
  scripts/jecho.java


## jargs ####################################################################

IFS= read -r -d '' description <<'EOF'
print argument count and then each argument enclosed in angle brackets
`jargs [ARG]...`
EOF
jbang alias add \
  --force \
  --description="$description" \
  scripts/jargs.java

## kebab-case-demo ##########################################################

IFS= read -r -d '' description <<'EOF'
demo of jbang's support for scripts with kebab-case style name
`kebab-case-demo`
EOF
jbang alias add \
  --force \
  --description="$description" \
  scripts/kebab-case-demo


## multi-source-file-demo ######################################################

IFS= read -r -d '' description <<'EOF'
demo of jbang's support for handling multi-source-file java programs
`multi-source-file-demo [NAME]`
EOF
jbang alias add \
  --force \
  --name=multi-source-file-demo \
  --description="$description" \
  scripts/multi-source-file-demo/Main.java


## saxonhe ##################################################################

IFS= read -r -d '' description <<'EOF'
simple wrapper for launching Saxon-HE's command line interfaces for XSLT, XQuery, and the Gizmo utility
`saxonhe (transform|query|gizmo) OPTIONS`
This script uses the *HE* edition of [The Saxon XSLT and XQuery Processor from Saxonica Limited](https://www.saxonica.com).
**Two HTML SAX parsers are bundled.** To use them, add the `-x:org.ccil.cowan.tagsoup.Parser` or `-x:nu.validator.htmlparser.sax.HtmlParser` option when invoking the *transform* or *query* commands.
**This variant uses the latest v12.x release.** See the `saxonhe-v11` variant for v11.x.
EOF
jbang alias add \
  --force \
  --description="$description" \
  scripts/saxonhe.java


## saxonhe11 ################################################################

IFS= read -r -d '' description <<'EOF'
simple wrapper for launching Saxon-HE v11.x command line interfaces for XSLT, XQuery, and the Gizmo utility
`saxonhe-v11 (transform|query|gizmo) OPTIONS`
This script uses the *HE* edition of [The Saxon XSLT and XQuery Processor from Saxonica Limited](https://www.saxonica.com).
**Two HTML SAX parsers are bundled.** To use them, add the `-x:org.ccil.cowan.tagsoup.Parser` or `-x:nu.validator.htmlparser.sax.HtmlParser` option when invoking the *transform* or *query* commands.
**This variant uses the latest v11.x release.** See the `saxonhe` variant for v12.x.
EOF
jbang alias add \
  --force \
  --description="$description" \
  scripts/saxonhe11.java

## saxonheX #################################################################

IFS= read -r -d '' description <<'EOF'
for experimenting with tagsoup modifications
`saxonhe-exp (transform|query|gizmo) OPTIONS`
EOF
jbang alias add \
  --force \
  --description="$description" \
  scripts/saxonheX.java


## sqlline-test ##############################################################

IFS= read -r -d '' description <<'EOF'
for testing *renovate* dependency automation
`sqlline-test [options...] [properties files...]`
**markdown support test:** newline
*italics* _italics_ **bold** \
[link](https://www.jbang.dev)
EOF
jbang alias add \
  --name=sqlline-test \
  --description="$description" \
  --deps com.oracle.database.jdbc:ojdbc8:23.2.0.0 \
  --deps com.h2database:h2:2.1.214 \
  --deps org.postgresql:postgresql:42.3.1 \
  sqlline:sqlline:1.11.0

