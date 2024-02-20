# jbang-catalog

## 2024-02-19 Create an alias for sqlline

```
jbang alias add \
  --name=sqlline \
  --deps com.oracle.database.jdbc:ojdbc8:23.2.0.0 \
  --deps com.h2database:h2:2.1.214 \
  sqlline:sqlline:1.11.0
```

I purposely didn't use the latest releases of depenencies because I want to
test the ability to be alerted to the availability of new releases of
dependencies.

## How to check for updates to dependencies?
One way to check for updates to dependencies is to run this script:
```
./display_dependency_updates <alias name> <path to catalog file>
```
For example:
```console
[robin@laptop ~/src/jbang-catalog]$ ./display_dependency_updates sqlline ./jbang-catalog.json
[INFO] Scanning for projects...
[INFO]
[INFO] -------------------------< com.example:my-app >-------------------------
[INFO] Building my-app 1
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- versions:2.16.2:display-dependency-updates (default-cli) @ my-app ---
[INFO] The following dependencies in Dependencies have newer versions:
[INFO]   com.h2database:h2 ................................. 2.1.214 -> 2.2.224
[INFO]   com.oracle.database.jdbc:ojdbc8 ............. 23.2.0.0 -> 23.3.0.23.09
[INFO]   sqlline:sqlline ..................................... 1.11.0 -> 1.12.0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.587 s
[INFO] Finished at: 2024-02-19T19:52:23-10:00
[INFO] ------------------------------------------------------------------------
```


