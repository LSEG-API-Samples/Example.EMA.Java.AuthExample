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
java -cp target/java-project-1.0-SNAPSHOT-jar-with-dependencies.jar com.refinitiv.ema.examples.authexample.Consumer -authurl "http://localhost:8443/getToken" -username authuser1 -password password -service ELEKTRON_DD -item PTT.BK
```
The output is:
```
Following options are selected:
appId = 256
authurl = http://localhost:8443/getToken
username = authuser1
password = *****
service = ELEKTRON_DD
item = PTT.BK
Nov 23, 2021 3:34:46 PM org.apache.http.client.protocol.ResponseProcessCookies processCookies
WARNING: Invalid cookie header: "set-cookie: connect.sid=s%3A7EkQ15fpm7s8R5x2hvvlH05G26YMwCut.eo%2FhJsspQ0zMslML6qpcIPMSPQL2WK6ROP%2FD4n7gNBg; Path=/; Expires=Tue, 23 Nov 2021 13:50:53 GMT; HttpOnly". Invalid 'expires' attribute: Tue, 23 Nov 2021 13:50:53 GMT
{"success":true,"message":"Enjoy your token!","token":"QHtSSs0xKmIqAurrcyaLsCBzwF"}
Token: QHtSSs0xKmIqAurrcyaLsCBzwF
Nov 23, 2021 3:34:46 PM com.refinitiv.ema.access.ChannelCallbackClient reactorChannelEventCallback
INFO: loggerMsg
    ClientName: ChannelCallbackClient
    Severity: Info
    Text:    Received ChannelUp event on channel Channel
        Instance Name EmaConsumer_1
        Component Version ads3.5.3.L1.linux.rrg 64-bit
loggerMsgEnd


Received Login Refresh Message

RefreshMsg
    streamId="1"
    domain="Login Domain"
    solicited
    RefreshComplete
    state="Open / Ok / None / 'Login accepted by host replablinux5.'"
    itemGroup="00 00"
    name="wasin"
    nameType="1"
    Attrib dataType="ElementList"
        ElementList
            ElementEntry name="AllowSuspectData" dataType="UInt" value="1"
            ElementEntry name="ApplicationId" dataType="Ascii" value="256"
            ElementEntry name="ApplicationName" dataType="Ascii" value="ADS"
            ElementEntry name="AuthenticationErrorCode" dataType="UInt" value="0"
            ElementEntry name="AuthenticationErrorText" dataType="Ascii" value="Success"
            ElementEntry name="AuthenticationTTReissue" dataType="UInt" value="1637661255"
            ElementEntry name="Position" dataType="Ascii" value="192.168.0.184/WIN-2NOVD7CLMV1"
            ElementEntry name="ProvidePermissionExpressions" dataType="UInt" value="1"
            ElementEntry name="ProvidePermissionProfile" dataType="UInt" value="0"
            ElementEntry name="SingleOpen" dataType="UInt" value="1"
            ElementEntry name="SupportEnhancedSymbolList" dataType="UInt" value="1"
            ElementEntry name="SupportOMMPost" dataType="UInt" value="1"
            ElementEntry name="SupportPauseResume" dataType="UInt" value="0"
            ElementEntry name="SupportStandby" dataType="UInt" value="0"
            ElementEntry name="SupportBatchRequests" dataType="UInt" value="7"
            ElementEntry name="SupportViewRequests" dataType="UInt" value="1"
            ElementEntry name="SupportOptimizedPauseResume" dataType="UInt" value="0"
        ElementListEnd
    AttribEnd
RefreshMsgEnd


Received Refresh Message

RefreshMsg
    streamId="5"
    domain="MarketPrice Domain"
    solicited
    RefreshComplete
    state="Open / Ok / None / 'All is well'"
    itemGroup="00 02"
    permissionData="03 11 a7 67 61 c0"
    name="PTT.BK"
    nameType="1"
    serviceId="4519"
    serviceName="ELEKTRON_DD"
    Payload dataType="FieldList"
        FieldList FieldListNum="2" DictionaryId="1"
            FieldEntry fid="1" name="PROD_PERM" dataType="UInt" value="6761"
            FieldEntry fid="2" name="RDNDISPLAY" dataType="UInt" value="142"
            FieldEntry fid="3" name="DSPLY_NAME" dataType="Rmtes" value="PTT"
            FieldEntry fid="4" name="RDN_EXCHID" dataType="Enum" value="158"
            FieldEntry fid="5" name="TIMACT" dataType="Time" value="08:34:49:000:000:000"
            FieldEntry fid="6" name="TRDPRC_1" dataType="Real" value="37.25"
            FieldEntry fid="7" name="TRDPRC_2" dataType="Real" value="37.25"
            FieldEntry fid="8" name="TRDPRC_3" dataType="Real" value="37.00"
            FieldEntry fid="9" name="TRDPRC_4" dataType="Real" value="37.25"
            FieldEntry fid="10" name="TRDPRC_5" dataType="Real" value="37.25"
            FieldEntry fid="11" name="NETCHNG_1" dataType="Real" value="0.00"
            FieldEntry fid="12" name="HIGH_1" dataType="Real" value="37.75"
            FieldEntry fid="13" name="LOW_1" dataType="Real" value="37.00"
            FieldEntry fid="14" name="PRCTCK_1" dataType="Enum" value="1"
            FieldEntry fid="15" name="CURRENCY" dataType="Enum" value="764"
            FieldEntry fid="16" name="TRADE_DATE" dataType="Date" value="23 NOV 2021"
            FieldEntry fid="18" name="TRDTIM_1" dataType="Time" value="08:34:36:000:000:000"
            FieldEntry fid="19" name="OPEN_PRC" dataType="Real" value="37.50"
            FieldEntry fid="21" name="HST_CLOSE" dataType="Real" value="37.25"
            FieldEntry fid="22" name="BID" dataType="Real" value="37.00"
            FieldEntry fid="25" name="ASK" dataType="Real" value="37.25"
            FieldEntry fid="28" name="NEWS" dataType="Rmtes" value="YYYY"
            FieldEntry fid="29" name="NEWS_TIME" dataType="Time" value="04:45:00:000:000:000"
```
