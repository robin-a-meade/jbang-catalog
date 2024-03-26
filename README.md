# jbang-catalog

## scripts

## saxonhe

Uses latest in Saxon-HE in 12.x line.

```
jbang alias add scripts/saxonhe.java
jbang app install saxonhe
```

## saxonhe11

Uses latest in Saxon-HE in 11.x line. (For comparing results with Saxon-HE 12.x)

```
jbang alias add scripts/saxonhe11.java
jbang app install saxonhe11
```

## deps-to-classpath

Given as arguments a list of dependencies in short Gradle form, print
corresponding class path string using operating system specific path separator.
The dependencies will, if necessary, be downloaded and installed into the local
maven repository.

```
jbang alias add --name=deps-to-classpath scripts/DepsToClasspath.java
jbang app install deps-to-classpath
```


## How to check for updates to dependencies?

Discussion:

- Ways to check for updates to alias dependencies
  https://github.com/orgs/jbangdev/discussions/1751

### Use renovate template

Mentioned in above discussion:

> Run `jbang init -t jbangcatalog@jbanghub mycatalog` and you'll get a project with a `renovate.json` that will find updates in //Deps and catalog script refs.

Try it...  
I get an error:

```console
[user@laptop]$ jbang init -t jbangcatalog@jbanghub mycatalog
[jbang] [ERROR] No template found with name 'jbangcatalog'
[jbang] Run with --verbose for more details
```

Explore:

- Found a `renovate.json` here:
  https://github.com/jbanghub/jbang-catalog/blob/main/template/renovate.json
- It extends preset `"github>jbanghub/.github"`
- That preset is this:  
  https://github.com/jbanghub/.github/blob/main/default.json
- (See: https://docs.renovatebot.com/config-presets/)
- Aha! There is a `jbanghub` template defined in:
  https://github.com/jbanghub/jbang-catalog/blob/main/jbang-catalog.json
- So, probabably, I need to `s/jbangcatalog@/jbanghub@/`:  
  `jbang init -t jbanghub@jbanghub mycatalog`

Try it...  
Got further:

```console
[user@laptop]$ jbang init -t jbanghub@jbanghub mycatalog
[jbang] File initialized. [...]
[user@laptop]$ tree -a mycatalog
mycatalog
├── .github
│   ├── renovate.json
│   └── workflows
│       └── build.yml
├── jbang-catalog.json
└── readme.adoc
```

I believe I just need the `.github/renovate.json` file. I copy it into my existing `jbang-catalog` repo:

```bash
mkdir -p ~/src/jbang-catalog/.github/
cp mycatalog/.github/renovate.json ~/src/jbang-catalog/.github/
```

Then, I believe I need to install the Renovate app into my repo.

1. https://github.com/apps/renovate
2. Click the **Install** button
3. Authorize the App to access your repo
4. Accept terms of the service

I then see the Renovate app listed at among my repo's "integrations" at **Settings > GitHub Apps**.

Click the **Configure** button. Can activate for all repos or just specified repos. I activated it for just my `jbang-catalog` repo.

It works! Pull requests started appearing. And a _Dependency Dashboard_ issue was created that allows for interaction with the Renovate bot.

### Additional notes:

I think

```
"fileMatch": "\\.java",
```

could be replaced with:

```
"fileMatch": [".+\\.java$", ".+\\.jsh$"],
```

or

```
"fileMatch": ".+\\.(java|jsh)$",
```

Documentation on regexManagers:  
https://docs.renovatebot.com/presets-regexManagers/
