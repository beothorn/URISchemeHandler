URISchemeHandler
==================

A Java library to open and register applications with an URIScheme. See http://en.wikipedia.org/wiki/URI_scheme

But why?
Browsers use the URI Schemes  handlers registered with the OS to decide which application to use to handle a URI scheme. 
For example the mailto scheme name is usually associated with your default email client, so if there is something like this on 
a website  
<pre><code>
mailto:foo@bar.com
</code></pre> 
It will open your email client when you click on it.


To use just add to your pom
<pre><code>
\<dependency\>  
  \<groupId\>com.github.beothorn\</groupId\>  
  \<artifactId\>URISchemeHandler\</artifactId\>  
  \<version>1.5\</version\>  
\</dependency\>  
</code></pre>

Usage
==================

Opening an URIScheme string with default handler:    

<pre><code>
	String magnetLink = "magnet:?xt=urn:foobarbaz";  
	URI magnetLinkUri = new URI(magnetLink);  
	URISchemeHandler uriSchemeHandler = new URISchemeHandler();  
	uriSchemeHandler.open(magnetLinkUri);
</code></pre>

This will open the torrent client registered with the os to handle the magnet scheme name. 
For example, if you install utorrent, utorrent will open and ask you if you want to add the magnet link to your downloads.  

Registering a URIScheme    

<pre><code>
	URISchemeHandler urlHandler = new URISchemeHandler();  
	String schemeName = "mySchemeHandler";  
	urlHandler.register(schemeName,"c:\\mySchemeHandler.exe");  //c:\\mySchemeHandler.exe or any command to receive the URI as parameter
</code></pre>

Before adding a new scheme, make sure it doesn't conflict with an existing one. There's a list of them on the wikipedia article mentioned above.


Limitations
==================  
OS X is not implemented yet. If this library is called from a mac os it throws a exception.
