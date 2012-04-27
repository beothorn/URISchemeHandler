URLProtocolHandler
==================

Opens the URL Protocol Handler application registered on your OS.

Today each OS implements a different way to register a protocol handler for a URL protocol.
Java does not have a way to call the default protocol handler for a URL on its vm.
This lib detects the current OS and tries to open the default handler.

Usage
==================

String magnetLink = "magnet:?xt=urn:btih:cd9f90d9d6314cdb997cdd9f0fc29564683236bd&dn=Ubuntu+12.04+LTS+i386+Precise+Pangolin&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80&tr=udp%3A%2F%2Ftracker.ccc.de%3A80";
URLProtocolHandler urlHandler = new URLProtocolHandler();
urlHandler.open(magnetLink);