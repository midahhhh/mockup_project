package com.example.data

object PoskestrenRepository {

    // SAW Criteria Weights
    const val W_SUHU = 0.30
    const val W_NYERI = 0.25
    const val W_KOMORBID = 0.15
    const val W_DURASI = 0.15
    const val W_VITAL = 0.15

    // Image URLs from HTML
    const val IMG_HERO_CLINIC = "https://lh3.googleusercontent.com/aida-public/AB6AXuD0aDSGd5FOuncI-lrh1WBuZjYaM6D1U8kGnaB4rSShyR7y-CkAEdfIXpnokSztayeghnXbcR1s2Osup478zPTY8Dha94ukMMMzXK5lLNiEUCqNPGQlSDmMC680g2oX_UzLN-JEPkpi68xt9C9UM6YWq6MwdHSe5X_CQYkxMUjdqyi8l5EDpfzqBZH18AmEAyrTe5GfQZDe304VMQG6nmZRNIvHrj1MjB--_cPB43yAHmuG4q5lpR-g"
    const val IMG_NURSE_SCREENING = "https://lh3.googleusercontent.com/aida-public/AB6AXuApDOpnTHA0u1Xh5nPyDednYru_NYRvLf3q69uIWW492zPy1Y6OqTKjJ69nBfbnhRznFouq-o_L88YFXZRa9nFN6imMUagttjcQhSV0jXxzbslXmB-hgxK_oqS6sxL0xDdGkLDqYmtMxFPYGK5dPIGp3uRSQ2wai20OnAeS7qYJaR4-x2SYynh8xnZAcUo1DXG0kXNaZ25BTC8VajdZQOd3ZURcedpf8BlfL1rBb-l8uU848qzvAAqA"
    const val IMG_DOCTOR_EXAM = "https://lh3.googleusercontent.com/aida-public/AB6AXuD45mHq-DYiPqKWqESH9RRhs_LIiw7eaPDlFJu_LM3lRA3P6PuTzwy0Gtrkd_nWU-m6dyTHm_O9AUwtMVS_palw9VVd3u7nNzCJFT4zYluUw089PLBZ3m6YyKItaHRMxRYPfQxHhuSczhe7zIrtIQojyghnmxUXQiKSnuI3fKmUSscFpmcZTn1-C8ybFlhFa4dNVhPe1e1mj5I2QC4ZISuYVFdpqScT3tbgClg-ZQtL_UrkkU_2995D"
    const val IMG_PHARMACY = "https://lh3.googleusercontent.com/aida-public/AB6AXuA59bGFwjFf3L9cSVj3ShFPaL3xS6Iek4RDMh1Fv1MW6gEeIwA1rt2wfepFaN6co4QK4kkmSf8dTlrdALQqvXs7yPhq5w7kPij47L1SwDQP8tUCoGWXFvZwwk4SNpkHAwDV2vcBa656g-UtxPyMPg3KZHuNqJshuLtyugd5peiTxBE6fcVMLhBUCsPhd4PV_aXHHdbUVeAzZOK5wnZSsScvTrVQ66dJTg9ae2zBlOm4utKnkuD8dvSY"
    const val IMG_SANTRI_FATIH = "https://lh3.googleusercontent.com/aida-public/AB6AXuCj2o_flfjgvng6QH7qKI0V0ioasjeLdhC2YoXqTcOVESLgUO1YsZGZjREjqRzb9aD0WAieOFiKHRauxUInU117IgP1mRiGVFr6HwUO4xe6ATtAdXarypfGsOZ6ZVlDpHJ_x5uRpKp5P2_O87qweQK1Z_GTl4dNpSOoLb486Zbfc4YOfpuMEaPbESOh7FVuIkmt0i4K7cbSI7LEvFGjM3AeUiaYBK7RLeXRQWIQQ61d02UuKfIqEMTY"
    const val IMG_SANTRI_ZAKI = "https://lh3.googleusercontent.com/aida-public/AB6AXuAQbeu-zwxxHIMKmcDqsYjQEtA_YeYYsH6E85pg75U-6s5weToWM8NBTadUYWCZODG35-6YuILoredvd1RaFY6RDAS81z-J4PkPp-bi6q0ijR-6AGCQ9AJDzB2AzXMqzzGt56AXqz-3c6rQqpNVDIsfvOoKx8WiIPb-jDmB5WOz5fgsdppM122ltaBZeFkkMqMMwavAB4KvsWKybEm1z6SwuRf6L0YrN1HtN1_wSgWQqSW_16WGbTWZ"
    const val IMG_SANTRI_FARHAN = "https://lh3.googleusercontent.com/aida-public/AB6AXuC64L8k5Ff2BPu0xhXT2pXUGxf6tl9f5NRhi3ylJUwLX2Dt3RDn5_BJ18oniw4w3u17daG_ylQYkxCh0-SpmhCfJ3o-wLR_4oi97Z_UQrojVBR53lTvZSAcSIELWJg2tqlmIxbGHGb2DqWuJFVLyCDh24oXH_J12zsf4dMPrdxkEcTutQ7f47JfKEldghO9iN3giBso0XO0JcG4q42TSHZ6cP4UrpI13VUFbUP2rKydOYhGYsDVUfin"
    const val IMG_SANTRI_BILAL = "https://lh3.googleusercontent.com/aida-public/AB6AXuDaIUM-I6NE6TglCTXxKkojmFcsG-ZaoghWViZNJtrufrim_PY_Q1dq8sQ0tfADDw9wQk0mhqSEv2JiX-wel54ElKgvQspts8Dud4O6IJh86IomoHSb_e4rWQeQIZkUHGe8Owy2o4eRapzdm_mjmgPZpmmwcNQoqhKbGIcwu0r-VOcC-MHucKFyud8QCLqveiXQa-4aG-POGV6Ki39O07fgZpbu7S3asPtwnYlcRVptrcCg65Oo5LlG"
    const val IMG_DR_AGUS = "https://lh3.googleusercontent.com/aida-public/AB6AXuBwsO8zhpY_JLFM0dUWiFPTdHMv6U5PhuPxGHHPf0qfAXrXySCQBYfUiADz0tHLX-EvUH6LN-RGjHGnih-ykZ-nhCdZIgsvAgISh8-Z_WWQeTvQFOhV5iNGgwlB87mPnu9xegdXp_HHXCHxRcv1GxxKKGaFvT9vFlTx-xnJskDZp3sj4RR90AMdfZg_tptmAQQV52kdh4kYrIP9KtxyKfZ3QuLok6rR9cBAjI4I5_fxipDNt6DTpGNn"
    const val IMG_PHARMACIST_FEMALE = "https://lh3.googleusercontent.com/aida-public/AB6AXuBSQ-S7fjtC8kDw-jaRtTQPPyLzhRiLCD_Ls1ZG3oDZimFqHZc22pMe_g9IUJi0xin0nOsDkKSv19DCkgP7Akk7HCOHOvSSGwjIrVs2WaA8vb__n7eMPpuT9K2foGtGx5EfJItCKc9fYILGlmerkqFdI7yWh6z8TQGJuf3C2-x_Y69kZBWY36T6vckRfNPsjTxBtHKkf-wiaiPk6_FAdvUmzs_tDfFc6ERESKPoBl52Qfav5xy6_rhN"

