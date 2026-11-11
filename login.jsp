<%@ page contentType="text/html;charset=UTF-8" language="java" import="rmi.web.RmiHelper" %>
<% String error = (String) request.getAttribute("error"); %>
<!DOCTYPE html>
<html lang="id"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login Admin</title>
<style>
body{margin:0;font-family:'Segoe UI',Arial,sans-serif;background:#f4f6f5;display:flex;min-height:100vh;align-items:center;justify-content:center}
.card{background:#fff;padding:32px;border-radius:10px;box-shadow:0 2px 12px rgba(0,0,0,.08);width:340px}
h2{margin:0 0 4px;color:#1b2d54}p{margin:0 0 20px;color:#667;font-size:14px}
input{width:100%;box-sizing:border-box;padding:11px;margin-bottom:12px;border:1px solid #ccd;border-radius:4px;font-size:15px}
button{width:100%;padding:11px;background:#1aa88a;color:#fff;border:0;border-radius:4px;font-size:15px;cursor:pointer}
.err{background:#fdecea;color:#b3261e;padding:10px;border-radius:4px;margin-bottom:12px;font-size:14px}
a{color:#1b2d54;font-size:14px;display:block;text-align:center;margin-top:14px}
</style></head><body>
<form class="card" method="post" action="<%= request.getContextPath() %>/login">
  <h2>Login Administrator</h2><p>Masuk untuk mengelola data mahasiswa.</p>
  <% if (error != null) { %><div class="err"><%= RmiHelper.esc(error) %></div><% } %>
  <input name="username" placeholder="Username" required autofocus>
  <input name="password" type="password" placeholder="Password" required>
  <button type="submit">Masuk</button>
  <a href="<%= request.getContextPath() %>/mahasiswa">&larr; Kembali ke daftar</a>
</form></body></html>
