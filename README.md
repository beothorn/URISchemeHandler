URISchemelHandler
==================

A Java library to open and register applications with a URIScheme. See http://en.wikipedia.org/wiki/URI_scheme

Usage
==================

Opening a URIScheme string with default handler:    

String magnetLink = "magnet:?xt=urn:foobarbaz";  
URI magnetLinkUri = new URI(magnetLink);
URISchemeHandler uriSchemeHandler = new URISchemeHandler();  
uriSchemeHandler.open(magnetLinkUri);  

Registering a URIScheme    

URISchemeHandler urlHandler = new URISchemeHandler();  
String schemeName = "mySchemeHandler";  
urlHandler.register(schemeName,"c:\\mySchemeHandler.exe");  

Limitations
==================  
Macs don't have a place were the uri scheme handlers are registered. If this library is called from a mac os it throws a exception.  
On linux only gnome is supported for now.  