    fun calculateSawScore(
        temp: Double,
        painScore: Int,
        comorbidVal: Double,
        durationVal: Double,
        vitalVal: Double
    ): SawCalculationResult {
        // Benefit normalization against maximum reference values
        val maxTemp = 40.0
        val rTemp = (temp / maxTemp).coerceIn(0.0, 1.0)

        val maxPain = 10.0
        val rPain = (painScore / maxPain).coerceIn(0.0, 1.0)

        val rComorbid = comorbidVal.coerceIn(0.0, 1.0)
        val rDuration = durationVal.coerceIn(0.0, 1.0)
        val rVital = vitalVal.coerceIn(0.0, 1.0)

        val c1 = W_SUHU * rTemp
        val c2 = W_NYERI * rPain
        val c3 = W_KOMORBID * rComorbid
        val c4 = W_DURASI * rDuration
        val c5 = W_VITAL * rVital

        val totalVi = c1 + c2 + c3 + c4 + c5

        val level = when {
            totalVi >= 0.75 -> TriageLevel.DARURAT_MERAH
            totalVi >= 0.45 -> TriageLevel.SEDANG_KUNING
            else -> TriageLevel.RINGAN_HIJAU
        }

        return SawCalculationResult(
            rTemp = rTemp,
            rPain = rPain,
            rComorbid = rComorbid,
            rDuration = rDuration,
            rVital = rVital,
            c1Weighted = c1,
            c2Weighted = c2,
            c3Weighted = c3,
            c4Weighted = c4,
            c5Weighted = c5,
            totalVi = totalVi,
            level = level
        )
    }

