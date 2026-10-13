package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PoskestrenRepository
import com.example.ui.components.RemoteImage
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@Composable
fun LandingScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToQueue: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeStep by remember { mutableIntStateOf(1) }
    var nisQuery by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ClinicalBackground),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Hero Banner
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = ClinicalSurfaceContainerHighest
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Emergency,
                                contentDescription = null,
                                tint = ClinicalPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "24 JAM SIAGA MEDIS PESANTREN",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ClinicalPrimary
                            )
                        }
                    }

                    Text(
                        text = "Layanan Kesehatan Terpadu & Triase Cerdas Santri Al-Bahjah Bogor",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ClinicalOnSurface,
                        lineHeight = 26.sp
                    )

                    Text(
                        text = "Pelayanan medis terpadu pesantren dengan sistem pendukung keputusan triase cepat berbasis metode SAW untuk penanganan santri tepat & tanggap.",
                        fontSize = 13.sp,
                        color = ClinicalOnSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    // Hero Image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        RemoteImage(
                            url = PoskestrenRepository.IMG_HERO_CLINIC,
                            contentDescription = "Poskestren Al-Bahjah",
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color(0xCC042019))
                                    )
                                )
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "STATUS IGD HARI INI",
                                        fontSize = 10.sp,
                                        color = ClinicalPrimaryFixed,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Ruang Rawat Terpantau Tenang",
                                        fontSize = 13.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = ClinicalPrimary
                                ) {
                                    Text(
                                        text = "Aktif Siaga",
                                        fontSize = 11.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    // CTA Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onNavigateToLogin,
                            colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.MedicalServices, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Masuk Portal", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = onNavigateToQueue,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ClinicalSecondary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Cek Antrean", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Trust indicators
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(14.dp))
                            Text("Dinkes Kab. Bogor", fontSize = 11.sp, color = ClinicalOnSurfaceVariant)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.Balance, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(14.dp))
                            Text("Algoritma SAW Terkalibrasi", fontSize = 11.sp, color = ClinicalOnSurfaceVariant)
                        }
                    }
                }
            }
        }

        // 4 KPI Metrics Strip
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MetricMiniCard(
                        title = "TOTAL PENANGANAN",
                        value = "3.420",
                        subtitle = "Santri Terlayani Periode Ini",
                        icon = Icons.Default.Groups,
                        modifier = Modifier.weight(1f)
                    )
                    MetricMiniCard(
                        title = "AKURASI SAW",
                        value = "99.4%",
                        subtitle = "Ketepatan Triase Otomatis",
                        icon = Icons.Default.Analytics,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MetricMiniCard(
                        title = "EFISIENSI RESPON",
                        value = "15 Mnt",
                        subtitle = "Rata-rata Waktu Tanggap",
                        icon = Icons.Default.Timer,
                        modifier = Modifier.weight(1f)
                    )
                    MetricMiniCard(
                        title = "SDM KLINIS",
                        value = "4",
                        subtitle = "Tenaga Medis Bersertifikat STR",
                        icon = Icons.Default.Badge,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Jam Operasional & Layanan Poskestren
        item {
            SectionHeader(
                tag = "JADWAL TUGAS MEDIS",
                title = "Jam Operasional & Layanan Poskestren",
                subtitle = "Menjamin kesinambungan kesehatan santri mukim putra dan putri dengan pergantian shift terstruktur."
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ShiftCard(
                    title = "Shift Pagi (Rawat Jalan)",
                    time = "07.30 - 12.00 WIB",
                    points = listOf(
                        "Screening harian santri asrama",
                        "Konsultasi dokter umum & resep obat",
                        "Pemeriksaan tanda vital dasar"
                    ),
                    capacity = "Kapasitas Poli: 35 Santri / Hari",
                    icon = Icons.Default.WbSunny,
                    isEmergency = false
                )

                ShiftCard(
                    title = "Shift Sore (Poli Lanjutan)",
                    time = "13.30 - 17.30 WIB",
                    points = listOf(
                        "Pemeriksaan lanjutan & observasi",
                        "Perawatan luka & tindakan ringan",
                        "Edukasi sanitasi kamar santri"
                    ),
                    capacity = "Kapasitas Poli: 25 Santri / Hari",
                    icon = Icons.Default.WbTwilight,
                    isEmergency = false
                )

                ShiftCard(
                    title = "Shift Malam & UGD (Pelayanan Kritis)",
                    time = "24 Jam Standby",
                    points = listOf(
                        "Triase gawat darurat santri demam tinggi",
                        "Ambulans siaga rujukan RSUD Leuwiliang/Ciawi",
                        "Perawat asrama piket on-site"
                    ),
                    capacity = "Tersedia 4 Bed Observasi Isolasi",
                    icon = Icons.Default.LocalHospital,
                    isEmergency = true
                )
            }
        }

        // SOP Workflow: 4-Step Interactive
        item {
            SectionHeader(
                tag = "STANDARD OPERATING PROCEDURE",
                title = "Alur Pelayanan Santri Terintegrasi",
                subtitle = "Sistem mengombinasikan pemeriksaan fisik perawat dengan algoritma Simple Additive Weighting (SAW) untuk menentukan urgensi."
            )
        }

        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Step selection tabs
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        for (step in 1..4) {
                            val isSelected = activeStep == step
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) ClinicalPrimary else ClinicalSurfaceContainerLow,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { activeStep = step }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "0$step",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else ClinicalPrimary
                                    )
                                    Text(
                                        text = when(step) {
                                            1 -> "Screening"
                                            2 -> "SAW"
                                            3 -> "Dokter"
                                            else -> "Farmasi"
                                        },
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isSelected) Color.White else ClinicalOnSurfaceVariant,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }

                    // Content of selected step
                    when (activeStep) {
                        1 -> StepDetailCard(
                            stepNumber = "1",
                            title = "Registrasi Identitas & Screening Vital Awal",
                            description = "Santri menyerahkan Kartu Tanda Santri (NIS) atau dipindai barcode. Petugas mencatat keluhan utama, mengukur suhu tubuh non-kontak, SpO2, dan denyut nadi.",
                            tag = "Tahap Awal Kedatangan Santri",
                            params = "Suhu, SpO2, Tekanan Darah (< 3 Menit/Santri)",
                            imageUrl = PoskestrenRepository.IMG_NURSE_SCREENING
                        )
                        2 -> StepDetailCard(
                            stepNumber = "2",
                            title = "Triase Cerdas Metode SAW (Simple Additive Weighting)",
                            description = "Data klinis dinormalisasi berdasarkan matriks kriteria (Suhu 30%, Nyeri 25%, Komorbid 15%, Durasi 15%, Vital 15%). Algoritma menghitung skor Vi untuk klasifikasi Zona Merah (>0.75), Kuning (0.45-0.75), Hijau (<0.45).",
                            tag = "Algoritma Pendukung Keputusan",
                            params = "Formula: Vi = ∑(wj * rij) • Otomatis & Transparan",
                            imageUrl = null
                        )
                        3 -> StepDetailCard(
                            stepNumber = "3",
                            title = "Pemeriksaan Dokter & Penatalaksanaan Medis",
                            description = "Dokter penanggung jawab memeriksa santri berdasarkan urutan prioritas triase. Dilakukan tindakan definitif, nebulisasi, terapi suportif, atau observasi isolasi.",
                            tag = "Tindakan Klinis Profesional",
                            params = "Fasilitas: Nebulizer, Oksigen Konsentrator, EKG Mini",
                            imageUrl = PoskestrenRepository.IMG_DOCTOR_EXAM
                        )
                        else -> StepDetailCard(
                            stepNumber = "4",
                            title = "Farmasi Obat, Istirahat & Edukasi Santri",
                            description = "Apoteker Poskestren menyiapkan obat sesuai E-Prescription terintegrasi. Dilengkapi surat dispensasi KBM yang tersinkronisasi otomatis ke bagian pengasuhan asrama.",
                            tag = "Pengobatan & Pemantauan",
                            params = "Sinkronisasi WhatsApp ke Pembina & Wali Santri",
                            imageUrl = PoskestrenRepository.IMG_PHARMACY
                        )
                    }
                }
            }
        }

        // Jadwal Dokter Jaga Hari Ini
        item {
            SectionHeader(
                tag = "UPDATE REAL-TIME",
                title = "Jadwal Dokter & Perawat Hari Ini",
                subtitle = "Pergantian dokter jaga dilakukan teratur dengan operan rekam medis terpadu."
            )
        }

        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    PoskestrenRepository.doctorSchedules.forEach { doc ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = ClinicalSurfaceContainerLow
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(ClinicalPrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = doc.initials,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = doc.nama,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ClinicalOnSurface
                                    )
                                    Text(
                                        text = "${doc.jabatan} • ${doc.shift}",
                                        fontSize = 11.sp,
                                        color = ClinicalOnSurfaceVariant
                                    )
                                    Text(
                                        text = "${doc.jam} (${doc.lokasi})",
                                        fontSize = 10.sp,
                                        color = ClinicalPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                if (doc.isActiveNow) {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = ClinicalSecondaryContainer
                                    ) {
                                        Text(
                                            text = "Aktif",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = ClinicalPrimary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Cek Antrean Online & Hotline
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cek Antrean Santri Hari Ini",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = ClinicalOnSurface
                        )
                        Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSecondaryContainer) {
                            Text(
                                text = "LIVE DATA",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ClinicalPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "Ketik Nomor Induk Santri (NIS) untuk melihat estimasi waktu pemeriksaan.",
                        fontSize = 12.sp,
                        color = ClinicalOnSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = nisQuery,
                            onValueChange = { nisQuery = it },
                            placeholder = { Text("Contoh: 2023-0891 atau 0491", fontSize = 12.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = {
                                if (nisQuery.isBlank()) {
                                    searchResult = "Silakan masukkan NIS santri."
                                } else if (nisQuery.contains("0891") || nisQuery.contains("fatih", ignoreCase = true)) {
                                    searchResult = "Antrean #01: Muhammad Fatih (Abu Bakar 03) - Sedang Dalam Tindakan di Poli 1"
                                } else {
                                    searchResult = "Antrean #A-14: Santri NIS ${nisQuery.trim()} - Menunggu Panggilan Dokter di Poli Sore"
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Cari", fontWeight = FontWeight.Bold)
                        }
                    }

                    if (searchResult != null) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = ClinicalSurfaceContainerLow,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = searchResult!!,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = ClinicalPrimary,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                    Divider(color = ClinicalOutlineVariant.copy(alpha = 0.4f), modifier = Modifier.padding(vertical = 4.dp))

                    // WhatsApp Emergency Callout
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = ClinicalPrimaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(Icons.Default.PhoneInTalk, contentDescription = null, tint = Color.White)
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Hotline Darurat 24 Jam", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("WhatsApp Siaga: 0812-9876-5432 (Ambulans Poskestren)", fontSize = 11.sp, color = ClinicalSurfaceContainerLow)
                            }
                        }
                    }
                }
            }
        }

        // Islamic Health Quote Banner
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = ClinicalSurfaceContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = null,
                        tint = ClinicalPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "“Dua nikmat yang sering dilupakan oleh kebanyakan manusia, yaitu kesehatan dan waktu luang.”",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ClinicalOnSurface,
                        textAlign = TextAlign.Center,
                        lineHeight = 19.sp
                    )
                    Text(
                        text = "— HR. Bukhari no. 6412 • Pedoman Kebersihan & Kesehatan Pondok Pesantren Al-Bahjah",
                        fontSize = 11.sp,
                        color = ClinicalOnSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Institutional Footer
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = ClinicalInverseSurface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(ClinicalPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.LocalHospital, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text("Poskestren Al-Bahjah Bogor", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                            Text("Pos Kesehatan Pesantren Terpadu", fontSize = 10.sp, color = ClinicalSurfaceDim)
                        }
                    }

                    Text(
                        text = "Jl. Raya Dramaga - Bogor No. 45, Babakan, Dramaga, Kab. Bogor, Jawa Barat 16680 • Call Center: (0251) 862-9988",
                        fontSize = 10.sp,
                        color = ClinicalSurfaceDim,
                        lineHeight = 14.sp
                    )

                    Text(
                        text = "© 2025 Poskestren Al-Bahjah Bogor. Sistem Informasi Medis Santri v2.4 • Metode SAW Tervalidasi",
                        fontSize = 10.sp,
                        color = ClinicalInverseOnSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricMiniCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalOutline)
                Icon(icon, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(16.dp))
            }
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
            Text(text = subtitle, fontSize = 10.sp, color = ClinicalOnSurfaceVariant, maxLines = 1)
        }
    }
}

