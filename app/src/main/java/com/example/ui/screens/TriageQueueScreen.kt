package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.PoskestrenRepository
import com.example.data.TriageLevel
import com.example.data.TriagePatient
import com.example.ui.components.TriageLevelBadge
import com.example.ui.theme.*

@Composable
fun TriageQueueScreen(
    onNavigateToExamination: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("all") }
    var selectedPatientForDetail by remember { mutableStateOf<TriagePatient?>(null) }
    var showWeightDialog by remember { mutableStateOf(false) }
    var callToastMessage by remember { mutableStateOf<String?>(null) }

    val patients = remember { PoskestrenRepository.initialTriagePatients }

    val filteredPatients = remember(selectedFilter, patients) {
        when (selectedFilter) {
            "merah" -> patients.filter { it.triageLevel == TriageLevel.DARURAT_MERAH }
            "kuning" -> patients.filter { it.triageLevel == TriageLevel.SEDANG_KUNING }
            "hijau" -> patients.filter { it.triageLevel == TriageLevel.RINGAN_HIJAU }
            else -> patients
        }
    }

    Box(modifier = modifier.fillMaxSize().background(ClinicalBackground)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Protocol Clinical Disclaimer Banner
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ClinicalSurfaceContainerHigh,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.MedicalInformation, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(16.dp))
                                Text("PROTOKOL KLINIS SAW AKTIF", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                                Surface(shape = RoundedCornerShape(4.dp), color = ClinicalPrimary) {
                                    Text("v2.4 Live", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                }
                            }
                            Button(
                                onClick = { showWeightDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = ClinicalSecondary),
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Icon(Icons.Default.Tune, contentDescription = null, modifier = Modifier.size(12.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Bobot Kriteria", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Text(
                            text = "Skor SAW adalah instrumen pendukung keputusan prioritas (decision support) dan tidak menggantikan diskresi klinis dokter pada kegawatdaruratan mutlak.",
                            fontSize = 11.sp,
                            color = ClinicalOnSurfaceVariant,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            // Header Title & Category Pills
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("TRIASE CERDAS POSKESTREN AL-BAHJAH", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                    Text("Papan Antrean Triase SAW Terpadu", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                    Text(
                        text = "Urutan penanganan santri otomatis dihitung berdasarkan metode Simple Additive Weighting (SAW) benefit criteria.",
                        fontSize = 12.sp,
                        color = ClinicalOnSurfaceVariant
                    )

                    // Category Filters
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterPill(
                            label = "Semua (18)",
                            isSelected = selectedFilter == "all",
                            onClick = { selectedFilter = "all" },
                            modifier = Modifier.weight(1f)
                        )
                        FilterPill(
                            label = "Darurat (3)",
                            isSelected = selectedFilter == "merah",
                            activeColor = ClinicalError,
                            onClick = { selectedFilter = "merah" },
                            modifier = Modifier.weight(1f)
                        )
                        FilterPill(
                            label = "Sedang (7)",
                            isSelected = selectedFilter == "kuning",
                            activeColor = ClinicalTertiary,
                            onClick = { selectedFilter = "kuning" },
                            modifier = Modifier.weight(1f)
                        )
                        FilterPill(
                            label = "Ringan (8)",
                            isSelected = selectedFilter == "hijau",
                            activeColor = ClinicalPrimary,
                            onClick = { selectedFilter = "hijau" },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Real-time Clinical Metrics Ticker
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TickerBox(
                        title = "Prioritas V₁",
                        value = "0.965",
                        sub = "< 5m Tindakan",
                        color = ClinicalError,
                        modifier = Modifier.weight(1f)
                    )
                    TickerBox(
                        title = "Menunggu",
                        value = "5 Santri",
                        sub = "2 Dokter Jaga",
                        color = ClinicalPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    TickerBox(
                        title = "Rata-rata Respon",
                        value = "4.2 Mnt",
                        sub = "Tepat SPM",
                        color = ClinicalOnSurface,
                        modifier = Modifier.weight(1f)
                    )
                    TickerBox(
                        title = "Suhu > 38.5°C",
                        value = "2 Kasus",
                        sub = "Isolasi Siap",
                        color = ClinicalTertiary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Patient List Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Matriks Keputusan & Antrean (${filteredPatients.size} Santri)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ClinicalOnSurface
                    )
                    Text(
                        text = "Sinkron 14:28 WIB",
                        fontSize = 11.sp,
                        color = ClinicalOutline
                    )
                }
            }

            // Patients list items
            items(filteredPatients) { p ->
                TriagePatientRowCard(
                    patient = p,
                    onCallClick = {
                        callToastMessage = "Memanggil ${p.nama} ke Poli 1 via Pengeras Suara Poskestren..."
                    },
                    onDetailClick = {
                        selectedPatientForDetail = p
                    },
                    onExamineClick = {
                        onNavigateToExamination(p.nis)
                    }
                )
            }
        }

        // Call Toast Message
        if (callToastMessage != null) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = ClinicalInverseSurface,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Campaign, contentDescription = null, tint = ClinicalPrimaryFixed)
                        Text(text = callToastMessage!!, fontSize = 12.sp, color = Color.White)
                    }
                    IconButton(onClick = { callToastMessage = null }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }

        // Detail SAW Calculation Dialog
        if (selectedPatientForDetail != null) {
            val patient = selectedPatientForDetail!!
            Dialog(onDismissRequest = { selectedPatientForDetail = null }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Rincian Kalkulasi Matriks SAW", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                                Text(patient.nama + " (" + patient.kamar + ")", fontSize = 12.sp, color = ClinicalPrimary, fontWeight = FontWeight.SemiBold)
                            }
                            IconButton(onClick = { selectedPatientForDetail = null }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }

                        // Formula notice
                        Text(
                            text = "Normalisasi benefit: r_ij = x_ij / Max(x_j). Skor Preferensi Vi = ∑ (w_j * r_ij)",
                            fontSize = 11.sp,
                            color = ClinicalOutline
                        )

                        // Table rows
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = ClinicalSurfaceContainerLow,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                SawCalcRow("C1 Suhu (${patient.suhu}°C)", "w=0.30", "r=${String.format("%.3f", patient.suhu / 40.0)}", String.format("%.3f", patient.c1Val))
                                SawCalcRow("C2 Nyeri (${patient.skalaNyeri}/10)", "w=0.25", "r=${String.format("%.2f", patient.skalaNyeri / 10.0)}", String.format("%.3f", patient.c2Val))
                                SawCalcRow("C3 Komorbid (${patient.komorbid})", "w=0.15", "r=1.00", String.format("%.3f", patient.c3Val))
                                SawCalcRow("C4 Durasi Sakit", "w=0.15", "r=0.95", String.format("%.3f", patient.c4Val))
                                SawCalcRow("C5 Kesadaran & SpO2 (${patient.spo2}%)", "w=0.15", "r=1.00", String.format("%.3f", patient.c5Val))
                            }
                        }

                        // Total Score Highlight
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (patient.triageLevel == TriageLevel.DARURAT_MERAH) ClinicalErrorContainer else ClinicalSecondaryContainer,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("SKOR PREFERENSI AKHIR (Vi)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                                    Text(patient.statusText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = ClinicalOnSurface)
                                }
                                Text(
                                    text = String.format("%.3f", patient.viScore),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (patient.triageLevel == TriageLevel.DARURAT_MERAH) ClinicalError else ClinicalPrimary
                                )
                            }
                        }

                        Button(
                            onClick = { selectedPatientForDetail = null },
                            colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Tutup", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Bobot Kriteria Dialog
        if (showWeightDialog) {
            Dialog(onDismissRequest = { showWeightDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Bobot SAW Aktif (100%)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                            IconButton(onClick = { showWeightDialog = false }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }

                        WeightRowItem("C1: Suhu Tubuh (°C)", "30% (W=0.30)", 0.30f, ClinicalPrimary)
                        WeightRowItem("C2: Skala Nyeri NRS (1-10)", "25% (W=0.25)", 0.25f, ClinicalPrimaryContainer)
                        WeightRowItem("C3: Riwayat Komorbiditas", "15% (W=0.15)", 0.15f, ClinicalSecondary)
                        WeightRowItem("C4: Durasi Onset Sakit", "15% (W=0.15)", 0.15f, ClinicalSecondaryFixedDim)
                        WeightRowItem("C5: Status Kesadaran & Vital", "15% (W=0.15)", 0.15f, ClinicalSecondaryFixedDim)

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = ClinicalSurfaceContainerLow,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Kebijakan Triase Pesantren: Perubahan bobot hanya dapat disahkan oleh dr. Agus Syaifullah (Kepala Poskestren) dan Tim Medis Al-Bahjah.",
                                fontSize = 10.sp,
                                color = ClinicalOnSurfaceVariant,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Button(
                            onClick = { showWeightDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Selesai", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterPill(
    label: String,
    isSelected: Boolean,
    activeColor: Color = ClinicalPrimary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) activeColor else Color.White,
        shadowElevation = if (isSelected) 2.dp else 1.dp,
        modifier = modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else ClinicalOnSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun TickerBox(
    title: String,
    value: String,
    sub: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalOutline)
            Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
            Text(sub, fontSize = 8.sp, color = ClinicalOnSurfaceVariant, maxLines = 1)
        }
    }
}

@Composable
private fun TriagePatientRowCard(
    patient: TriagePatient,
    onCallClick: () -> Unit,
    onDetailClick: () -> Unit,
    onExamineClick: () -> Unit
) {
    val isEmergency = patient.triageLevel == TriageLevel.DARURAT_MERAH

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isEmergency) ClinicalErrorContainer.copy(alpha = 0.25f) else Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(if (isEmergency) ClinicalError else ClinicalPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "#${patient.rank}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Column {
                        Text(
                            text = patient.nama,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ClinicalOnSurface
                        )
                        Text(
                            text = "${patient.nis} • ${patient.kamar}",
                            fontSize = 10.sp,
                            color = ClinicalOutline
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Vi: ${String.format("%.3f", patient.viScore)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isEmergency) ClinicalError else ClinicalPrimary,
                        fontFamily = FontFamily.Monospace
                    )
                    TriageLevelBadge(level = patient.triageLevel)
                }
            }

            // Clinical parameters row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ClinicalSurfaceContainerLow, RoundedCornerShape(6.dp))
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Suhu: ${patient.suhu}°C", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (patient.suhu > 38.5) ClinicalError else ClinicalOnSurface)
                Text("Nyeri: ${patient.skalaNyeri}/10", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (patient.skalaNyeri >= 7) ClinicalError else ClinicalOnSurface)
                Text(patient.komorbid, fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
                Text("SpO2: ${patient.spo2}%", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (patient.spo2 < 95) ClinicalError else ClinicalPrimary)
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = onCallClick,
                    colors = ButtonDefaults.buttonColors(containerColor = if (isEmergency) ClinicalError else ClinicalPrimary),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Panggil ke Poli", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onDetailClick,
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(14.dp), tint = ClinicalPrimary)
                    Spacer(Modifier.width(4.dp))
                    Text("Hitung SAW", fontSize = 11.sp, color = ClinicalPrimary, fontWeight = FontWeight.Bold)
                }

                IconButton(
                    onClick = onExamineClick,
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(Icons.Default.MedicalInformation, contentDescription = "Pemeriksaan", tint = ClinicalPrimary)
                }
            }
        }
    }
}

@Composable
private fun SawCalcRow(label: String, weight: String, norm: String, weighted: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = ClinicalOnSurface)
            Text("$weight • $norm", fontSize = 10.sp, color = ClinicalOutline)
        }
        Text(weighted, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, fontFamily = FontFamily.Monospace)
    }
}

@Composable
private fun WeightRowItem(name: String, weight: String, ratio: Float, color: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(name, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = ClinicalOnSurface)
            Text(weight, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
        }
        LinearProgressIndicator(
            progress = { ratio },
            color = color,
            trackColor = ClinicalSurfaceContainerLow,
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
        )
    }
}