    data class SawCalculationResult(
        val rTemp: Double,
        val rPain: Double,
        val rComorbid: Double,
        val rDuration: Double,
        val rVital: Double,
        val c1Weighted: Double,
        val c2Weighted: Double,
        val c3Weighted: Double,
        val c4Weighted: Double,
        val c5Weighted: Double,
        val totalVi: Double,
        val level: TriageLevel
    )

    val doctorSchedules = listOf(
        DoctorSchedule("FA", "dr. Fathurrahman Al-Habsyi", "Dokter Penanggung Jawab Klinis", "Shift Pagi", "07.30 - 12.00 WIB", "Poli 1 (Rawat Jalan)", true),
        DoctorSchedule("SN", "dr. Siti Nurhaliza, Sp.A", "Konsultan Pediatri & Remaja", "Shift Sore", "13.30 - 17.30 WIB", "Poli Konsultasi", false),
        DoctorSchedule("AR", "Ns. Ahmad Rosyid, S.Kep", "Perawat Triase & Gawat Darurat", "Shift Malam & UGD", "24 Jam Standby", "Ruang UGD Poskestren", true)
    )

    val initialTriagePatients = listOf(
        TriagePatient(
            id = "T1",
            rank = 1,
            waktuDaftar = "14:10",
            nis = "2023-0891",
            nama = "Muhammad Fatih",
            asrama = "Abu Bakar",
            kamar = "Abu Bakar 03",
            suhu = 39.8,
            febrisLabel = "Febris Tinggi",
            skalaNyeri = 9,
            komorbid = "Asma Kronis",
            durasiSakit = "6 Jam (Onset Mendadak)",
            vitalKesadaran = "Compos Mentis Lemah",
            spo2 = 93,
            hr = 118,
            rr = 26,
            td = "110/70",
            c1Val = 0.298,
            c2Val = 0.225,
            c3Val = 0.150,
            c4Val = 0.142,
            c5Val = 0.150,
            viScore = 0.965,
            triageLevel = TriageLevel.DARURAT_MERAH,
            statusText = "Prioritas 1: Darurat Merah"
        ),
        TriagePatient(
            id = "T2",
            rank = 2,
            waktuDaftar = "14:15",
            nis = "2022-0412",
            nama = "Abdullah Zaki",
            asrama = "Umar",
            kamar = "Umar 02",
            suhu = 38.9,
            febrisLabel = "Febris Sedang",
            skalaNyeri = 8,
            komorbid = "Dispepsia Akut",
            durasiSakit = "4 Jam (Nyeri Epigastrium)",
            vitalKesadaran = "Compos Mentis",
            spo2 = 97,
            hr = 98,
            rr = 20,
            td = "100/70",
            c1Val = 0.260,
            c2Val = 0.200,
            c3Val = 0.130,
            c4Val = 0.125,
            c5Val = 0.125,
            viScore = 0.840,
            triageLevel = TriageLevel.DARURAT_MERAH,
            statusText = "Prioritas 2: Darurat Merah"
        ),
        TriagePatient(
            id = "T3",
            rank = 3,
            waktuDaftar = "14:18",
            nis = "2021-1104",
            nama = "Farhan Hilmi",
            asrama = "Ali",
            kamar = "Ali 01",
            suhu = 38.2,
            febrisLabel = "Subfebris",
            skalaNyeri = 5,
            komorbid = "Batuk Kronik",
            durasiSakit = "3 Hari (Flu & Batuk)",
            vitalKesadaran = "Compos Mentis",
            spo2 = 98,
            hr = 84,
            rr = 18,
            td = "110/75",
            c1Val = 0.220,
            c2Val = 0.125,
            c3Val = 0.110,
            c4Val = 0.120,
            c5Val = 0.120,
            viScore = 0.695,
            triageLevel = TriageLevel.SEDANG_KUNING,
            statusText = "Prioritas 3: Sedang Kuning"
        ),
        TriagePatient(
            id = "T4",
            rank = 4,
            waktuDaftar = "14:22",
            nis = "2023-1490",
            nama = "Ahmad Syakir",
            asrama = "Utsman",
            kamar = "Utsman 04",
            suhu = 37.6,
            febrisLabel = "Ringan",
            skalaNyeri = 4,
            komorbid = "Riwayat Maag",
            durasiSakit = "1 Hari (Mual Pasca Santap)",
            vitalKesadaran = "Compos Mentis",
            spo2 = 99,
            hr = 78,
            rr = 16,
            td = "115/80",
            c1Val = 0.190,
            c2Val = 0.100,
            c3Val = 0.090,
            c4Val = 0.100,
            c5Val = 0.100,
            viScore = 0.580,
            triageLevel = TriageLevel.SEDANG_KUNING,
            statusText = "Prioritas 4: Sedang Kuning"
        ),
        TriagePatient(
            id = "T5",
            rank = 5,
            waktuDaftar = "14:26",
            nis = "2024-0021",
            nama = "Bilal Ramadhan",
            asrama = "Hamzah",
            kamar = "Hamzah 01",
            suhu = 36.8,
            febrisLabel = "Normal",
            skalaNyeri = 2,
            komorbid = "Tidak Ada",
            durasiSakit = "2 Jam (Lecet Lutut)",
            vitalKesadaran = "Compos Mentis Baik",
            spo2 = 99,
            hr = 72,
            rr = 16,
            td = "120/80",
            c1Val = 0.100,
            c2Val = 0.050,
            c3Val = 0.050,
            c4Val = 0.060,
            c5Val = 0.060,
            viScore = 0.320,
            triageLevel = TriageLevel.RINGAN_HIJAU,
            statusText = "Prioritas 5: Ringan Hijau"
        )
    )

