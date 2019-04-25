# SimpleThreadedJavaHttpServer
A simple HTTP Server for those of us who don't need to leverage the vast frameworks.

## Motivation
While entering the realm of Java HTTP server development I found myself stranded between managing the nuance of HTTP standards directly via sockets and leveraging massive frameworks such as Jetty, Jersey, and Spring.

I opted out by building my own middleground. A simple HTTP abstraction for those of us on Java SE that allows the simple instantiation of a threaded server via the creation of your own Thread extended class.

## Getting Started
This is the only section (besides my motivation) because the whole point is to get yourself started, quickly, with a simple, malleable HTTP server.

### 1. Instantiate Server
You can instantiate this in whatever method you choose, as it will spin up a new thread to run your server on, by listening to the port number provided. As each request comes in, your HttpSocketServer will start a new thread of the type provided for threadableClassName.

Like so:
`HttpServer.HttpConnectionManager.of(int port, String threadableClassName);`

This server will listen on the specified port and run a new thread for each incoming request. Which means you only have to worry about the `run()` method of your threadable class.

### 2. Message Management
Nobody wants to deal with the minutia formatting text into valid Http Messages, so we do that for you with the abstract class `HttpMessage` and its lovely children `HttpRequest` and `HttpResponse`.

These are just one piece of the Yin-Yang relationship. There is still the "Connection Manager". Our `HttpConnectionManager` takes on the responsibility of managing the socket streams, parsing those chars and bytes into your `HttpRequest` and sending your `HttpResponse` back down the pipe, closing out the socket when you're mission is complete.

I haven't gone overboard with my implementation of these classes (I don't think) and you will most likely want to supplement them. That's great! I do think I have provided the skeleton you'll be able to leave in the closet. Namely:

- Parsing the Request (with the help of `HttpConnectionManager`)
- Setting and getting key elements: headers, method, status code, etc.
- Letting you build the work you want to, without the overhead of concatenating strings just the right way.


## Thank you! Feedback is welcome.

