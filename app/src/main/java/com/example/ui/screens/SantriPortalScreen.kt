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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PoskestrenRepository
import com.example.ui.components.RemoteImage
import com.example.ui.theme.*

@Composable
fun SantriPortalScreen(
    modifier: Modifier = Modifier
) {
    var isWaliMode by remember { mutableStateOf(true) }
    var actionToast by remember { mutableStateOf<String?>(null) }
    var expandedDetailDate by remember { mutableStateOf<String?>(null) }

    Box(modifier = modifier.fillMaxSize().background(ClinicalBackground)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Profile & Switcher
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                RemoteImage(
                                    url = PoskestrenRepository.IMG_SANTRI_FATIH,
                                    contentDescription = "Santri Fatih",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text("Muhammad Fatih", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                                    Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSurfaceContainerLow) {
                                        Text("NIS: 2023-0891", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                    }
                                }
                                Text("Kelas 10A • Kamar Abu Bakar 03", fontSize = 11.sp, color = ClinicalOnSurfaceVariant)
                                Text("Kompleks Putra Pondok Pesantren Al-Bahjah", fontSize = 10.sp, color = ClinicalOutline)
                            }
                        }

                        // Mode Selector Toggle
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(ClinicalSurfaceContainerLow)
                                .padding(3.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Button(
                                onClick = { isWaliMode = false },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (!isWaliMode) ClinicalPrimary else Color.Transparent,
                                    contentColor = if (!isWaliMode) Color.White else ClinicalOnSurfaceVariant
                                ),
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = PaddingValues(vertical = 6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Tampilan Santri", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }

                            Button(
                                onClick = { isWaliMode = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isWaliMode) ClinicalPrimary else Color.Transparent,
                                    contentColor = if (isWaliMode) Color.White else ClinicalOnSurfaceVariant
                                ),
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = PaddingValues(vertical = 6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.SupervisorAccount, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Wali: H. Fauzi", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            // Security & Privacy Notice
            item {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = ClinicalSurfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(18.dp))
                        Text(
                            text = "Portal Terverifikasi & Terenkripsi: Menampilkan rekam medis dan perizinan santri binaan yang diverifikasi dokter.",
                            fontSize = 11.sp,
                            color = ClinicalOnSurfaceVariant,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            // Status Kunjungan Hari Ini Card
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(ClinicalError))
                                Text("KUNJUNGAN HARI INI", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalError)
                            }
                            Surface(shape = RoundedCornerShape(4.dp), color = ClinicalErrorContainer) {
                                Text("TINDAKAN MEDIS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalError, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("NOMOR ANTREAN TRIASE", fontSize = 10.sp, color = ClinicalOutline)
                                Text("#01", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = ClinicalError)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("LOKASI PEMERIKSAAN", fontSize = 10.sp, color = ClinicalOutline)
                                Text("Poli 1 (dr. Agus)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                            }
                        }

                        // Progress step
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Progres Pelayanan:", fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
                                Text("Tahap 3 dari 4 (Konsultasi Dokter)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                            }
                            LinearProgressIndicator(
                                progress = { 0.75f },
                                color = ClinicalPrimary,
                                trackColor = ClinicalSurfaceContainerLow,
                                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
                            )
                        }

                        // Estimasi
                        Surface(shape = RoundedCornerShape(8.dp), color = ClinicalSurfaceContainerLow, modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.Timer, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(16.dp))
                                Text(
                                    text = "Estimasi selesai pukul 10:45 WIB sebelum santri diantar istirahat ke asrama.",
                                    fontSize = 11.sp,
                                    color = ClinicalOnSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // Rekomendasi Istirahat Asrama (Bed Rest)
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = ClinicalSurfaceContainer,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.MedicalInformation, contentDescription = null, tint = ClinicalPrimary)
                                Text("Rekomendasi Istirahat Asrama", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                            }
                            Surface(shape = RoundedCornerShape(10.dp), color = ClinicalPrimaryContainer) {
                                Text("Resmi", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Surface(shape = RoundedCornerShape(8.dp), color = Color.White, modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Durasi Izin Sakit (Bed Rest):", fontSize = 11.sp, color = ClinicalOutline)
                                    Text("2 Hari Penuh", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                                }
                                Text("Periode: 24 Okt 2024 s/d 26 Okt 2024", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = ClinicalOnSurface)
                                Text("Dispensasi dari KBM sekolah, halaqah Quran, dan tugas piket santri.", fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Pengurus Asrama Pendamping:", fontSize = 10.sp, color = ClinicalOutline)
                                Text("Ust. Ridwan Fauzi", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                            }
                            Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSecondaryContainer) {
                                Text("Terkonfirmasi", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        // Download surat & Call Center Buttons
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { actionToast = "Menghubungi Call Center Poskestren Al-Bahjah..." },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Call, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Call Center", color = ClinicalPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { actionToast = "Surat Izin Sakit PDF Muhammad Fatih berhasil diunduh." },
                                colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Unduh Surat", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Resep Obat Hari Ini
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Resep Obat Hari Ini", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                            Text("24 Okt 2024", fontSize = 10.sp, color = ClinicalOutline)
                        }

                        // Obat 1
                        Surface(shape = RoundedCornerShape(8.dp), color = ClinicalSurfaceContainerLow, modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Paracetamol Drop 100mg/ml", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                                    Surface(shape = RoundedCornerShape(4.dp), color = ClinicalTertiaryFixed) {
                                        Text("Disiapkan", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalOnTertiaryFixed, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                    }
                                }
                                Text("Dosis: 3 x 0.8 ml sesudah makan (Anti Piretik)", fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
                                Text("Petugas: Apt. Nurul • Estimasi 10 menit", fontSize = 9.sp, color = ClinicalOutline)
                            }
                        }

                        // Obat 2
                        Surface(shape = RoundedCornerShape(8.dp), color = ClinicalSurfaceContainerLow, modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Inhaler Salbutamol 100 mcg", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                                    Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSecondaryContainer) {
                                        Text("Tersedia", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                    }
                                }
                                Text("Aturan Pakai: 2 hisap bila sesak berulang", fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
                                Text("Disimpan di Kotak Obat Asrama • Siap Diambil", fontSize = 9.sp, color = ClinicalOutline)
                            }
                        }
                    }
                }
            }

            // Riwayat Kunjungan Medis Terdahulu (Timeline)
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Riwayat Kunjungan Medis Santri", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)

                        TimelineRecordItem(
                            day = "24",
                            month = "Okt",
                            title = "Keluhan Febris & Sesak Ringan",
                            status = "Dalam Perawatan",
                            statusColor = ClinicalError,
                            statusBg = ClinicalErrorContainer,
                            summary = "Suhu 38.4°C, batuk kering dengan riwayat rhinitis pasca halaqah.",
                            doctor = "dr. Agus Syaifullah • Poli 1",
                            isExpanded = expandedDetailDate == "24okt",
                            onToggle = { expandedDetailDate = if (expandedDetailDate == "24okt") null else "24okt" }
                        )

                        TimelineRecordItem(
                            day = "12",
                            month = "Sep",
                            title = "Alergi Debu / Rinitis Akut",
                            status = "Sembuh Tuntas",
                            statusColor = ClinicalPrimary,
                            statusBg = ClinicalSecondaryContainer,
                            summary = "Bersin berulang disertai mata merah setelah kerja bakti perpustakaan.",
                            doctor = "dr. Siti Khadijah • Terapi Cetirizine",
                            isExpanded = expandedDetailDate == "12sep",
                            onToggle = { expandedDetailDate = if (expandedDetailDate == "12sep") null else "12sep" }
                        )

                        TimelineRecordItem(
                            day = "04",
                            month = "Mei",
                            title = "Screening Tahunan Santri Baru",
                            status = "Sehat",
                            statusColor = ClinicalPrimary,
                            statusBg = ClinicalSecondaryContainer,
                            summary = "Pemeriksaan fisik umum lengkap, rekam TTV normal, bebas kelainan.",
                            doctor = "Tim Medis Poskestren Al-Bahjah",
                            isExpanded = expandedDetailDate == "04mei",
                            onToggle = { expandedDetailDate = if (expandedDetailDate == "04mei") null else "04mei" }
                        )
                    }
                }
            }

            // Komitmen Khidmat Santri Footer Box
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ClinicalSurfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp))) {
                            RemoteImage(
                                url = PoskestrenRepository.IMG_PHARMACIST_FEMALE,
                                contentDescription = "Apoteker",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Column {
                            Text("Komitmen Pelayanan Khidmat Santri", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                            Text("Poskestren menjamin nutrisi pemulihan dan pendampingan santri selama istirahat asrama.", fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
                        }
                    }
                }
            }
        }

        // Action Toast
        if (actionToast != null) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = ClinicalInverseSurface,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(actionToast!!, color = Color.White, fontSize = 12.sp)
                    IconButton(onClick = { actionToast = null }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TimelineRecordItem(
    day: String,
    month: String,
    title: String,
    status: String,
    statusColor: Color,
    statusBg: Color,
    summary: String,
    doctor: String,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = ClinicalSurfaceContainerLow,
        modifier = Modifier.fillMaxWidth().clickable { onToggle() }
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ClinicalSurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(day, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                        Text(month, fontSize = 9.sp, color = ClinicalOutline)
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                    }
                    Text(doctor, fontSize = 10.sp, color = ClinicalPrimary)
                }

                Surface(shape = RoundedCornerShape(4.dp), color = statusBg) {
                    Text(status, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = statusColor, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }

            if (isExpanded) {
                Divider(color = ClinicalOutlineVariant.copy(alpha = 0.3f), modifier = Modifier.padding(vertical = 4.dp))
                Text(summary, fontSize = 11.sp, color = ClinicalOnSurfaceVariant, lineHeight = 15.sp)
            }
        }
    }
}