    val initialSantriList = listOf(
        Santri(
            nis = "2023-0891",
            nama = "Muhammad Fatih",
            gender = "Laki-laki",
            usia = 16,
            tglLahir = "2008-04-12",
            jenjang = "Kelas 10A",
            asrama = "Abu Bakar",
            kamar = "Abu Bakar 03",
            golDarah = "A+",
            alergi = "Dingin & Debu",
            namaWali = "H. Fauzi",
            hpWali = "0812-3456-7890",
            riwayatBerobatCount = 4,
            status = SantriHealthStatus.DIPERIKSA_POSKESTREN,
            avatarUrl = IMG_SANTRI_FATIH
        ),
        Santri(
            nis = "2022-0412",
            nama = "Abdullah Zaki",
            gender = "Laki-laki",
            usia = 17,
            tglLahir = "2007-09-18",
            jenjang = "Kelas 11B",
            asrama = "Umar",
            kamar = "Umar 02",
            golDarah = "O+",
            alergi = "Tidak Ada",
            namaWali = "Bpk. Rahmat",
            hpWali = "0813-9876-5432",
            riwayatBerobatCount = 2,
            status = SantriHealthStatus.ANTREAN_TRIASE,
            avatarUrl = IMG_SANTRI_ZAKI
        ),
        Santri(
            nis = "2021-1104",
            nama = "Farhan Hilmi",
            gender = "Laki-laki",
            usia = 18,
            tglLahir = "2006-03-05",
            jenjang = "Kelas 12C",
            asrama = "Ali",
            kamar = "Ali 01",
            golDarah = "B+",
            alergi = "Penisilin (Fatal)",
            namaWali = "Bpk. Hilman",
            hpWali = "0821-4567-8901",
            riwayatBerobatCount = 6,
            status = SantriHealthStatus.SEHAT_DI_ASRAMA,
            avatarUrl = IMG_SANTRI_FARHAN
        ),
        Santri(
            nis = "2024-0021",
            nama = "Bilal Ramadhan",
            gender = "Laki-laki",
            usia = 13,
            tglLahir = "2011-06-25",
            jenjang = "Kelas 7A",
            asrama = "Hamzah",
            kamar = "Hamzah 01",
            golDarah = "AB+",
            alergi = "Seafood",
            namaWali = "Ibu Maryam",
            hpWali = "0852-1122-3344",
            riwayatBerobatCount = 1,
            status = SantriHealthStatus.SEHAT_DI_ASRAMA,
            avatarUrl = IMG_SANTRI_BILAL
        ),
        Santri(
            nis = "2023-1490",
            nama = "Ahmad Syakir",
            gender = "Laki-laki",
            usia = 15,
            tglLahir = "2009-02-14",
            jenjang = "Kelas 9B",
            asrama = "Utsman",
            kamar = "Utsman 04",
            golDarah = "O+",
            alergi = "Kacang Tanah",
            namaWali = "Bpk. Syakur",
            hpWali = "0819-2233-4455",
            riwayatBerobatCount = 3,
            status = SantriHealthStatus.IZIN_ISTIRAHAT,
            avatarUrl = ""
        ),
        Santri(
            nis = "2410-0115",
            nama = "Siti Maryam Azzahra",
            gender = "Perempuan",
            usia = 14,
            tglLahir = "2010-07-20",
            jenjang = "Kelas 8C",
            asrama = "Khadijah",
            kamar = "Khadijah 02",
            golDarah = "A+",
            alergi = "Susu Sapi (Laktosa)",
            namaWali = "Ustadzah Aisyah",
            hpWali = "0812-7788-9900",
            riwayatBerobatCount = 2,
            status = SantriHealthStatus.SEHAT_DI_ASRAMA,
            avatarUrl = ""
        )
    )

