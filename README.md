URISchemelHandler
==================

A Java library to open and register applications with a URIScheme. See http://en.wikipedia.org/wiki/URI_scheme

Usage
==================

Opening a url with default handler:    

String magnetLink = "magnet:?xt=urn:foobarbaz";  
URLProtocolHandler urlHandler = new URLProtocolHandler();  
urlHandler.open(magnetLink);  

Registering a protocol handler    

URLProtocolHandler urlHandler = new URLProtocolHandler();  
String protocol = "myProtocol";  
urlHandler.register(protocol,"c:\\protocolHandlerApplication.exe");  

Limitations
==================  
Macs don't have a place were the url protocol handlers are registered. If this library is called from a mac os it throws a exception.  
On linux only gnome is supported for now.  