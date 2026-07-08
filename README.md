# 🧩 RubikSolver Android

RubikSolver adalah aplikasi Android modern yang dirancang untuk membantu Anda menyelesaikan teka-teki Rubik's Cube 3x3 dengan mudah. Aplikasi ini menggabungkan algoritma penyelesaian yang cepat dengan antarmuka pengguna yang intuitif menggunakan Jetpack Compose.

## ✨ Fitur Utama

### 1. 🔍 Manual Cube Entry
Masukkan kondisi Rubik Anda secara manual melalui antarmuka 2D yang interaktif. Anda cukup memilih warna dan menempelkannya pada kotak (facelet) yang sesuai dengan kondisi asli Rubik Anda.

### 2. ⚡ Fast Solver Algorithm
Menggunakan implementasi algoritma penyelesaian yang efisien (Min2Phase), aplikasi dapat menemukan langkah-langkah solusi hanya dalam hitungan detik.

### 3. 📜 Solve History
Jangan khawatir kehilangan solusi Anda! Setiap kali Anda menyelesaikan Rubik, aplikasi secara otomatis menyimpan:
- Kondisi awal Rubik (Cube State).
- Waktu penyelesaian (Timestamp).
- Langkah-langkah solusi lengkap.
- Fitur untuk menghapus seluruh riwayat untuk menjaga kebersihan data.

### 4. 🌓 Dark Mode Support
Bekerja dengan nyaman di kondisi cahaya apa pun. RubikSolver mendukung tema gelap (Dark Mode) yang elegan untuk mengurangi kelelahan mata.

### 5. 🔊 Sound Effects
Memberikan pengalaman pengguna yang lebih responsif dengan efek suara klik yang memuaskan saat berinteraksi dengan tombol dan antarmuka.

## 🚀 Teknologi yang Digunakan

- **Language:** Kotlin & Java (Hybrid for Database Compatibility)
- **UI Framework:** Jetpack Compose (Modern Declarative UI)
- **Architecture:** MVVM (Model-View-ViewModel) with Clean Architecture principles.
- **Database:** Room Persistence Library (Local Storage for History).
- **Navigation:** Compose Navigation for seamless screen transitions.
- **Dependency Management:** Version Catalog (libs.versions.toml).

## 🛠️ Instalasi & Pengembangan

1. Clone repositori ini:
   ```bash
   git clone https://github.com/username/rubik-solver-android.git
   ```
2. Buka project di **Android Studio (Ladybug atau yang terbaru)**.
3. Pastikan Anda memiliki SDK Android versi 26 atau lebih tinggi.
4. Lakukan **Gradle Sync** dan jalankan aplikasi pada emulator atau perangkat fisik.

## 📸 Tampilan Aplikasi

| Home Screen | Manual Entry | History List | Solution Detail |
| :---: | :---: | :---: | :---: |
| Antarmuka utama yang bersih | Input warna yang intuitif | Daftar riwayat tersimpan | Langkah solusi detail |

---

Dibuat dengan ❤️ untuk para pecinta Rubik. 🧩
