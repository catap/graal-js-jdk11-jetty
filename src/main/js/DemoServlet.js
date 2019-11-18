/**
 * Implementation of HttpServlet service function
 */
(load("Loaded.js"),
function (req, resp) {
    sharedObject.counter += 1;
    resp.setStatus(statusCode());
    resp.setContentType(sharedObject.map.get('Content-Type'));
    resp.getWriter().write("Some body");
});
