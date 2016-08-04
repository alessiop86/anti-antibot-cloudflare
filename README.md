### Status: _WORKING_

The project started as a Java 7 porting of https://github.com/Anorov/cloudflare-scrape, a python module to bypass Cloudflare's anti-bot page (also known as "I'm Under Attack Mode", or IUAM). 

Abstraction was priority, I am allowing users to choose whatever http client they desire, by writing a custom class that implements `HttpClientAdapter` or using the two provided implementations (one using Apache Http Client and the other using OkHttpClient - for Android).

### Temporary Instructions
Soon I will publish it to Jitpack so it will be possible to just add the dependency to your gradle/maven project depedencies. For the moment that is not done and you need to create the jars with `mvn package` and import it in your project. 

#Usage:
```
AntiAntiBotCloudFlare antibot = new ApacheHttpAntiAntibotCloudFlareFactory().createInstance();
String html = antibot.getUrl(protectedUrl);
antibot.close();
```

```
AntiAntiBotCloudFlare antibot = new OkHttpAntiAntibotCloudFlareFactory().createInstance();
String html = antibot.getUrl(protectedUrl);
antibot.close(); //not really necessary in this case
```

Each AntiAntiBotCloudFlare has its own http client instance. Soon will follow more documentation.


