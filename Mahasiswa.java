package rmi.common;

import java.io.Serializable;

public class Mahasiswa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nim, nama, jurusan;

    public Mahasiswa(String nim, String nama, String jurusan) {
        this.nim = nim; this.nama = nama; this.jurusan = jurusan;
    }
    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public String getJurusan() { return jurusan; }
    public void setNama(String nama) { this.nama = nama; }
    public void setJurusan(String jurusan) { this.jurusan = jurusan; }
}
