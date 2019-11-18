/**
 * Looks like java imports isn't it? ;)
 */
var Server = Java.type('org.eclipse.jetty.server.Server');
var ServletContextHandler = Java.type('org.eclipse.jetty.servlet.ServletContextHandler');
var ServletHolder = Java.type('org.eclipse.jetty.servlet.ServletHolder');
var HandlerList = Java.type('org.eclipse.jetty.server.handler.HandlerList');
var ShutdownHandler = Java.type('org.eclipse.jetty.server.handler.ShutdownHandler');

var JsFriendlyServlet = Java.type('ky.korins.graaljs.JsFriendlyServlet');


/**
 * Let's create a simple jetty server with two handlers: shutdown and our servlets
 */

var server = new Server(12345);
var handlers = new HandlerList();

handlers.addHandler(new ShutdownHandler("SomeKey", false, true));

var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
handlers.addHandler(context);

server.setHandler(handlers);

context.setContextPath("/");

sharedObj.put('Content-Type', 'text/plain');
sharedObj.put('counter', 0);

// Here a tricky part — use our JsFriendlyServlet to overstep GrralJS threads limitation
var demoServletHolder = new ServletHolder("holder-name", new JsFriendlyServlet("DemoServlet.js"));
context.addServlet(demoServletHolder, "/demo/*");

server.start();
server.join();

console.log('It was processed messages: ' + sharedObj.get('counter'));