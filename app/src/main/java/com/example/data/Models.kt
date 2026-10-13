package com.example.data

enum class TriageLevel {
    DARURAT_MERAH,
    SEDANG_KUNING,
    RINGAN_HIJAU
}

enum class SantriHealthStatus {
    SEHAT_DI_ASRAMA,
    ANTREAN_TRIASE,
    DIPERIKSA_POSKESTREN,
    IZIN_ISTIRAHAT,
    RAWAT_INAP
}

enum class MedicineStatus {
    AMAN,
    MENIPIS,
    KRITIS,
    KADALUWARSA_DEKAT
}

enum class PrescriptionStatus {
    MENUNGGU_DIPROSES,
    SIAP_DIAMBIL,
    SELESAI_DISERAHKAN
}

data class Santri(
    val nis: String,
    val nama: String,
    val gender: String = "Laki-laki",
    val usia: Int,
    val tglLahir: String = "2008-05-10",
    val jenjang: String,
    val asrama: String,
    val kamar: String,
    val golDarah: String,
    val alergi: String,
    val namaWali: String,
    val hpWali: String,
    val riwayatBerobatCount: Int = 1,
    val status: SantriHealthStatus = SantriHealthStatus.SEHAT_DI_ASRAMA,
    val avatarUrl: String = ""
)

data class Obat(
    val kode: String,
    val nama: String,
    val deskripsi: String,
    val kategori: String,
    val sisaStok: Int,
    val satuan: String,
    val tglExpired: String,
    val lokasiRak: String,
    val nomorBatch: String,
    val batasMinimum: Int = 5,
    val status: MedicineStatus = MedicineStatus.AMAN
)

data class PrescriptionItem(
    val namaObat: String,
    val aturanPakai: String,
    val jenis: String,
    val stokInfo: String = "Stok Aman",
    val warning: Boolean = false
)

data class Prescription(
    val id: String,
    val noResep: String,
    val waktu: String,
    val nis: String,
    val namaSantri: String,
    val kamar: String,
    val dokter: String,
    val items: List<PrescriptionItem>,
    val status: PrescriptionStatus,
    val penerima: String = "",
    val waktuPenyerahan: String = "",
    val catatan: String = ""
)

data class TriagePatient(
    val id: String,
    val rank: Int,
    val waktuDaftar: String,
    val nis: String,
    val nama: String,
    val asrama: String,
    val kamar: String,
    val suhu: Double,
    val febrisLabel: String,
    val skalaNyeri: Int,
    val komorbid: String,
    val durasiSakit: String,
    val vitalKesadaran: String,
    val spo2: Int,
    val hr: Int,
    val rr: Int,
    val td: String,
    val c1Val: Double, // normalized or weighted
    val c2Val: Double,
    val c3Val: Double,
    val c4Val: Double,
    val c5Val: Double,
    val viScore: Double,
    val triageLevel: TriageLevel,
    val statusText: String
)

data class MedicalRecordEntry(
    val no: String,
    val tanggal: String,
    val noRM: String,
    val nis: String,
    val namaSantri: String,
    val kelas: String,
    val asrama: String,
    val kamar: String,
    val diagnosaUtama: String,
    val icd10: String,
    val keluhanKlinis: String,
    val skorVi: Double,
    val triageCategory: String,
    val tindakanMedis: String,
    val obatDiberikan: String,
    val dokterDpjp: String
)

data class DoctorSchedule(
    val initials: String,
    val nama: String,
    val jabatan: String,
    val shift: String,
    val jam: String,
    val lokasi: String,
    val isActiveNow: Boolean = false
)
