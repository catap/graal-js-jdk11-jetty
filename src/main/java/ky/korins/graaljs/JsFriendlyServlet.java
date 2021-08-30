package ky.korins.graaljs;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsFriendlyServlet extends HttpServlet {

    private final ThreadLocal<Map.Entry<Context, Value>> ctx;

    public JsFriendlyServlet(String file) throws IOException {
        Source js = App.openJsFile(file);
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
