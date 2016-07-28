UNFINISHED! WORK IN PROGRESS! NOT COMPLETE! NOT WORKING! 

**I need some help, I need some website with the CloudFlare anti bot protection (I'm under attack mode) activated, so I can implement the rest of the library (I believe that most of the work it has already been done). Please open an issue or send me a private message :)**



The project started as a Java 7 porting of https://github.com/Anorov/cloudflare-scrape, a python module to bypass Cloudflare's anti-bot page (also known as "I'm Under Attack Mode", or IUAM). I am writing this Java library with abstraction as priority, allowing users to choose whatever http client they desire, by writing a custom class that implements `HttpClientAdapter` (but it is just possible to use the provided default implementation based on OkHttpClient, working on both Android and Java SE/EE).
