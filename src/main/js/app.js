/**
 * Looks like java imports isn't it? ;)
 */
var Server = Java.type('org.eclipse.jetty.server.Server');
var ServerConnector = Java.type('org.eclipse.jetty.server.ServerConnector');
var ServletContextHandler = Java.type('org.eclipse.jetty.servlet.ServletContextHandler');
var ServletHolder = Java.type('org.eclipse.jetty.servlet.ServletHolder');
var HandlerList = Java.type('org.eclipse.jetty.server.handler.HandlerList');
var ShutdownHandler = Java.type('org.eclipse.jetty.server.handler.ShutdownHandler');
var JsFriendlyQueuedThreadPool = Java.type('ky.korins.graaljs.JsFriendlyQueuedThreadPool');
var JsFriendlyServlet = Java.type('ky.korins.graaljs.JsFriendlyServlet');


/**
 * Let's create a simple jetty server with two handlers: shutdown and our servlets
 */
var jsFriendlyQueuedThreadPool = new JsFriendlyQueuedThreadPool(200, 10, 30); // maxThreads, minThreads and idleSeconds
var server = new Server(jsFriendlyQueuedThreadPool);

var serverConnector = new ServerConnector(server);
serverConnector.setPort(12345);
server.addConnector(serverConnector);

var handlers = new HandlerList();

handlers.addHandler(new ShutdownHandler("SomeKey", false, true));

var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
handlers.addHandler(context);

server.setHandler(handlers);

context.setContextPath("/");

/**
 * Example of working with shared object. It should be anywhere to be honest.
 * Keep in mind that as object you can use only java types.
 */
sharedObject.map.put('Content-Type', 'text/plain');

/**
 * Here a tricky part — use our JsFriendlyServlet to overstep GrralJS threads limitation
 */
var demoServlet = new JsFriendlyServlet("DemoServlet.js");
jsFriendlyQueuedThreadPool.addWellKnowServlet(demoServlet);
var demoServletHolder = new ServletHolder("holder-name", demoServlet);
context.addServlet(demoServletHolder, "/demo/*");

/**
 * Start the server and wait until it will be terminated by shutdown handler
 */
server.start();
server.join();

console.log('It was processed messages: ' + sharedObject.counter);