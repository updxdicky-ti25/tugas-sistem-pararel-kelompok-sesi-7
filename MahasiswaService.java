package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MahasiswaService extends Remote {
    List<Mahasiswa> getAll() throws RemoteException;
    Mahasiswa cariByNim(String nim) throws RemoteException;
    List<Mahasiswa> cariByNama(String kataKunci) throws RemoteException;
    boolean tambah(Mahasiswa m) throws RemoteException;
    boolean ubah(Mahasiswa m) throws RemoteException;
    boolean hapus(String nim) throws RemoteException;
    int getJumlah() throws RemoteException;
}
