URISchemelHandler
==================

A Java library to open and register applications with an URIScheme. See http://en.wikipedia.org/wiki/URI_scheme

Just add to your pom

\<dependency\>  
  \<groupId\>com.github.beothorn\</groupId\>  
  \<artifactId\>URISchemelHandler\</artifactId\>  
  \<version>1.4\</version\>  
\</dependency\>  


Usage
==================

Opening an URIScheme string with default handler:    

String magnetLink = "magnet:?xt=urn:foobarbaz";  
URI magnetLinkUri = new URI(magnetLink);  
URISchemeHandler uriSchemeHandler = new URISchemeHandler();  
uriSchemeHandler.open(magnetLinkUri);  

This will open the torrent client registered with the os to handle the magnet scheme name. 
For example, if you install utorrent, utorrent will open and ask you if you want to add the magnet link to your downloads.  

Registering a URIScheme    

URISchemeHandler urlHandler = new URISchemeHandler();  
String schemeName = "mySchemeHandler";  
urlHandler.register(schemeName,"c:\\mySchemeHandler.exe");  //c:\\mySchemeHandler.exe or any command to receive the URI as parameter

Before adding a new scheme, make sure it doesn't conflict with an existing one. There's a list of them on the wikipedia article mentioned above.



Limitations
==================  
Macs don't have a place were the uri scheme handlers are registered. If this library is called from a mac os it throws a exception.  
On linux only gnome is supported for now.  