package ky.korins.graaljs;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsFriendlyServlet extends HttpServlet {

    private final ThreadLocal<Map.Entry<Context, Value>> ctx;

    public JsFriendlyServlet(String file) throws IOException {
        URL url = getClass().getClassLoader().getResource(file);
        if (url == null) {
            throw new IOException("Can't find file" + file);
        }
        Source js = Source.newBuilder("js", url).build();
        ctx = ThreadLocal.withInitial(() -> {
            Context cx = App.createContext();
            Value handler = cx.eval(js);
            return new HashMap.SimpleEntry<>(cx, handler);
        });
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Context cx = ctx.get().getKey();
        try {
            cx.enter();
            ctx.get().getValue().execute(request, response);
        } finally {
            cx.leave();
        }
    }

    public void cleanupThread() {
        ctx.remove();
    }
}
