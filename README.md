### Status: _INCOMPLETE/IN PROGRESS_
The library is a work in progress, **The Apache Http Client adapter is already working, so it is possible to use the library right now using that adapter**, the OkHttpClient adapter is having some problems, I am not sure why because the request executed looks exactly the same including headers and cookies. I am running out of idea on how to debug it, I introduced a python webserver to print headers and cookies and I used it to observe the interaction between the two adapters and a fake protected resource. Any help in that front is appreciated.


The project started as a Java 7 porting of https://github.com/Anorov/cloudflare-scrape, a python module to bypass Cloudflare's anti-bot page (also known as "I'm Under Attack Mode", or IUAM). I am writing this Java library with abstraction as priority, allowing users to choose whatever http client they desire, by writing a custom class that implements `HttpClientAdapter` or using the two provided implementations (one using Apache Http Client and the other using OkHttpClient). When everything will be working I intend to split the project in 3, main project and a module for each of the two default adapters.


### Temporary Instructions
As soon as the library is in a stable state I will publish it to Jitpack so it will be possible to just add the dependency to your gradle/maven project depedencies. For the moment that is not done and you need to create the jar with `mvn package` and import it in your project. Then just

```
AntiAntiBotCloudFlare antibot = new AntiAntiBotCloudFlare(new ApacheHttpClientHttpClientAdapter());
String html = antibot.getUrl(protectedUrl);
```
