<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,rmi.common.Mahasiswa,rmi.web.RmiHelper" %>
<%!
  static String h(String s) { return RmiHelper.esc(s); }
%>
<%
  List<Mahasiswa> daftar = (List<Mahasiswa>) request.getAttribute("daftar");
  Integer jumlah = (Integer) request.getAttribute("jumlah");
  String error = (String) request.getAttribute("error");
  Mahasiswa edit = (Mahasiswa) request.getAttribute("editMhs");
  String q = request.getParameter("q") == null ? "" : request.getParameter("q");
  boolean admin = Boolean.TRUE.equals(session.getAttribute("admin"));
  String flash = (String) session.getAttribute("flash");
  session.removeAttribute("flash");
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="id"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Monitoring Data Mahasiswa</title>
<style>
*{box-sizing:border-box}
body{margin:0;font-family:'Segoe UI',Arial,sans-serif;background:#f4f6f5;color:#333}
header{background:#1b2d54;color:#fff;padding:24px 38px;display:flex;justify-content:space-between;align-items:center}
header h1{margin:0;font-size:26px}header small{font-size:14px;opacity:.9}
.btn-admin{background:#2a3d66;color:#fff;text-decoration:none;padding:10px 18px;border-radius:4px;font-size:15px}
.wrap{max-width:1450px;margin:36px auto;background:#fff;border-radius:10px;padding:38px;box-shadow:0 2px 12px rgba(0,0,0,.06)}
.judul{background:#e8f3fc;border-radius:6px;padding:20px 25px;display:flex;justify-content:space-between;align-items:center}
.judul h2{margin:0;color:#2a7fba;font-size:21px}
.badge{background:#fff;border:1px solid #cfe3f3;color:#2a7fba;font-weight:700;font-size:19px;padding:8px 15px;border-radius:4px}
.grid{display:grid;grid-template-columns:1fr 445px;gap:38px;margin-top:30px}
.cari{display:flex;gap:12px;margin-bottom:26px}
.cari input{flex:1;padding:0 14px;height:68px;border:1px solid #ccd;border-radius:4px;font-size:16px}
.btn{border:0;border-radius:4px;color:#fff;font-size:17px;padding:0 20px;height:68px;cursor:pointer;text-decoration:none;display:inline-flex;align-items:center;text-align:left}
.teal{background:#1aa88a}.gray{background:#7f8c8d}
table{width:100%;border-collapse:collapse;border:1px solid #dde}
th{background:#1b2d54;color:#fff;text-align:left;padding:17px 15px;border-right:1px solid #3a4a70}
td{padding:12px 15px;border-bottom:1px solid #eee}
td.kosong{text-align:center;color:#667;padding:30px}
.aksi a,.aksi button{font-size:13px;padding:5px 10px;border-radius:3px;border:0;color:#fff;cursor:pointer;text-decoration:none}
.aksi form{display:inline}.ed{background:#e0a100}.hp{background:#c0392b}
.side{border:1px solid #e1e4e8;border-radius:6px;padding:26px;background:#fafafa;align-self:start}
.side h3{margin:0 0 18px;font-size:22px;color:#1b2d54}.side p{color:#555;line-height:1.5;margin:0}
.side a{color:#1aa88a;font-weight:700}
.side input{width:100%;padding:10px;margin:0 0 10px;border:1px solid #ccd;border-radius:4px;font-size:15px}
.side button{background:#1aa88a;color:#fff;border:0;padding:10px 18px;border-radius:4px;font-size:15px;cursor:pointer}
.side .batal{background:#7f8c8d;text-decoration:none;color:#fff;padding:10px 18px;border-radius:4px;font-size:15px;margin-left:6px}
.pesan{padding:12px 16px;border-radius:4px;margin:18px 0 0;font-size:15px}
.ok{background:#e6f6f1;color:#0f6b55}.err{background:#fdecea;color:#b3261e}
.tim{margin-top:38px;background:#fafafa;border:1px solid #eee;border-radius:6px;padding:18px 18px 12px;font-size:15px}
.tim ul{margin:10px 0 0;padding-left:26px;line-height:1.75}
@media(max-width:900px){.grid{grid-template-columns:1fr}.wrap{margin:16px;padding:18px}}
</style></head><body>
<header>
  <div><h1>Monitoring Data Mahasiswa</h1><small>Data diambil dari server Java RMI</small></div>
  <% if (admin) { %><a class="btn-admin" href="<%= ctx %>/login?aksi=logout">Keluar (admin)</a>
  <% } else { %><a class="btn-admin" href="<%= ctx %>/login">Masuk sebagai admin</a><% } %>
</header>
<div class="wrap">
  <div class="judul"><h2>Daftar Mahasiswa</h2><span class="badge" id="badge"><%= jumlah == null ? 0 : jumlah %> mahasiswa terdaftar</span></div>
  <% if (flash != null) { %><div class="pesan ok"><%= h(flash) %></div><% } %>
  <% if (error != null) { %><div class="pesan err"><%= h(error) %></div><% } %>
  <div class="grid">
    <div>
      <form class="cari" method="get" action="<%= ctx %>/mahasiswa">
        <input name="q" value="<%= h(q) %>" placeholder="Cari berdasarkan NIM atau nama">
        <button class="btn teal" type="submit">Cari</button>
        <a class="btn gray" href="<%= ctx %>/mahasiswa">Tampilkan<br>semua</a>
      </form>
      <table>
        <tr><th>NIM</th><th>Nama</th><th>Jurusan</th><th>Aksi</th></tr>
        <% if (daftar == null || daftar.isEmpty()) { %>
          <tr><td class="kosong" colspan="4">Belum ada data mahasiswa atau RMI Server belum aktif.</td></tr>
        <% } else for (Mahasiswa m : daftar) { %>
          <tr>
            <td><%= h(m.getNim()) %></td><td><%= h(m.getNama()) %></td><td><%= h(m.getJurusan()) %></td>
            <td class="aksi">
              <% if (admin) { %>
                <a class="ed" href="<%= ctx %>/mahasiswa?edit=<%= h(m.getNim()) %>">Edit</a>
                <form method="post" action="<%= ctx %>/mahasiswa" onsubmit="return confirm('Hapus <%= h(m.getNama()) %>?')">
                  <input type="hidden" name="aksi" value="hapus"><input type="hidden" name="nim" value="<%= h(m.getNim()) %>">
                  <button class="hp" type="submit">Hapus</button>
                </form>
              <% } else { %>-<% } %>
            </td>
          </tr>
        <% } %>
      </table>
    </div>
    <div class="side">
      <% if (!admin) { %>
        <h3>Tambah mahasiswa</h3>
        <p>Silakan <a href="<%= ctx %>/login">masuk sebagai admin</a> terlebih dahulu untuk menambah, mengubah, atau menghapus data mahasiswa.</p>
      <% } else { %>
        <h3><%= edit != null ? "Ubah mahasiswa" : "Tambah mahasiswa" %></h3>
        <form method="post" action="<%= ctx %>/mahasiswa">
          <input type="hidden" name="aksi" value="<%= edit != null ? "ubah" : "tambah" %>">
          <input name="nim" placeholder="NIM" required value="<%= edit != null ? h(edit.getNim()) : "" %>" <%= edit != null ? "readonly" : "" %>>
          <input name="nama" placeholder="Nama lengkap" required value="<%= edit != null ? h(edit.getNama()) : "" %>">
          <input name="jurusan" list="jurusan" placeholder="Jurusan" required value="<%= edit != null ? h(edit.getJurusan()) : "" %>">
          <datalist id="jurusan"><option>Teknik Informatika</option><option>Sistem Informasi</option><option>Teknik Elektro</option><option>Manajemen</option><option>Akuntansi</option></datalist>
          <button type="submit"><%= edit != null ? "Simpan perubahan" : "Tambah" %></button>
          <% if (edit != null) { %><a class="batal" href="<%= ctx %>/mahasiswa">Batal</a><% } %>
        </form>
      <% } %>
    </div>
  </div>
  <div class="tim">
    <b>Disusun Oleh Anggota Kelompok (TI25G):</b>
    <ul>
      <li>Marisa Oktaviani (20250040069)</li>
      <li>Dicky Abdullah Upara (20250040172)</li>
      <li>Musyaraf Wattimena (20250040140)</li>
      <li>Tri Kholipianto Rahmatsyah (20250040019)</li>
    </ul>
  </div>
</div>
<script>
setInterval(function () {   // jumlah mahasiswa diperbarui otomatis tiap 5 detik lewat panggilan RMI
  fetch('<%= ctx %>/mahasiswa?aksi=jumlah').then(function (r) { return r.ok ? r.text() : null; })
    .then(function (t) { if (t !== null && t !== '-1') document.getElementById('badge').textContent = t + ' mahasiswa terdaftar'; })
    .catch(function () {});
}, 5000);
</script>
</body></html>
