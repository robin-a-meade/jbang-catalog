#!/bin/bash

read -r -d '' description <<'EOF'
`deps-to-classpath DEP...`. Resolves dependencies and prints the classpath. Utilizes jbang internals.
EOF
jbang alias add \
  --force \
  --name=deps-to-classpath \
  --description="$description" \
  scripts/DepsToClasspath.java

jbang alias add \
  --force \
  --description="Print its arguments" \
  scripts/jecho.java


