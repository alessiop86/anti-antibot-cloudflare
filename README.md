UNFINISHED! WORK IN PROGRESS! NOT COMPLETE! NOT WORKING! 

**I need some help, I need some website with the CloudFlare anti bot protection (I'm under attack mode) activated, so I can implement the rest of the library (I believe that most of the work it has already been done). Please open an issue or send me a private message :)**



the project is pretty much a Java 7 porting of https://github.com/Anorov/cloudflare-scrape,  a Java library to bypass Cloudflare's anti-bot page (also known as "I'm Under Attack Mode", or IUAM). I am writing it with abstraction as priority, allowing you to choose whatever http client you desire writing a custom class that implements HttpClientAdapter (but you can just use the default implementation based on OkHttpClient, working on both Android and Java SE/EE).
