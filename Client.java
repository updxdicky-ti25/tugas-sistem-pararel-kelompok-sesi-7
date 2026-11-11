package rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import rmi.common.Mahasiswa;
import rmi.common.MahasiswaService;

public class Client {
    /** Pemakaian: java rmi.client.Client [HOST_SERVER] */
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try {
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            MahasiswaService svc = (MahasiswaService) registry.lookup("MahasiswaService");
            System.out.println("Koneksi ke RMI Server " + host + " berhasil.\n");
            List<Mahasiswa> daftar = svc.getAll();
            System.out.printf("%-4s %-13s %-30s %s%n", "No", "NIM", "Nama", "Jurusan");
            int no = 1;
            for (Mahasiswa m : daftar)
                System.out.printf("%-4d %-13s %-30s %s%n", no++, m.getNim(), m.getNama(), m.getJurusan());
            System.out.println("\nJumlah mahasiswa: " + svc.getJumlah());
        } catch (Exception e) {
            System.err.println("Koneksi ke RMI Server GAGAL: " + e.getMessage());
        }
    }
}
