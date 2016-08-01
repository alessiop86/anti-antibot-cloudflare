### Status: _INCOMPLETE/IN PROGRESS_
The library is a work in progress, **The Apache Http Client adapter is already working, so it is possible to use the library right now using that adapter**, the OkHttpClient adapter is having some problems (probably a cookie not forwarded correctly after last 302).


The project started as a Java 7 porting of https://github.com/Anorov/cloudflare-scrape, a python module to bypass Cloudflare's anti-bot page (also known as "I'm Under Attack Mode", or IUAM). I am writing this Java library with abstraction as priority, allowing users to choose whatever http client they desire, by writing a custom class that implements `HttpClientAdapter` or using the two provided implementations (one using Apache Http Client and the other using OkHttpClient).

### Temporary Instructions
As soon as the library is in a stable state I will publish it to Jitpack so it will be possible to just add the dependency to your gradle/maven project depedencies. For the moment that is not done and you need to create the jars with `mvn package` and import it in your project. 

```
AntiAntiBotCloudFlare antibot = new AntiAntiBotCloudFlare(new ApacheHttpClientHttpClientAdapter());
String html = antibot.getUrl(protectedUrl);
```