    val initialMedicines = listOf(
        Obat(
            kode = "OBT-001",
            nama = "Paracetamol Drop 100mg/mL",
            deskripsi = "Drops Oral 15mL • Paracetamol Murni",
            kategori = "Antipiretik Anak",
            sisaStok = 4,
            satuan = "Botol",
            tglExpired = "2025-11-20",
            lokasiRak = "Rak A-02",
            nomorBatch = "BTH-2024-099",
            batasMinimum = 20,
            status = MedicineStatus.KRITIS
        ),
        Obat(
            kode = "OBT-014",
            nama = "Amoxicillin Trihydrate 500mg",
            deskripsi = "Kapsul • Antibiotik Spektrum Luas",
            kategori = "Antibiotik",
            sisaStok = 38,
            satuan = "Strip",
            tglExpired = "2024-12-15",
            lokasiRak = "Rak B-01",
            nomorBatch = "BTH-2023-412",
            batasMinimum = 15,
            status = MedicineStatus.KADALUWARSA_DEKAT
        ),
        Obat(
            kode = "OBT-025",
            nama = "Ventolin Inhaler 100mcg",
            deskripsi = "Salbutamol Aerosol 200 dosis • UGD Fast-Response",
            kategori = "Bronkodilator / Asma",
            sisaStok = 2,
            satuan = "Canister",
            tglExpired = "2026-08-14",
            lokasiRak = "Rak Emergency-1",
            nomorBatch = "BTH-2024-811",
            batasMinimum = 6,
            status = MedicineStatus.MENIPIS
        ),
        Obat(
            kode = "OBT-032",
            nama = "Antasida Doen Tablet Kunyah",
            deskripsi = "Aluminium Hidroksida / Magnesium Hidroksida",
            kategori = "Antasida / Maag",
            sisaStok = 120,
            satuan = "Strip",
            tglExpired = "2026-03-10",
            lokasiRak = "Rak A-05",
            nomorBatch = "BTH-2024-302",
            batasMinimum = 25,
            status = MedicineStatus.AMAN
        ),
        Obat(
            kode = "OBT-048",
            nama = "Betadine Antiseptic Solution 60mL",
            deskripsi = "Povidone Iodine 10% • Perawatan Luka Poskestren",
            kategori = "Antiseptik Topikal",
            sisaStok = 18,
            satuan = "Botol",
            tglExpired = "2026-06-05",
            lokasiRak = "Rak C-02",
            nomorBatch = "BTH-2024-519",
            batasMinimum = 10,
            status = MedicineStatus.AMAN
        ),
        Obat(
            kode = "OBT-060",
            nama = "Cetirizine HCl 10mg",
            deskripsi = "Tablet Salut Selaput • Antihistamin Alergi Santri",
            kategori = "Antihistamin",
            sisaStok = 45,
            satuan = "Strip",
            tglExpired = "2026-01-18",
            lokasiRak = "Rak A-08",
            nomorBatch = "BTH-2024-210",
            batasMinimum = 15,
            status = MedicineStatus.AMAN
        )
    )

