# This project has been discontinued

&nbsp;

&nbsp;

&nbsp;


The project started as a Java 7 porting of https://github.com/Anorov/cloudflare-scrape, a python module to bypass Cloudflare's anti-bot page (also known as "I'm Under Attack Mode", or IUAM). 

The project allows you to programmatically get the content of a website, whether it is protected by IUAM mode or not. 
As of 5th of August 2016 it is working, but since CloudFlare change its protection techniques periodically it might not work in the future. Please open an issue in that case and I will try to fix it, depending on my commitments on other projects.

Abstraction was priority, I am allowing users to choose whatever http client they desire, by writing a custom class that implements `HttpClientAdapter` or using the two provided implementations (one using Apache Http Client and the other using OkHttpClient - for Android).

## General Instructions
Each `AntiAntiBotCloudFlare` has its own http client instance, that must be closed (`AntiAntiBotCloudFlare` implements `AutoCloseable`, you can close it or use within a try-with-resources block, and it will take care of closing the inner http client instance.

##Usage:
###Library with ApacheHttpClient
(Requires Java>=7) Using _Maven_, add to your `pom.xml` `<repositories>` block the jitpack.io repository:
```
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
```
and add this to your  `pom.xml` `<dependencies>` block:
```
<dependency>
  <artifactId>anti-antibot-cloudflare-apachehttpclient</artifactId>
  <groupId>com.github.alessiop86.anti-antibot-cloudflare</groupId>
  <version>1.2</version>
</dependency>
``` 
    
Then feel free to use the library like this (using a try-with-resources block could be a better alternative):
```
AntiAntiBotCloudFlare scraper = new ApacheHttpAntiAntibotCloudFlareFactory().createInstance();
String html = scraper.getUrl(possiblyProtectedUrl);
scraper.close();
```

If you need to get an image or another non-textual content, use the `getByteArrayFromUrl` method: 
```
byte[] bytes = antiAntiBotCloudFlare.getByteArrayFromUrl(imageUrl);
//example: writing the image whose content is contained in bytes to localPath:
InputStream input = new ByteArrayInputStream(bytes);
OutputStream output = new FileOutputStream(localPath);
IOUtils.copy(input, output);
```

###Library with OkHttpClient (Android)
Add jitpack.io repository to your root build.gradle file:
```
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
and add this dependency:
```
dependencies {
	compile 'compile 'com.github.alessiop86.anti-antibot-cloudflare:anti-antibot-cloudflare-okhttpclient:1.2''
}
```

Then feel free to use the library like this (using a try-with-resources block could be a better alternative):
```
AntiAntiBotCloudFlare antibot = new OkHttpAntiAntibotCloudFlareFactory().createInstance();
String html = antibot.getUrl(possiblyProtectedUrl);
antibot.close(); //not really necessary in this case, since it does nothing
```

Again, If you need to get an image or another non-textual content, use the `getByteArrayFromUrl` method:  
```
byte[] bytes = antiAntiBotCloudFlare.getByteArrayFromUrl(imageUrl);
//the image content is now stored inside the byte array.
```
