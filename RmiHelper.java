package rmi.web;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import jakarta.servlet.ServletContext;
import rmi.common.MahasiswaService;

public class RmiHelper {
    /** Lookup dilakukan tiap request, jadi server yang baru dihidupkan langsung terdeteksi. */
    public static MahasiswaService get(ServletContext ctx) throws Exception {
        String host = ctx.getInitParameter("rmiHost");
        String port = ctx.getInitParameter("rmiPort");
        Registry r = LocateRegistry.getRegistry(host == null ? "localhost" : host,
                port == null ? 1099 : Integer.parseInt(port));
        return (MahasiswaService) r.lookup("MahasiswaService");
    }

    public static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
