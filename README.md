# Example.EMA.Java.AuthExample

```
Options:
  -?                            Shows this usage

  -authurl <authurl>           Required: Authentication URL to get a token (e.g. http://<AuthServer>:8443/getToken)
  -username <username>         Required: Username
  -password <password>         Required: Password
  -service <service>           Required: Service name
  -item <ric>                  Optional: A RIC
```

```
mvn package 
```

```
java -cp target/java-project-1.0-SNAPSHOT-jar-with-dependencies.jar com.refinitiv.ema.examples.authexample.Consumer -authurl "http://localhost:8443/getToken" -username wasin -password reuters -service ELEKTRON_DD -item PTT.BK
```
