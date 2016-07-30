import SimpleHTTPServer
import logging

cookieHeader = None


class MyHTTPRequestHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):
    def do_GET(self):
        self.cookieHeader = self.headers.get('Cookie')
        print self.headers
        SimpleHTTPServer.SimpleHTTPRequestHandler.do_GET(self)


    def end_headers(self):
        self.send_my_headers()

        SimpleHTTPServer.SimpleHTTPRequestHandler.end_headers(self)

    def send_my_headers(self):
	self.send_header("Set-Cookie", "_cfuididi=0b59c51aca680614ad7d395510880dd7")

if __name__ == '__main__':
    SimpleHTTPServer.test(HandlerClass=MyHTTPRequestHandler)
