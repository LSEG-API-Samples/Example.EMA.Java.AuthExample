# EMA Java UserAuthn Authentication Example
This EMA Java example demonstrates how to get tokens from a token generate and use retrieved tokens to login to ADS. For more information, please refer to this article. 

The example requires the following parameters.

```
Options:
  -?                            Shows this usage

  -authurl <authurl>           Required: Authentication URL to get a token (e.g. http://<AuthServer>:8443/getToken)
  -username <username>         Required: Username
  -password <password>         Required: Password
  -service <service>           Required: Service name
  -item <ric>                  Optional: A RIC
```
After downloading the exmple, use the following command to build the example.

```
mvn package 
```

Then, run the example with the following command.

```
java -cp target/java-project-1.0-SNAPSHOT-jar-with-dependencies.jar com.refinitiv.ema.examples.authexample.Consumer -authurl "http://localhost:8443/getToken" -username user01 -password password -service ELEKTRON_DD -item PTT.BK
```
