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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.PoskestrenRepository
import com.example.data.Prescription
import com.example.data.PrescriptionStatus
import com.example.ui.theme.*

@Composable
fun PharmacyDispensingScreen(
    modifier: Modifier = Modifier
) {
    var prescriptions by remember { mutableStateOf(PoskestrenRepository.initialPrescriptions) }
    var selectedPrescriptionForReview by remember { mutableStateOf<Prescription?>(null) }
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var activeKanbanTab by remember { mutableIntStateOf(0) } // 0: Menunggu, 1: Siap Ambil, 2: Selesai

    val waitingList = remember(prescriptions) { prescriptions.filter { it.status == PrescriptionStatus.MENUNGGU_DIPROSES } }
    val readyList = remember(prescriptions) { prescriptions.filter { it.status == PrescriptionStatus.SIAP_DIAMBIL } }
    val doneList = remember(prescriptions) { prescriptions.filter { it.status == PrescriptionStatus.SELESAI_DISERAHKAN } }

    Box(modifier = modifier.fillMaxSize().background(ClinicalBackground)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
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
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.Medication, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(16.dp))
                                Text("FARMASI & DISPENSING KLINIS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                            }
                            Surface(shape = RoundedCornerShape(12.dp), color = ClinicalSecondaryContainer) {
                                Text("22 Resep Hari Ini", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }

                        Text("Dashboard Farmasi & Dispensing Resep", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                        Text("Monitoring antrean resep masuk dari poli pemeriksaan, telaah klinis, peringatan interaksi obat, dan serah terima obat santri Al-Bahjah.", fontSize = 11.sp, color = ClinicalOnSurfaceVariant)

                        Button(
                            onClick = {
                                toastMessage = "Telaah Otomatis SIMKES: 5 antrean resep diperiksa. Semua kombinasi obat sinergis dan aman."
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Rule, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Telaah Otomatis Seluruh Resep", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Kanban Tab Switcher
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TabPill(
                        label = "Menunggu (${waitingList.size})",
                        isSelected = activeKanbanTab == 0,
                        badgeColor = ClinicalTertiaryFixed,
                        onClick = { activeKanbanTab = 0 },
                        modifier = Modifier.weight(1f)
                    )
                    TabPill(
                        label = "Siap Ambil (${readyList.size})",
                        isSelected = activeKanbanTab == 1,
                        badgeColor = ClinicalSecondaryContainer,
                        onClick = { activeKanbanTab = 1 },
                        modifier = Modifier.weight(1f)
                    )
                    TabPill(
                        label = "Selesai (${doneList.size})",
                        isSelected = activeKanbanTab == 2,
                        badgeColor = ClinicalPrimaryFixed,
                        onClick = { activeKanbanTab = 2 },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Tab Content
            when (activeKanbanTab) {
                0 -> {
                    items(waitingList) { rx ->
                        PrescriptionWaitingCard(
                            rx = rx,
                            onStartPrepare = {
                                prescriptions = prescriptions.map {
                                    if (it.id == rx.id) it.copy(status = PrescriptionStatus.SIAP_DIAMBIL) else it
                                }
                                toastMessage = "Resep ${rx.noResep} selesai diracik dan dipindahkan ke 'Siap Diambil'."
                            },
                            onReview = { selectedPrescriptionForReview = rx }
                        )
                    }
                }
                1 -> {
                    items(readyList) { rx ->
                        PrescriptionReadyCard(
                            rx = rx,
                            onHandover = {
                                prescriptions = prescriptions.map {
                                    if (it.id == rx.id) it.copy(status = PrescriptionStatus.SELESAI_DISERAHKAN, waktuPenyerahan = "Baru saja") else it
                                }
                                toastMessage = "Penyerahan obat ${rx.namaSantri} terkonfirmasi. Stok otomatis berkurang."
                            },
                            onPrint = {
                                toastMessage = "Etiket aturan pakai ${rx.namaSantri} dicetak."
                            }
                        )
                    }
                }
                else -> {
                    items(doneList) { rx ->
                        PrescriptionDoneCard(rx = rx)
                    }
                }
            }

            // Bottom Alert: Critical Stock & Clinical Screening
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
                            Text("Peringatan Stok & Interaksi Obat", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                            Surface(shape = RoundedCornerShape(4.dp), color = ClinicalTertiaryFixed) {
                                Text("1 Kritis • 0 Interaksi", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalOnTertiaryFixed, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        // Paracetamol Critical
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = ClinicalTertiaryFixed.copy(alpha = 0.3f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.ProductionQuantityLimits, contentDescription = null, tint = ClinicalTertiary)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Paracetamol Drop 100mg / 15ml", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                                    Text("Tersisa 4 botol di bawah batas minimum (5 botol). Perlu PO restock.", fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
                                }
                                Button(
                                    onClick = { toastMessage = "Surat Pesanan PO Paracetamol dibuat." },
                                    colors = ButtonDefaults.buttonColors(containerColor = ClinicalTertiaryContainer),
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text("PO", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        // Clinical Screening Safe Badge
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = ClinicalSurfaceContainerLow,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.HealthAndSafety, contentDescription = null, tint = ClinicalPrimary)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Skrining Klinis SAW: 100% Lolos", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                                    Text("Tidak ditemukan kontraindikasi usia atau interaksi mayor antar resep.", fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Review Modal
        if (selectedPrescriptionForReview != null) {
            val rx = selectedPrescriptionForReview!!
            Dialog(onDismissRequest = { selectedPrescriptionForReview = null }) {
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
                            Text("Telaah Klinis Resep ${rx.noResep}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                            IconButton(onClick = { selectedPrescriptionForReview = null }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }

                        Text("Santri: ${rx.namaSantri} (${rx.kamar})", fontSize = 12.sp, color = ClinicalPrimary, fontWeight = FontWeight.SemiBold)

                        Surface(shape = RoundedCornerShape(8.dp), color = ClinicalSurfaceContainerLow, modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(14.dp))
                                    Text("Tepat Pasien & Dosis Proporsional", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(14.dp))
                                    Text("Tepat Indikasi & Waktu Minum Terjadwal", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(14.dp))
                                    Text("Bebas Duplikasi & Bebas Alergi", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }

                        Button(
                            onClick = {
                                selectedPrescriptionForReview = null
                                toastMessage = "Resep ${rx.noResep} telah tervalidasi telaah klinis."
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Validasi & Lanjutkan", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Toast feedback
        if (toastMessage != null) {
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
                    Text(toastMessage!!, color = Color.White, fontSize = 12.sp)
                    IconButton(onClick = { toastMessage = null }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TabPill(label: String, isSelected: Boolean, badgeColor: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) ClinicalPrimary else Color.White,
        shadowElevation = 1.dp,
        modifier = modifier.clickable { onClick() }
    ) {
        Box(modifier = Modifier.padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
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
private fun PrescriptionWaitingCard(
    rx: Prescription,
    onStartPrepare: () -> Unit,
    onReview: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(rx.noResep, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ClinicalOutline)
                    Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSurfaceContainerLow) {
                        Text(rx.waktu, fontSize = 9.sp, color = ClinicalOnSurfaceVariant, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                    }
                }
                Surface(shape = RoundedCornerShape(4.dp), color = ClinicalErrorContainer) {
                    Text("Triase Darurat", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalError, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }

            Text(rx.namaSantri, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
            Text("${rx.kamar} • Dokter: ${rx.dokter}", fontSize = 10.sp, color = ClinicalOutline)

            // Items list
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                rx.items.forEach { item ->
                    Surface(shape = RoundedCornerShape(6.dp), color = ClinicalSurfaceContainerLow, modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(item.namaObat, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                                Text(item.aturanPakai, fontSize = 10.sp, color = ClinicalOnSurfaceVariant)
                            }
                            if (item.warning) {
                                Surface(shape = RoundedCornerShape(4.dp), color = ClinicalTertiaryFixed) {
                                    Text(item.stokInfo, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = ClinicalOnTertiaryFixed, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                }
                            }
                        }
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = onStartPrepare,
                    colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Science, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Mulai Racik", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onReview,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.VerifiedUser, contentDescription = null, modifier = Modifier.size(14.dp), tint = ClinicalPrimary)
                    Spacer(Modifier.width(4.dp))
                    Text("Telaah Resep", fontSize = 11.sp, color = ClinicalPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun PrescriptionReadyCard(
    rx: Prescription,
    onHandover: () -> Unit,
    onPrint: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(rx.noResep, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ClinicalOutline)
                Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSecondaryContainer) {
                    Text("Kemas Rapi", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }

            Text(rx.namaSantri, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
            Text("${rx.kamar} • Siap diambil di loket farmasi", fontSize = 10.sp, color = ClinicalOnSurfaceVariant)

            Surface(shape = RoundedCornerShape(6.dp), color = ClinicalPrimaryFixed.copy(alpha = 0.2f), modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Status Notifikasi Asrama: Terkirim ke Pengurus Asrama via SIMKES WhatsApp Bot.",
                    fontSize = 10.sp,
                    color = ClinicalPrimary,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = onHandover,
                    colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Handshake, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Serahkan Obat", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onPrint,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(14.dp), tint = ClinicalSecondary)
                }
            }
        }
    }
}

@Composable
private fun PrescriptionDoneCard(rx: Prescription) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(rx.noResep, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ClinicalOutline)
                Surface(shape = RoundedCornerShape(4.dp), color = ClinicalPrimaryFixed) {
                    Text("Selesai Diserahkan", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalOnPrimaryFixed, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }

            Text(rx.namaSantri, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
            Text("Diterima oleh: ${if (rx.penerima.isNotBlank()) rx.penerima else "Santri Langsung"}", fontSize = 11.sp, color = ClinicalPrimary, fontWeight = FontWeight.SemiBold)
            Text("Pengurangan stok otomatis berhasil dicatat di buku kasir obat Poskestren.", fontSize = 10.sp, color = ClinicalOutline)
        }
    }
}