    val initialPrescriptions = listOf(
        Prescription(
            id = "R1",
            noResep = "#RXP-2024-089",
            waktu = "10:12 WIB",
            nis = "2023-0891",
            namaSantri = "Muhammad Fatih",
            kamar = "Kamar Abu Bakar 03",
            dokter = "dr. Agus Syaifullah",
            items = listOf(
                PrescriptionItem("R/ Paracetamol Drop 100mg", "3x1 cth sesudah makan", "Sirup tetes oral anak", "Tersisa 4 Btl (Kritis)", true),
                PrescriptionItem("R/ Salbutamol 2mg", "3x1 tab post-prandial", "Bronkodilator", "Stok Aman (84 tab)", false),
                PrescriptionItem("R/ Vitamin B-Complex", "1x1 tab pagi", "Suplemen pemulihan", "Stok Aman", false)
            ),
            status = PrescriptionStatus.MENUNGGU_DIPROSES,
            catatan = "Triase Darurat - Berikan kompres hangat dan nebulasi"
        ),
        Prescription(
            id = "R2",
            noResep = "#RXP-2024-090",
            waktu = "10:28 WIB",
            nis = "2208119",
            namaSantri = "Zaki Ahmad",
            kamar = "Kamar Usman 01",
            dokter = "dr. Agus Syaifullah",
            items = listOf(
                PrescriptionItem("R/ Ventolin Inhaler 100mcg", "2 puff saat sesak", "Aerosol Inhalasi", "Tersedia 3 Unit", false),
                PrescriptionItem("R/ Dexamethasone 0.5mg", "3x1 tab sesudah makan", "Anti-inflamasi", "Stok Aman", false)
            ),
            status = PrescriptionStatus.MENUNGGU_DIPROSES,
            catatan = "Darurat (Asma Akut) - Simpan di saku santri"
        ),
        Prescription(
            id = "R3",
            noResep = "#RXP-2024-085",
            waktu = "09:40 WIB",
            nis = "2311002",
            namaSantri = "Abdullah Zaki",
            kamar = "Kamar Umar 02",
            dokter = "dr. Agus Syaifullah",
            items = listOf(
                PrescriptionItem("Amoxicillin Dry Syr 125mg/5ml", "3x1 cth habiskan", "Antibiotik", "Terkemas", false),
                PrescriptionItem("Paracetamol Syr 120mg/5ml", "3x1 cth prn demam", "Antipiretik", "Terkemas", false),
                PrescriptionItem("Cetirizine Syr 5mg/5ml", "1x1 cth malam", "Antihistamin", "Terkemas", false)
            ),
            status = PrescriptionStatus.SIAP_DIAMBIL,
            catatan = "Telah diberi etiket jelas dan instruksi minum",
            penerima = "Pembina Asrama Umar"
        ),
        Prescription(
            id = "R4",
            noResep = "#RXP-2024-086",
            waktu = "09:55 WIB",
            nis = "2308112",
            namaSantri = "Hamzah Al-Ghazali",
            kamar = "Kamar Ali 04",
            dokter = "dr. Siti Nurhaliza",
            items = listOf(
                PrescriptionItem("Antasida Doen Tablet", "3x1 tab kunyah ac", "Antasida", "Tersedia", false),
                PrescriptionItem("Domperidone 10mg", "3x1 tab 30m ac", "Antiemetik", "Tersedia", false)
            ),
            status = PrescriptionStatus.SIAP_DIAMBIL,
            catatan = "Minum 15 menit sebelum makan"
        ),
        Prescription(
            id = "R5",
            noResep = "#RXP-2024-081",
            waktu = "10:15 WIB",
            nis = "2107055",
            namaSantri = "Bilal Ramadhan",
            kamar = "Kamar Hamzah 01",
            dokter = "dr. Agus Syaifullah",
            items = listOf(
                PrescriptionItem("Betadine Ointment 5g", "Oles 2x sehari pada lecet", "Antiseptik", "Diserahkan", false),
                PrescriptionItem("Kassa Steril & Plester", "Ganti perban setiap pagi", "Alkes", "Diserahkan", false)
            ),
            status = PrescriptionStatus.SELESAI_DISERAHKAN,
            penerima = "Pembina Asrama Ust. Ridwan",
            waktuPenyerahan = "Hari ini, Pukul 10.15 WIB"
        )
    )

