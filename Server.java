package rmi.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    private static Registry registry;

    /** Pemakaian: java rmi.server.Server [IP_SERVER]  (IP diisi bila dipakai lewat LAN) */
    public static void main(String[] args) throws Exception {
        if (args.length > 0) System.setProperty("java.rmi.server.hostname", args[0]);
        registry = LocateRegistry.createRegistry(1099);
        registry.rebind("MahasiswaService", new MahasiswaServiceImpl());
        System.out.println("RMI Server aktif di port 1099 (service: MahasiswaService)");
        System.out.println("Tekan Ctrl+C untuk menghentikan.");
    }
}
