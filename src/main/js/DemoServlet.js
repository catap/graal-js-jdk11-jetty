/**
 * Implementation of HttpServlet service function
 */
(load("Loaded.js"),
function (req, resp) {
    sharedObj.put('counter', sharedObj.get('counter') + 1);
    resp.setStatus(statusCode);
    resp.setContentType(sharedObj.get('Content-Type'));
    resp.getWriter().write("Some body");
});
