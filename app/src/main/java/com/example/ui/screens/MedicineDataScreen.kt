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
import com.example.data.MedicineStatus
import com.example.data.Obat
import com.example.data.PoskestrenRepository
import com.example.ui.theme.*

@Composable
fun MedicineDataScreen(
    onNavigateToDispensing: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }
    var selectedStatus by remember { mutableStateOf("Semua") }

    var medicines by remember { mutableStateOf(PoskestrenRepository.initialMedicines) }
    var selectedObatForCard by remember { mutableStateOf<Obat?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    // Add medicine form state
    var formCode by remember { mutableStateOf("") }
    var formName by remember { mutableStateOf("") }
    var formCategory by remember { mutableStateOf("Antipiretik Anak") }
    var formStock by remember { mutableStateOf("20") }
    var formUnit by remember { mutableStateOf("Botol") }
    var formRack by remember { mutableStateOf("Rak A-01") }
    var formExpiry by remember { mutableStateOf("2026-12-30") }

    val filteredMedicines = remember(searchQuery, selectedCategory, selectedStatus, medicines) {
        medicines.filter { o ->
            val matchQuery = searchQuery.isBlank() ||
                o.nama.contains(searchQuery, ignoreCase = true) ||
                o.kode.contains(searchQuery, ignoreCase = true) ||
                o.deskripsi.contains(searchQuery, ignoreCase = true)
            val matchCategory = selectedCategory == "Semua" || o.kategori.contains(selectedCategory, ignoreCase = true)
            val matchStatus = selectedStatus == "Semua" || when (selectedStatus) {
                "Aman" -> o.status == MedicineStatus.AMAN
                "Menipis" -> o.status == MedicineStatus.MENIPIS
                "Kritis" -> o.status == MedicineStatus.KRITIS
                "Kadaluwarsa" -> o.status == MedicineStatus.KADALUWARSA_DEKAT
                else -> true
            }
            matchQuery && matchCategory && matchStatus
        }
    }

    Box(modifier = modifier.fillMaxSize().background(ClinicalBackground)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Bar
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
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.Inventory2, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(16.dp))
                                Text("MODUL FARMASI POSKESTREN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                            }
                            Text("Sinkronisasi: 10 Mnt lalu", fontSize = 10.sp, color = ClinicalOutline)
                        }

                        Text("Manajemen Data Obat & Stok Farmasi", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                        Text("Pengendalian persediaan obat, pelacakan batch kedaluwarsa, dan klasifikasi obat poskestren.", fontSize = 11.sp, color = ClinicalOnSurfaceVariant)
                    }
                }
            }

            // 4 Stats Cards
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MedicineStatCard("TOTAL JENIS OBAT", "142", "6 Golongan Farmasi", Icons.Default.Medication, ClinicalPrimary, Modifier.weight(1f))
                        MedicineStatCard("STOK KRITIS / MENIPIS", "6", "Perlu Restock", Icons.Default.Warning, ClinicalError, Modifier.weight(1f))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MedicineStatCard("MENDEKATI EXPIRED", "4", "< 60 Hari (FEFO)", Icons.Default.Event, ClinicalTertiary, Modifier.weight(1f))
                        MedicineStatCard("OBAT EMERGENCY UGD", "18", "Troli Siaga UGD", Icons.Default.LocalHospital, ClinicalPrimaryContainer, Modifier.weight(1f))
                    }
                }
            }

            // Protocol FEFO Banner
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ClinicalInverseSurface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(ClinicalPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Inventory2, contentDescription = null, tint = Color.White)
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("Protokol FEFO Farmasi Al-Bahjah", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Surface(shape = RoundedCornerShape(4.dp), color = ClinicalPrimary) {
                                    Text("Aktif", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                }
                            }
                            Text(
                                text = "Pengeluaran obat mendahulukan expired terdekat. Suhu Ruang Farmasi: 23.4°C (Stabil).",
                                fontSize = 10.sp,
                                color = ClinicalSurfaceDim,
                                lineHeight = 14.sp
                            )
                        }

                        Button(
                            onClick = {
                                formCode = "OBT-0" + (61..99).random()
                                formName = ""
                                showAddDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimaryFixed),
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("+ Batch", color = ClinicalOnPrimaryFixed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Search & Action Toolbar
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Cari kode obat, nama obat, atau zat aktif...", fontSize = 12.sp) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ClinicalOutline) },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(16.dp))
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Action Buttons row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Button(
                                onClick = {
                                    formCode = "OBT-0" + (61..99).random()
                                    formName = ""
                                    showAddDialog = true
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Tambah Obat", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = onNavigateToDispensing,
                                colors = ButtonDefaults.buttonColors(containerColor = ClinicalSecondaryContainer),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Medication, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Dispensing", color = ClinicalPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Filter chips
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            CategoryPill("Semua", selectedCategory == "Semua") { selectedCategory = "Semua" }
                            CategoryPill("Antipiretik", selectedCategory == "Antipiretik") { selectedCategory = "Antipiretik" }
                            CategoryPill("Antibiotik", selectedCategory == "Antibiotik") { selectedCategory = "Antibiotik" }
                            CategoryPill("Asma", selectedCategory == "Asma") { selectedCategory = "Asma" }
                            CategoryPill("Topikal", selectedCategory == "Antiseptik") { selectedCategory = "Antiseptik" }
                        }
                    }
                }
            }

            // Table of Medicines
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Inventaris Obat Poskestren (${filteredMedicines.size})", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                    Text("Terhubung E-Logistik Al-Bahjah", fontSize = 10.sp, color = ClinicalPrimary, fontWeight = FontWeight.SemiBold)
                }
            }

            items(filteredMedicines) { obat ->
                MedicineRowCard(
                    obat = obat,
                    onCardClick = { selectedObatForCard = obat },
                    onEditClick = {
                        formCode = obat.kode
                        formName = obat.nama
                        formCategory = obat.kategori
                        formStock = obat.sisaStok.toString()
                        formUnit = obat.satuan
                        formRack = obat.lokasiRak
                        formExpiry = obat.tglExpired
                        showAddDialog = true
                    }
                )
            }

            // Denah Rak Farmasi
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
                            Text("Denah Rak Farmasi Poskestren", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                            Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSurfaceContainerLow) {
                                Text("Kulkas: 4.2°C (Aman)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            RackBox("RAK A", "Antipiretik / Analgesik", "42 SKU", ClinicalPrimary, Modifier.weight(1f))
                            RackBox("RAK B", "Antibiotik & Antivirus", "28 SKU", ClinicalPrimary, Modifier.weight(1f))
                            RackBox("RAK C", "Salep & Cairan", "36 SKU", ClinicalSecondary, Modifier.weight(1f))
                            RackBox("TROLI UGD", "Fast-Response Syok", "18 SKU", ClinicalError, Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // Add/Edit Dialog
        if (showAddDialog) {
            Dialog(onDismissRequest = { showAddDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Tambah / Sesuaikan Stok Obat", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)

                        OutlinedTextField(
                            value = formCode,
                            onValueChange = { formCode = it },
                            label = { Text("Kode Obat", fontSize = 11.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        OutlinedTextField(
                            value = formName,
                            onValueChange = { formName = it },
                            label = { Text("Nama Obat & Dosis", fontSize = 11.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = formStock,
                                onValueChange = { formStock = it },
                                label = { Text("Sisa Stok", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            OutlinedTextField(
                                value = formUnit,
                                onValueChange = { formUnit = it },
                                label = { Text("Satuan", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = formRack,
                                onValueChange = { formRack = it },
                                label = { Text("Lokasi Rak", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            OutlinedTextField(
                                value = formExpiry,
                                onValueChange = { formExpiry = it },
                                label = { Text("Expired", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { showAddDialog = false },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Batal")
                            }

                            Button(
                                onClick = {
                                    if (formName.isNotBlank()) {
                                        val stockInt = formStock.toIntOrNull() ?: 10
                                        val status = when {
                                            stockInt <= 5 -> MedicineStatus.KRITIS
                                            stockInt <= 15 -> MedicineStatus.MENIPIS
                                            else -> MedicineStatus.AMAN
                                        }
                                        val newObat = Obat(
                                            kode = formCode,
                                            nama = formName,
                                            deskripsi = "Sediaan Poskestren Al-Bahjah",
                                            kategori = formCategory,
                                            sisaStok = stockInt,
                                            satuan = formUnit,
                                            tglExpired = formExpiry,
                                            lokasiRak = formRack,
                                            nomorBatch = "BTH-2024-001",
                                            batasMinimum = 5,
                                            status = status
                                        )
                                        medicines = listOf(newObat) + medicines.filter { it.kode != formCode }
                                        showAddDialog = false
                                        toastMessage = "Data obat $formName berhasil disimpan."
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Simpan", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Stock Card Detail Modal
        if (selectedObatForCard != null) {
            val obat = selectedObatForCard!!
            Dialog(onDismissRequest = { selectedObatForCard = null }) {
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
                            Column {
                                Text(obat.nama, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                                Text("${obat.kode} • ${obat.lokasiRak}", fontSize = 11.sp, color = ClinicalOutline)
                            }
                            IconButton(onClick = { selectedObatForCard = null }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = ClinicalSurfaceContainerLow,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("SISA STOK FISIK:", fontSize = 10.sp, color = ClinicalOutline)
                                    Text("${obat.sisaStok} ${obat.satuan}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                                }
                                Text("Expired: ${obat.tglExpired}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = ClinicalOnSurface)
                            }
                        }

                        Text("Riwayat Mutasi 30 Hari Terakhir:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                        MutationRow("24/10/2024", "Resep Rawat Jalan Santri #RXP-9021", "-2 ${obat.satuan}", ClinicalError)
                        MutationRow("21/10/2024", "Pelayanan Demam Asrama #RXP-8940", "-1 ${obat.satuan}", ClinicalError)
                        MutationRow("15/10/2024", "Penerimaan Faktur Farmasi #FP-818", "+10 ${obat.satuan}", ClinicalPrimary)

                        Button(
                            onClick = { selectedObatForCard = null },
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

        // Feedback toast
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
                    IconButton(onClick = { toastMessage = null }, modifier = Modifier.size(20.dp)) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MedicineStatCard(title: String, valText: String, sub: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = ClinicalOutline)
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(14.dp))
            }
            Text(valText, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = color)
            Text(sub, fontSize = 9.sp, color = ClinicalOnSurfaceVariant)
        }
    }
}

@Composable
private fun CategoryPill(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = if (isSelected) ClinicalPrimary else ClinicalSurfaceContainerLow,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else ClinicalOnSurfaceVariant,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun MedicineRowCard(
    obat: Obat,
    onCardClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth().clickable { onCardClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(obat.kode, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary, fontFamily = FontFamily.Monospace)
                    Surface(shape = RoundedCornerShape(4.dp), color = ClinicalSurfaceContainerLow) {
                        Text(obat.kategori, fontSize = 9.sp, color = ClinicalOnSurfaceVariant, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                    }
                }
                Text(obat.nama, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                Text("${obat.deskripsi} • ${obat.lokasiRak}", fontSize = 10.sp, color = ClinicalOutline)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text("${obat.sisaStok} ${obat.satuan}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = when(obat.status) {
                    MedicineStatus.KRITIS -> ClinicalError
                    MedicineStatus.MENIPIS -> ClinicalTertiary
                    else -> ClinicalPrimary
                })

                val (badgeText, badgeBg, badgeTextColor) = when (obat.status) {
                    MedicineStatus.KRITIS -> Triple("Kritis (<5)", ClinicalErrorContainer, ClinicalError)
                    MedicineStatus.MENIPIS -> Triple("Menipis", ClinicalTertiaryFixed, ClinicalOnTertiaryFixed)
                    MedicineStatus.KADALUWARSA_DEKAT -> Triple("Dekat EXP", ClinicalTertiaryFixed, ClinicalOnTertiaryFixed)
                    else -> Triple("Aman", ClinicalSecondaryContainer, ClinicalPrimary)
                }

                Surface(shape = RoundedCornerShape(4.dp), color = badgeBg) {
                    Text(badgeText, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = badgeTextColor, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                }
            }

            IconButton(onClick = onEditClick, modifier = Modifier.size(30.dp)) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = ClinicalOutline, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun RackBox(name: String, desc: String, sku: String, color: Color, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = ClinicalSurfaceContainerLow,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(name, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
            Text(desc, fontSize = 8.sp, color = ClinicalOutline, maxLines = 1)
            Surface(shape = RoundedCornerShape(4.dp), color = Color.White) {
                Text(sku, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = color, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
            }
        }
    }
}

@Composable
private fun MutationRow(date: String, doc: String, change: String, changeColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(doc, fontSize = 10.sp, color = ClinicalOnSurface, maxLines = 1)
            Text(date, fontSize = 9.sp, color = ClinicalOutline)
        }
        Text(change, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = changeColor)
    }
}