    val medicalRecordEntries = listOf(
        MedicalRecordEntry("01", "24 Okt 2024", "RM-2024-0982", "23091024", "Muhammad Farhan", "Kelas 8A", "Abu Bakar", "Kamar B-04", "Febris Hari ke-2 + ISPA", "ICD-10: R50.9 / J06.9", "Suhu 38.6°C, batuk pilek, lesu", 0.785, "Moderat", "Observasi Suhu, Kompres Hangat, Istirahat Asrama", "Paracetamol 500mg, Ambroxol, Vit C", "dr. Agus Syaifullah"),
        MedicalRecordEntry("02", "23 Okt 2024", "RM-2024-0980", "22080412", "Ahmad Zaki Al-Fatih", "Kelas 9C", "Umar", "Kamar U-12", "Gastritis Erosif Akut", "ICD-10: K29.7", "Nyeri Epigastrik 6/10, mual berulang", 0.740, "Moderat", "Edukasi Diet Lunak, Infus RL 500ml (Rawat 1 Hari)", "Antasida Doen, Omeprazole 20mg, Sukralfat", "dr. Agus Syaifullah"),
        MedicalRecordEntry("03", "23 Okt 2024", "RM-2024-0975", "24100115", "Siti Maryam Azzahra", "Kelas 7B", "Khadijah", "Kamar K-03", "Tinea Corporis & Prurigo", "ICD-10: B35.4", "Gatal kemerahan di leher & punggung", 0.412, "Ringan", "Konseling Higiene Kasur & Pakaian Kering", "Ketoconazole 2% cream, Cetirizine 10mg", "Ns. Siti Rahmah, S.Kep"),
        MedicalRecordEntry("04", "22 Okt 2024", "RM-2024-0968", "23091180", "Fadhil Nur Hidayat", "Kelas 8C", "Utsman", "Kamar T-08", "Sprain Ankle Dextra Gr. 1", "ICD-10: S93.4", "Kaki terkilir futsal asrama, bengkak ringan", 0.530, "Ringan", "Protokol R.I.C.E, Balut Elastic Bandage 4 Inch", "Natrium Diklofenak 50mg, Gel Heparin", "Ns. Arif Budiman, A.Md"),
        MedicalRecordEntry("05", "21 Okt 2024", "RM-2024-0960", "22080344", "Nabila Husna Khairunnisa", "Kelas 9A", "Aisyah", "Kamar A-01", "Asma Bronkiale Serangan Sedang", "ICD-10: J45.9", "Sesak nafas, wheezing, SpO2 93%", 0.915, "Gawat/Prioritas", "Nebulisasi Combivent 1 Resp, O2 Kanul 3 lpm", "Salbutamol 2mg, Metilprednisolon 4mg", "dr. Agus Syaifullah")
    )
}
