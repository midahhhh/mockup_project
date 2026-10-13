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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PoskestrenRepository
import com.example.ui.components.MetricKpiCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@Composable
fun DashboardScreen(
    onNavigateToTriage: () -> Unit,
    onNavigateToExamination: () -> Unit,
    onNavigateToSantri: () -> Unit,
    onNavigateToPharmacy: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTrendFilter by remember { mutableStateOf("7 Hari") }
    var notificationMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ClinicalBackground),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TOP GREETING BANNER
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.Transparent,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(ClinicalPrimary, ClinicalPrimaryContainer, ClinicalSecondary)
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = ClinicalPrimaryFixed, modifier = Modifier.size(14.dp))
                            Text(
                                text = "SIMKES POSKESTREN AL-BAHJAH",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ClinicalPrimaryFixed
                            )
                        }

                        Text(
                            text = "Ahlan wa Sahlan, dr. Agus Syaifullah!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Jadwal Jaga: Shift Pagi (08.00 - 14.00 WIB) • Poskestren Siaga & Tanggap • Kamar Observasi A & B Siap",
                            fontSize = 11.sp,
                            color = ClinicalOnPrimaryContainer,
                            lineHeight = 16.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onNavigateToSantri,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.PersonAdd, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("+ Pasien Baru", color = ClinicalPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = onNavigateToExamination,
                                colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimaryFixed),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.AssignmentLate, contentDescription = null, tint = ClinicalOnPrimaryFixed, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Mulai Triase", color = ClinicalOnPrimaryFixed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // 4 KPI STAT METRIC CARDS
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MetricKpiCard(
                        title = "Prioritas Pelayanan",
                        value = "18",
                        unit = "Santri",
                        subtitle = "3 Darurat • 7 Sedang • 8 Ringan",
                        icon = Icons.Default.PriorityHigh,
                        iconBgColor = ClinicalErrorContainer,
                        iconTint = ClinicalError,
                        badgeText = "+12% vs kmrn",
                        badgeBgColor = ClinicalErrorContainer,
                        badgeTextColor = ClinicalError,
                        modifier = Modifier.weight(1f)
                    )

                    MetricKpiCard(
                        title = "Total Kunjungan",
                        value = "42",
                        unit = "Santri",
                        subtitle = "Rata-rata tunggu: 8.5 menit",
                        icon = Icons.Default.MedicalInformation,
                        iconBgColor = ClinicalSecondaryContainer,
                        iconTint = ClinicalPrimary,
                        badgeText = "Optimal",
                        badgeBgColor = ClinicalSurfaceContainerLow,
                        badgeTextColor = ClinicalPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MetricKpiCard(
                        title = "Kapasitas Asrama Sakit",
                        value = "5/8",
                        unit = "Bed (62.5%)",
                        subtitle = "3 Bed kosong (Putra 2, Putri 1)",
                        icon = Icons.Default.Hotel,
                        iconBgColor = ClinicalSecondaryContainer,
                        iconTint = ClinicalSecondary,
                        modifier = Modifier.weight(1f)
                    )

                    MetricKpiCard(
                        title = "Manajemen Obat",
                        value = "4",
                        unit = "Menipis",
                        subtitle = "2 Batch kedaluwarsa <30 hari",
                        icon = Icons.Default.Warning,
                        iconBgColor = ClinicalTertiaryFixed,
                        iconTint = ClinicalTertiary,
                        badgeText = "Restock",
                        badgeBgColor = ClinicalTertiaryFixed,
                        badgeTextColor = ClinicalOnTertiaryFixed,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // TREN GEJALA & KUNJUNGAN (Chart section)
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("EPIDEMIOLOGI & MORBIDITAS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                            Text("Tren Gejala & Kunjungan", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(ClinicalSurfaceContainerLow)
                                .padding(2.dp)
                        ) {
                            listOf("7 Hari", "30 Hari", "Bulan Ini").forEach { filter ->
                                val selected = selectedTrendFilter == filter
                                Text(
                                    text = filter,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selected) Color.White else ClinicalOnSurfaceVariant,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (selected) ClinicalPrimary else Color.Transparent)
                                        .clickable { selectedTrendFilter = filter }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    // Legend
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LegendItem("Demam / ISPA", ClinicalPrimary)
                        LegendItem("Gastritis", ClinicalTertiaryContainer)
                        LegendItem("Cedera", ClinicalSecondary)
                        LegendItem("Dermatitis", ClinicalPrimaryFixedDim)
                    }

                    // 7-day Bar Visualizer
                    WeeklyBarGraph()

                    // Insight Box
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = ClinicalSurfaceContainerLow
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Insights, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Insight Klinis: Lonjakan kasus Demam & ISPA tercatat pada 3 hari terakhir terkonsentrasi di Asrama Putra Blok C. Disarankan pengecekan ventilasi kamar dan multivitamin.",
                                fontSize = 11.sp,
                                color = ClinicalOnSurface,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // PAPAN ANTREAN TRIASE CEPAT (Metode SAW)
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
                        Column {
                            Text("METODE SAW (WEIGHTED SUM)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalError)
                            Text("Papan Antrean Triase Cepat", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                        }
                        Surface(shape = RoundedCornerShape(12.dp), color = ClinicalSecondaryContainer) {
                            Text("Realtime", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                        }
                    }

                    Text(
                        text = "Prioritas dihitung otomatis berdasarkan kriteria tanda vital, nyeri & durasi sakit.",
                        fontSize = 11.sp,
                        color = ClinicalOnSurfaceVariant
                    )

                    // Patient Quick Cards
                    TriageQuickCard(
                        name = "M. Fatih",
                        nis = "NIS 20240182",
                        asrama = "Asrama Umar Bin Khattab - Kelas 10A",
                        viScore = "Vi: 0.942",
                        vitals = "Suhu 39.4°C • Nyeri 8/10 • Durasi 1 Hari",
                        category = "Darurat (Triase 1)",
                        isEmergency = true
                    )

                    TriageQuickCard(
                        name = "Zaki Ahmad",
                        nis = "NIS 20230091",
                        asrama = "Asrama Ali Bin Abi Thalib - Kelas 11B",
                        viScore = "Vi: 0.815",
                        vitals = "Asma Akut / Sesak • RR 28x/m • SpO2 93%",
                        category = "Darurat",
                        isEmergency = true
                    )

                    TriageQuickCard(
                        name = "Rayhan Pratama",
                        nis = "NIS 20240312",
                        asrama = "Asrama Utsman - Kelas 10C",
                        viScore = "Vi: 0.680",
                        vitals = "Gastritis / Mual Akut • Nyeri 5/10 • Suhu 37.2°C",
                        category = "Sedang",
                        isEmergency = false
                    )

                    TriageQuickCard(
                        name = "Ahmad Fauzi",
                        nis = "NIS 20230211",
                        asrama = "Asrama Abu Bakar - Kelas 11A",
                        viScore = "Vi: 0.490",
                        vitals = "Flu Ringan / Batuk • Suhu 36.8°C • Nyeri 2/10",
                        category = "Ringan",
                        isEmergency = false
                    )

                    Button(
                        onClick = onNavigateToTriage,
                        colors = ButtonDefaults.buttonColors(containerColor = ClinicalSurfaceContainerHigh),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Buka Papan Antrean Lengkap (18 Santri)", color = ClinicalOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(6.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = ClinicalOnSurface, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        // PERINGATAN LOGISTIK FARMASI
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
                        Column {
                            Text("STOK OPNAME & MASA SIMPAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalError)
                            Text("Peringatan Logistik Farmasi", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                        }
                        Text(
                            text = "Buka Gudang",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ClinicalPrimary,
                            modifier = Modifier.clickable { onNavigateToPharmacy() }
                        )
                    }

                    // Medicine Alert 1
                    PharmacyAlertItem(
                        name = "Paracetamol Drop 100mg/mL",
                        desc = "Antipiretik Anak • Rak A-02",
                        stock = "Tersisa 4 Botol",
                        min = "Min: 20",
                        badge = "Stok Kritis",
                        badgeColor = ClinicalErrorContainer,
                        badgeTextColor = ClinicalError,
                        actionText = "Reorder",
                        onAction = { notificationMessage = "Surat Pesanan Reorder Paracetamol dibuat." }
                    )

                    // Medicine Alert 2
                    PharmacyAlertItem(
                        name = "Amoxicillin 500mg Kaplet",
                        desc = "Batch: #AMX-9021 • Exp: 15 Des 2024",
                        stock = "38 Strip",
                        min = "Prioritaskan",
                        badge = "Kedaluwarsa Dekat",
                        badgeColor = ClinicalTertiaryFixed,
                        badgeTextColor = ClinicalOnTertiaryFixed,
                        actionText = "Retur",
                        onAction = { notificationMessage = "Permintaan retur batch Amoxicillin diajukan." }
                    )

                    // Medicine Alert 3
                    PharmacyAlertItem(
                        name = "Ventolin Inhaler 100mcg",
                        desc = "Darurat Asma Asrama • Rak Emergency",
                        stock = "2 Canister",
                        min = "Min: 6",
                        badge = "Menipis",
                        badgeColor = ClinicalSurfaceContainerHigh,
                        badgeTextColor = ClinicalPrimary,
                        actionText = "Minta",
                        onAction = { notificationMessage = "Permintaan restock Ventolin Inhaler terkirim." }
                    )
                }
            }
        }

        // AKSI PETUGAS SIAGA & AMBULANS CALLOUT
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("OPERASIONAL CEPAT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                    Text("Aksi Petugas Siaga", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)

                    ActionTile(
                        icon = Icons.Default.PersonAdd,
                        title = "+ Registrasi Pasien Baru",
                        desc = "Cari santri by NIS / scan barcode",
                        onClick = onNavigateToSantri
                    )

                    ActionTile(
                        icon = Icons.Default.MedicalServices,
                        title = "Mulai Pemeriksaan Triase",
                        desc = "Input tanda vital & hitung bobot SAW",
                        onClick = onNavigateToExamination
                    )

                    ActionTile(
                        icon = Icons.Default.Tv,
                        title = "Buka Layar Papan Antrean",
                        desc = "Tampilan display ruang tunggu santri",
                        onClick = onNavigateToTriage
                    )

                    // Ambulance callout
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = ClinicalPrimaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Default.Emergency, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                                Column {
                                    Text("Ambulans Siaga Al-Bahjah", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    Text("0812-9988-7711", fontSize = 11.sp, color = ClinicalPrimaryFixed)
                                }
                            }
                            Button(
                                onClick = { notificationMessage = "Menghubungi Driver Ambulans Poskestren..." },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("Panggil", color = ClinicalPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Notification Snackbar/Toast
        if (notificationMessage != null) {
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
                        Text(text = notificationMessage!!, color = Color.White, fontSize = 12.sp)
                        IconButton(onClick = { notificationMessage = null }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LegendItem(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
        Text(text = label, fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
    }
}

@Composable
private fun WeeklyBarGraph() {
    val days = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Ahad")
    val heights = listOf(
        listOf(45.dp, 35.dp, 20.dp, 25.dp),
        listOf(50.dp, 40.dp, 15.dp, 22.dp),
        listOf(42.dp, 32.dp, 24.dp, 30.dp),
        listOf(55.dp, 28.dp, 12.dp, 36.dp),
        listOf(58.dp, 42.dp, 30.dp, 38.dp),
        listOf(60.dp, 46.dp, 35.dp, 42.dp),
        listOf(65.dp, 48.dp, 38.dp, 44.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        days.forEachIndexed { i, day ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(modifier = Modifier.width(5.dp).height(heights[i][0]).clip(RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)).background(ClinicalPrimary))
                    Box(modifier = Modifier.width(5.dp).height(heights[i][1]).clip(RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)).background(ClinicalTertiaryContainer))
                    Box(modifier = Modifier.width(5.dp).height(heights[i][2]).clip(RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)).background(ClinicalSecondary))
                    Box(modifier = Modifier.width(5.dp).height(heights[i][3]).clip(RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)).background(ClinicalPrimaryFixedDim))
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = day,
                    fontSize = 9.sp,
                    color = if (i == 6) ClinicalPrimary else ClinicalOutline,
                    fontWeight = if (i == 6) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun TriageQuickCard(
    name: String,
    nis: String,
    asrama: String,
    viScore: String,
    vitals: String,
    category: String,
    isEmergency: Boolean
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = ClinicalSurfaceBright,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                    Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSurfaceContainer) {
                        Text(text = nis, fontSize = 9.sp, color = ClinicalOnSurfaceVariant, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                    }
                }
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (isEmergency) ClinicalErrorContainer else ClinicalTertiaryFixed
                ) {
                    Text(
                        text = category,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isEmergency) ClinicalError else ClinicalOnTertiaryFixed,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(text = asrama, fontSize = 11.sp, color = ClinicalOnSurfaceVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = vitals, fontSize = 10.sp, color = ClinicalOutline)
                Text(text = viScore, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (isEmergency) ClinicalError else ClinicalTertiary)
            }
        }
    }
}

@Composable
private fun PharmacyAlertItem(
    name: String,
    desc: String,
    stock: String,
    min: String,
    badge: String,
    badgeColor: Color,
    badgeTextColor: Color,
    actionText: String,
    onAction: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = ClinicalSurfaceContainerLow,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                    Surface(shape = RoundedCornerShape(4.dp), color = badgeColor) {
                        Text(text = badge, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = badgeTextColor, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                    }
                }
                Text(text = "$desc • $min", fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(text = stock, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = badgeTextColor)
                Text(
                    text = "+ $actionText",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ClinicalPrimary,
                    modifier = Modifier.clickable { onAction() }
                )
            }
        }
    }
}

@Composable
private fun ActionTile(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = ClinicalSurfaceContainerLow,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ClinicalPrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
            }
            Column {
                Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                Text(text = desc, fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
            }
        }
    }
}
