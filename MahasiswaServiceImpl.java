package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import rmi.common.Mahasiswa;
import rmi.common.MahasiswaService;

/** Thread-safe: RMI memproses tiap panggilan di thread terpisah. */
public class MahasiswaServiceImpl extends UnicastRemoteObject implements MahasiswaService {
    private final ConcurrentSkipListMap<String, Mahasiswa> data = new ConcurrentSkipListMap<>();

    public MahasiswaServiceImpl() throws RemoteException {
        super();
        for (String[] r : DataAwal.DATA) data.put(r[0], new Mahasiswa(r[0], r[1], r[2]));
    }

    private static boolean kosong(String s) { return s == null || s.trim().isEmpty(); }

    @Override public List<Mahasiswa> getAll() { return new ArrayList<>(data.values()); }

    @Override public Mahasiswa cariByNim(String nim) {
        return kosong(nim) ? null : data.get(nim.trim());
    }

    @Override public List<Mahasiswa> cariByNama(String kata) {
        List<Mahasiswa> hasil = new ArrayList<>();
        if (kosong(kata)) return hasil;
        String k = kata.trim().toLowerCase();
        for (Mahasiswa m : data.values())
            if (m.getNama().toLowerCase().contains(k)) hasil.add(m);
        return hasil;
    }

    @Override public boolean tambah(Mahasiswa m) {
        if (m == null || kosong(m.getNim()) || kosong(m.getNama()) || kosong(m.getJurusan())) return false;
        boolean ok = data.putIfAbsent(m.getNim().trim(), new Mahasiswa(m.getNim().trim(), m.getNama().trim(), m.getJurusan().trim())) == null;
        if (ok) System.out.println("[TAMBAH] " + m.getNim());
        return ok;
    }

    @Override public boolean ubah(Mahasiswa m) {
        if (m == null || kosong(m.getNim()) || kosong(m.getNama()) || kosong(m.getJurusan())) return false;
        boolean ok = data.replace(m.getNim().trim(), new Mahasiswa(m.getNim().trim(), m.getNama().trim(), m.getJurusan().trim())) != null;
        if (ok) System.out.println("[UBAH] " + m.getNim());
        return ok;
    }

    @Override public boolean hapus(String nim) {
        boolean ok = !kosong(nim) && data.remove(nim.trim()) != null;
        if (ok) System.out.println("[HAPUS] " + nim);
        return ok;
    }

    @Override public int getJumlah() { return data.size(); }
}
