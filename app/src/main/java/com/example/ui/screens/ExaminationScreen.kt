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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PoskestrenRepository
import com.example.data.TriageLevel
import com.example.ui.theme.*

@Composable
fun ExaminationScreen(
    patientNis: String = "2023-0891",
    onNavigateToPrescription: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var anamnesis by remember { mutableStateOf("Demam tinggi mendadak sejak tadi malam, sesak nafas ringan, nyeri dada terasa tertekan saat batuk kering.") }
    var bp by remember { mutableStateOf("110/70") }
    var pulse by remember { mutableStateOf("118") }
    var rr by remember { mutableStateOf("26") }

    var tempVal by remember { mutableDoubleStateOf(39.8) }
    var painVal by remember { mutableIntStateOf(9) }
    var comorbidFactor by remember { mutableDoubleStateOf(1.0) } // Asma
    var durationFactor by remember { mutableDoubleStateOf(0.95) } // <24 jam
    var vitalFactor by remember { mutableDoubleStateOf(1.0) } // SpO2 93%

    var calculationResult by remember {
        mutableStateOf(
            PoskestrenRepository.calculateSawScore(
                temp = tempVal,
                painScore = painVal,
                comorbidVal = comorbidFactor,
                durationVal = durationFactor,
                vitalVal = vitalFactor
            )
        )
    }

    var saveToast by remember { mutableStateOf<String?>(null) }

    fun recalculate() {
        calculationResult = PoskestrenRepository.calculateSawScore(
            temp = tempVal,
            painScore = painVal,
            comorbidVal = comorbidFactor,
            durationVal = durationFactor,
            vitalVal = vitalFactor
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ClinicalBackground),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Patient Header Bar
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PEMERIKSAAN & SPK TRIASE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = ClinicalPrimary
                        )

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (calculationResult.level == TriageLevel.DARURAT_MERAH) ClinicalErrorContainer else ClinicalSecondaryContainer
                        ) {
                            Text(
                                text = if (calculationResult.level == TriageLevel.DARURAT_MERAH) "Prioritas Darurat (Kategori Merah)" else "Prioritas Sedang / Terkendali",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (calculationResult.level == TriageLevel.DARURAT_MERAH) ClinicalError else ClinicalPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text(
                        text = "Muhammad Fatih",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ClinicalOnSurface
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSurfaceContainer) {
                            Text("NIS: $patientNis", fontSize = 11.sp, color = ClinicalOnSurfaceVariant, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                        Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSurfaceContainer) {
                            Text("15 Tahun • Asrama Abu Bakar 03", fontSize = 11.sp, color = ClinicalOnSurfaceVariant, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
            }
        }

        // Anamnesis & Keluhan Utama
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.MedicalInformation, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(18.dp))
                            Text("Anamnesis & Keluhan Utama", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                        }
                        Text("Auto-Save Aktif", fontSize = 10.sp, color = ClinicalOutline)
                    }

                    OutlinedTextField(
                        value = anamnesis,
                        onValueChange = { anamnesis = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }
        }

        // Vitals strip
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Tanda Vital Tambahan", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        VitalBox("Tekanan Darah", bp, "mmHg", "Normotensif", ClinicalPrimary, modifier = Modifier.weight(1f))
                        VitalBox("Denyut Nadi", pulse, "bpm", "Takikardia", ClinicalError, modifier = Modifier.weight(1f))
                        VitalBox("Laju Napas (RR)", rr, "x/mnt", "Takipnea", ClinicalError, modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // SAW Parameters
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Parameter Bobot Algoritma SAW", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                        Surface(shape = RoundedCornerShape(10.dp), color = ClinicalSurfaceContainerLow) {
                            Text("5 Kriteria SPK", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    // C1: Suhu Tubuh
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("C1: Suhu Tubuh (°C) • Bobot 30%", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = ClinicalOnSurface)
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (tempVal >= 39.0) ClinicalErrorContainer else ClinicalSurfaceContainerLow
                            ) {
                                Text(
                                    text = if (tempVal >= 39.0) "Febris Tinggi (>39°C)" else if (tempVal >= 37.8) "Febris Sedang" else "Normal",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (tempVal >= 39.0) ClinicalError else ClinicalPrimary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Slider(
                                value = tempVal.toFloat(),
                                onValueChange = {
                                    tempVal = String.format("%.1f", it).toDouble()
                                    recalculate()
                                },
                                valueRange = 36.0f..41.0f,
                                modifier = Modifier.weight(1f),
                                colors = SliderDefaults.colors(thumbColor = ClinicalPrimary, activeTrackColor = ClinicalPrimary)
                            )
                            Text(
                                text = "$tempVal °C",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (tempVal >= 39.0) ClinicalError else ClinicalPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    // C2: Skala Nyeri NRS
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("C2: Skala Nyeri (NRS 0–10) • Bobot 25%", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = ClinicalOnSurface)
                            Surface(shape = RoundedCornerShape(4.dp), color = if (painVal >= 8) ClinicalErrorContainer else ClinicalTertiaryFixed) {
                                Text(
                                    text = "Skala $painVal - ${if (painVal >= 8) "Sangat Hebat" else if (painVal >= 4) "Sedang" else "Ringan"}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (painVal >= 8) ClinicalError else ClinicalOnTertiaryFixed,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        // 0-10 Button selector
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            for (p in 0..10) {
                                val isSelected = painVal == p
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (isSelected) (if (p >= 8) ClinicalError else ClinicalPrimary) else ClinicalSurfaceContainerLow,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            painVal = p
                                            recalculate()
                                        }
                                ) {
                                    Box(modifier = Modifier.padding(vertical = 6.dp), contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "$p",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else ClinicalOnSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // C3: Riwayat Komorbid
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("C3: Riwayat Komorbid / Bawaan • Bobot 15%", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = ClinicalOnSurface)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            ComorbidOptionChip("Asma Aktif", 1.0, comorbidFactor) { comorbidFactor = it; recalculate() }
                            ComorbidOptionChip("Jantung/DM", 0.75, comorbidFactor) { comorbidFactor = it; recalculate() }
                            ComorbidOptionChip("Alergi", 0.5, comorbidFactor) { comorbidFactor = it; recalculate() }
                            ComorbidOptionChip("Nihil", 0.2, comorbidFactor) { comorbidFactor = it; recalculate() }
                        }
                    }

                    // C4: Durasi Onset Sakit
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("C4: Durasi & Kecepatan Onset • Bobot 15%", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = ClinicalOnSurface)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            ComorbidOptionChip("<24 Jam", 0.95, durationFactor) { durationFactor = it; recalculate() }
                            ComorbidOptionChip("1-2 Hari", 0.75, durationFactor) { durationFactor = it; recalculate() }
                            ComorbidOptionChip("3-5 Hari", 0.50, durationFactor) { durationFactor = it; recalculate() }
                            ComorbidOptionChip(">5 Hari", 0.25, durationFactor) { durationFactor = it; recalculate() }
                        }
                    }

                    // C5: Kesadaran & Vital SpO2
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("C5: Kesadaran & Hipoksia SpO2 • Bobot 15%", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = ClinicalOnSurface)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            ComorbidOptionChip("SpO2 93%", 1.0, vitalFactor) { vitalFactor = it; recalculate() }
                            ComorbidOptionChip("Apatis", 0.8, vitalFactor) { vitalFactor = it; recalculate() }
                            ComorbidOptionChip("SpO2 96%", 0.5, vitalFactor) { vitalFactor = it; recalculate() }
                            ComorbidOptionChip("SpO2 99%", 0.2, vitalFactor) { vitalFactor = it; recalculate() }
                        }
                    }

                    Button(
                        onClick = { recalculate() },
                        colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Kalkulasi Matriks SAW Otomatis", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // SAW Calculation Transparency Output Card
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("INDEKS PREFERENSI (Vi)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalOutline)
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = if (calculationResult.level == TriageLevel.DARURAT_MERAH) ClinicalErrorContainer else ClinicalSecondaryContainer
                        ) {
                            Text(
                                text = "Kritis: ${(calculationResult.totalVi * 100).toInt()}%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (calculationResult.level == TriageLevel.DARURAT_MERAH) ClinicalError else ClinicalPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = String.format("%.3f", calculationResult.totalVi),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (calculationResult.level == TriageLevel.DARURAT_MERAH) ClinicalError else ClinicalPrimary,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = if (calculationResult.level == TriageLevel.DARURAT_MERAH) "PRIORITAS 1: DARURAT" else if (calculationResult.level == TriageLevel.SEDANG_KUNING) "PRIORITAS 2: SEDANG" else "PRIORITAS 3: RINGAN",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (calculationResult.level == TriageLevel.DARURAT_MERAH) ClinicalError else ClinicalPrimary
                        )
                    }

                    // Recommendation Box
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (calculationResult.level == TriageLevel.DARURAT_MERAH) ClinicalErrorContainer.copy(alpha = 0.5f) else ClinicalSurfaceContainerLow,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = ClinicalError, modifier = Modifier.size(16.dp))
                            Text(
                                text = if (calculationResult.level == TriageLevel.DARURAT_MERAH)
                                    "Rekomendasi Tindakan Segera: Tangani di Ruang Tindakan IGD Poskestren. Berikan kompres hangat, nebulasi bronkodilator, suplementasi O2 kanul 2-3 lpm, dan observasi SpO2 ketat."
                                else
                                    "Rekomendasi Tindakan: Penanganan di Poli Rawat Jalan, berikan terapi pereda gejala, resep farmasi, dan istirahat kamar asrama.",
                                fontSize = 11.sp,
                                color = ClinicalOnSurface,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    // Matrix Table
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = ClinicalSurfaceContainerLow,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Matriks Transparansi: Vi = ∑(wj * rij)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                            MatrixRow("C1: Suhu Tubuh", "30%", String.format("%.3f", calculationResult.rTemp), String.format("%.3f", calculationResult.c1Weighted))
                            MatrixRow("C2: Skala Nyeri", "25%", String.format("%.3f", calculationResult.rPain), String.format("%.3f", calculationResult.c2Weighted))
                            MatrixRow("C3: Komorbiditas", "15%", String.format("%.3f", calculationResult.rComorbid), String.format("%.3f", calculationResult.c3Weighted))
                            MatrixRow("C4: Durasi Onset", "15%", String.format("%.3f", calculationResult.rDuration), String.format("%.3f", calculationResult.c4Weighted))
                            MatrixRow("C5: Kesadaran & SpO2", "15%", String.format("%.3f", calculationResult.rVital), String.format("%.3f", calculationResult.c5Weighted))
                        }
                    }
                }
            }
        }

        // Action Buttons: Resep Farmasi, Rawat Inap, Simpan
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onNavigateToPrescription,
                    colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(44.dp)
                ) {
                    Icon(Icons.Default.Medication, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Buat Resep Farmasi", fontWeight = FontWeight.Bold)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { saveToast = "Surat Rekomendasi Rawat Inap Asrama / Rujukan disiapkan." },
                        colors = ButtonDefaults.buttonColors(containerColor = ClinicalError),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).height(44.dp)
                    ) {
                        Icon(Icons.Default.AirlineSeatFlat, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Rawat / Rujuk", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { saveToast = "Rekam Medis Santri Muhammad Fatih tersimpan ke EMR." },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).height(44.dp)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Simpan EMR", fontSize = 12.sp, color = ClinicalPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        if (saveToast != null) {
            item {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = ClinicalInverseSurface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = saveToast!!, color = Color.White, fontSize = 12.sp)
                        IconButton(onClick = { saveToast = null }, modifier = Modifier.size(20.dp)) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VitalBox(label: String, valText: String, unit: String, status: String, statusColor: Color, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = ClinicalSurfaceContainerLow,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(label, fontSize = 9.sp, color = ClinicalOutline, maxLines = 1)
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(valText, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                Text(unit, fontSize = 8.sp, color = ClinicalOutline)
            }
            Text(status, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = statusColor)
        }
    }
}

@Composable
private fun ComorbidOptionChip(label: String, value: Double, selectedValue: Double, onSelect: (Double) -> Unit) {
    val isSelected = selectedValue == value
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = if (isSelected) ClinicalPrimary else ClinicalSurfaceContainerLow,
        modifier = Modifier.clickable { onSelect(value) }
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else ClinicalOnSurfaceVariant,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun MatrixRow(kriteria: String, bobot: String, norm: String, hasil: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(kriteria, fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
        Text("$bobot x $norm = $hasil", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, fontFamily = FontFamily.Monospace)
    }
}
