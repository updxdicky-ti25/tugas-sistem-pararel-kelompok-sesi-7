package rmi.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import rmi.common.Mahasiswa;
import rmi.common.MahasiswaService;

@WebServlet("/mahasiswa")
public class MahasiswaServlet extends HttpServlet {

    private static boolean isAdmin(HttpServletRequest req) {
        return Boolean.TRUE.equals(req.getSession().getAttribute("admin"));
    }
    private static String p(HttpServletRequest req, String n) {
        String v = req.getParameter(n);
        return v == null ? "" : v.trim();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("jumlah".equals(req.getParameter("aksi"))) {          // dipakai auto-refresh jumlah mahasiswa
            resp.setContentType("text/plain;charset=UTF-8");
            try {
                resp.getWriter().print(RmiHelper.get(getServletContext()).getJumlah());
            } catch (Exception e) {
                resp.setStatus(503);
                resp.getWriter().print("-1");
            }
            return;
        }
        String q = p(req, "q");
        List<Mahasiswa> daftar = new ArrayList<>();
        int jumlah = 0;
        try {
            MahasiswaService svc = RmiHelper.get(getServletContext());
            if (q.isEmpty()) {
                daftar = svc.getAll();
            } else {                                              // cari berdasarkan NIM atau nama
                Mahasiswa m = svc.cariByNim(q);
                if (m != null) daftar.add(m);
                for (Mahasiswa x : svc.cariByNama(q))
                    if (m == null || !x.getNim().equals(m.getNim())) daftar.add(x);
            }
            jumlah = svc.getJumlah();
            if (isAdmin(req) && !p(req, "edit").isEmpty())
                req.setAttribute("editMhs", svc.cariByNim(p(req, "edit")));
        } catch (Exception e) {
            req.setAttribute("error", "Tidak dapat terhubung ke RMI Server (" + e.getMessage() + ")");
        }
        req.setAttribute("daftar", daftar);
        req.setAttribute("jumlah", jumlah);
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (!isAdmin(req)) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        String aksi = p(req, "aksi"), msg;
        try {
            MahasiswaService svc = RmiHelper.get(getServletContext());
            Mahasiswa m = new Mahasiswa(p(req, "nim"), p(req, "nama"), p(req, "jurusan"));
            if ("tambah".equals(aksi))
                msg = svc.tambah(m) ? "Data berhasil ditambahkan." : "Gagal menambah: NIM sudah ada atau data kosong.";
            else if ("ubah".equals(aksi))
                msg = svc.ubah(m) ? "Data berhasil diubah." : "Gagal mengubah data.";
            else if ("hapus".equals(aksi))
                msg = svc.hapus(m.getNim()) ? "Data berhasil dihapus." : "Gagal menghapus data.";
            else msg = "Aksi tidak dikenal.";
        } catch (Exception e) {
            msg = "Gagal terhubung ke RMI Server: " + e.getMessage();
        }
        req.getSession().setAttribute("flash", msg);
        resp.sendRedirect(req.getContextPath() + "/mahasiswa");
    }
}
