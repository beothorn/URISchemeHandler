URLProtocolHandler
==================

Opens the URL Protocol Handler application registered on your OS.

Today each OS implements a different way to register a protocol handler for a URL protocol.
Java does not have a way to call the default protocol handler for a URL on its vm.
This lib detects the current OS and tries to open the default handler.

It currently supports:

Ubuntu
Windows7

Usage
==================

Opening a url with default handler:

String magnetLink = "magnet:?xt=urn:foobarbaz";
URLProtocolHandler urlHandler = new URLProtocolHandler();
urlHandler.open(magnetLink);

Registering a protocol handler

URLProtocolHandler urlHandler = new URLProtocolHandler();
String protocol = "myProtocol";
urlHandler.register(protocol,"protocolHandlerApplication handle%1");
