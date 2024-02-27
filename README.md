# jbang-catalog

## How to check for updates to dependencies?

Discussion:

- Ways to check for updates to alias dependencies
  https://github.com/orgs/jbangdev/discussions/1751

### Use renovate template

Mentioned in above discussion:

> Run `jbang init -t jbangcatalog@jbanghub mycatalog` and you'll get a project with a `renovate.json` that will find updates in //Deps and catalog script refs.

Try it. I get an error:

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
- So, probabably, I need to `s/jbangcatalog@/jbanghub@':  
`jbang init -t jbanghub@jbanghub mycatalog`

Try it.

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

Then, I believe I need to install the Mend Renovate app into my repo.

1. https://github.com/apps/renovate
2. Click the **Install** button
3. Authorize the App to access your repo
4. Accept terms of the service

I then see the Renovate app listed at among my repo's "integrations" at **Settings > GitHub Apps**.

Click the **Configure** button.