@Composable
private fun ShiftCard(
    title: String,
    time: String,
    points: List<String>,
    capacity: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isEmergency: Boolean
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isEmergency) ClinicalSurfaceContainerHigh else Color.White,
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isEmergency) ClinicalPrimaryContainer else ClinicalSurfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = if (isEmergency) Color.White else ClinicalPrimary, modifier = Modifier.size(20.dp))
                }
                Column {
                    Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                    Text(text = time, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                }
            }

            points.forEach { pt ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(12.dp))
                    Text(text = pt, fontSize = 11.sp, color = ClinicalOnSurfaceVariant)
                }
            }

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (isEmergency) ClinicalSurfaceContainerHighest else ClinicalSurfaceContainerLow,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = capacity,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ClinicalPrimary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun StepDetailCard(
    stepNumber: String,
    title: String,
    description: String,
    tag: String,
    params: String,
    imageUrl: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ClinicalSurfaceContainerLow, RoundedCornerShape(10.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = tag.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = ClinicalPrimary
        )
        Text(
            text = "$stepNumber. $title",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = ClinicalOnSurface
        )
        Text(
            text = description,
            fontSize = 12.sp,
            color = ClinicalOnSurfaceVariant,
            lineHeight = 17.sp
        )

        Surface(
            shape = RoundedCornerShape(6.dp),
            color = Color.White
        ) {
            Text(
                text = params,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = ClinicalOnSurface,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }

        if (imageUrl != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                RemoteImage(
                    url = imageUrl,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
