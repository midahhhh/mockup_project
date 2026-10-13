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
import com.example.data.Santri
import com.example.data.SantriHealthStatus
import com.example.ui.components.RemoteImage
import com.example.ui.theme.*

@Composable
fun SantriDataScreen(
    onNavigateToExamination: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedAsramaFilter by remember { mutableStateOf("Semua") }
    var selectedStatusFilter by remember { mutableStateOf("Semua") }

    var santriList by remember { mutableStateOf(PoskestrenRepository.initialSantriList) }
    var showAddEditDialog by remember { mutableStateOf(false) }
    var editingSantri by remember { mutableStateOf<Santri?>(null) }
    var toastMsg by remember { mutableStateOf<String?>(null) }

    // Form states for Add/Edit
    var formNis by remember { mutableStateOf("") }
    var formNama by remember { mutableStateOf("") }
    var formGender by remember { mutableStateOf("Laki-laki") }
    var formUsia by remember { mutableStateOf("15") }
    var formJenjang by remember { mutableStateOf("Kelas 10A") }
    var formAsrama by remember { mutableStateOf("Abu Bakar") }
    var formKamar by remember { mutableStateOf("Abu Bakar 01") }
    var formGolDarah by remember { mutableStateOf("A+") }
    var formAlergi by remember { mutableStateOf("Tidak Ada") }
    var formNamaWali by remember { mutableStateOf("") }
    var formHpWali by remember { mutableStateOf("") }

    val filteredList = remember(searchQuery, selectedAsramaFilter, selectedStatusFilter, santriList) {
        santriList.filter { s ->
            val matchSearch = searchQuery.isBlank() ||
                s.nama.contains(searchQuery, ignoreCase = true) ||
                s.nis.contains(searchQuery, ignoreCase = true) ||
                s.namaWali.contains(searchQuery, ignoreCase = true)
            val matchAsrama = selectedAsramaFilter == "Semua" || s.asrama.equals(selectedAsramaFilter, ignoreCase = true)
            val matchStatus = selectedStatusFilter == "Semua" || when (selectedStatusFilter) {
                "Sehat" -> s.status == SantriHealthStatus.SEHAT_DI_ASRAMA
                "Diperiksa" -> s.status == SantriHealthStatus.DIPERIKSA_POSKESTREN
                "Triase" -> s.status == SantriHealthStatus.ANTREAN_TRIASE
                "Istirahat" -> s.status == SantriHealthStatus.IZIN_ISTIRAHAT
                else -> true
            }
            matchSearch && matchAsrama && matchStatus
        }
    }

    Box(modifier = modifier.fillMaxSize().background(ClinicalBackground)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Stats 4-Pack
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SantriStatCard("TOTAL SANTRI", "850", "100% EMR", Icons.Default.School, ClinicalPrimary, Modifier.weight(1f))
                        SantriStatCard("SEHAT DI ASRAMA", "826", "97.1% Prima", Icons.Default.CheckCircle, ClinicalPrimaryContainer, Modifier.weight(1f))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SantriStatCard("PERAWATAN & TRIASE", "14", "Di Poskestren", Icons.Default.MedicalServices, ClinicalError, Modifier.weight(1f))
                        SantriStatCard("PERINGATAN ALERGI", "112", "Obat/Makanan", Icons.Default.NotificationsActive, ClinicalTertiary, Modifier.weight(1f))
                    }
                }
            }

            // Header & Action Bar
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
                            Column {
                                Text("INTEGRASI EMR & KESISWAAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                                Text("Master Data Santri & Rekam Induk", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                            }

                            Button(
                                onClick = {
                                    editingSantri = null
                                    formNis = "2024-0" + (100..999).random()
                                    formNama = ""
                                    formNamaWali = ""
                                    formHpWali = ""
                                    formAlergi = "Tidak Ada"
                                    showAddEditDialog = true
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("+ Santri Baru", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Search Bar
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Cari NIS, Nama Santri, atau Kontak Wali...", fontSize = 12.sp) },
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

                        // Filter Chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FilterDropdownButton(
                                label = "Asrama: $selectedAsramaFilter",
                                onClick = {
                                    selectedAsramaFilter = when (selectedAsramaFilter) {
                                        "Semua" -> "Abu Bakar"
                                        "Abu Bakar" -> "Umar"
                                        "Umar" -> "Ali"
                                        "Ali" -> "Hamzah"
                                        "Hamzah" -> "Khadijah"
                                        else -> "Semua"
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )

                            FilterDropdownButton(
                                label = "Status: $selectedStatusFilter",
                                onClick = {
                                    selectedStatusFilter = when (selectedStatusFilter) {
                                        "Semua" -> "Sehat"
                                        "Sehat" -> "Diperiksa"
                                        "Diperiksa" -> "Triase"
                                        "Triase" -> "Istirahat"
                                        else -> "Semua"
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )

                            Button(
                                onClick = {
                                    toastMsg = "Mengekspor berkas Excel Master 850 Santri..."
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ClinicalSurfaceContainerLow),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.FileDownload, contentDescription = null, tint = ClinicalSecondary, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Ekspor", color = ClinicalSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Santri List Header
            item {
                Text(
                    text = "Daftar Santri (${filteredList.size} ditampilkan)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = ClinicalOnSurface
                )
            }

            // Santri Cards
            items(filteredList) { santri ->
                SantriCard(
                    santri = santri,
                    onEditClick = {
                        editingSantri = santri
                        formNis = santri.nis
                        formNama = santri.nama
                        formGender = santri.gender
                        formUsia = santri.usia.toString()
                        formJenjang = santri.jenjang
                        formAsrama = santri.asrama
                        formKamar = santri.kamar
                        formGolDarah = santri.golDarah
                        formAlergi = santri.alergi
                        formNamaWali = santri.namaWali
                        formHpWali = santri.hpWali
                        showAddEditDialog = true
                    },
                    onMedicalRecordClick = {
                        onNavigateToExamination(santri.nis)
                    },
                    onDeleteClick = {
                        santriList = santriList.filter { it.nis != santri.nis }
                        toastMsg = "Data santri ${santri.nama} berhasil diarsipkan."
                    }
                )
            }
        }

        // Add/Edit Dialog
        if (showAddEditDialog) {
            Dialog(onDismissRequest = { showAddEditDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth().heightIn(max = 580.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (editingSantri == null) "Tambah Santri Baru" else "Edit Data Santri",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ClinicalOnSurface
                                )
                                IconButton(onClick = { showAddEditDialog = false }) {
                                    Icon(Icons.Default.Close, contentDescription = null)
                                }
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = formNis,
                                onValueChange = { formNis = it },
                                label = { Text("Nomor Induk Santri (NIS)", fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = formNama,
                                onValueChange = { formNama = it },
                                label = { Text("Nama Lengkap Santri", fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        item {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value = formJenjang,
                                    onValueChange = { formJenjang = it },
                                    label = { Text("Kelas / Jenjang", fontSize = 11.sp) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                OutlinedTextField(
                                    value = formGolDarah,
                                    onValueChange = { formGolDarah = it },
                                    label = { Text("Gol. Darah", fontSize = 11.sp) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                        }

                        item {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value = formAsrama,
                                    onValueChange = { formAsrama = it },
                                    label = { Text("Asrama", fontSize = 11.sp) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                OutlinedTextField(
                                    value = formKamar,
                                    onValueChange = { formKamar = it },
                                    label = { Text("Kamar", fontSize = 11.sp) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = formAlergi,
                                onValueChange = { formAlergi = it },
                                label = { Text("Riwayat Alergi (Obat, Makanan, Cuaca)", fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = formNamaWali,
                                onValueChange = { formNamaWali = it },
                                label = { Text("Nama Wali Santri", fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = formHpWali,
                                onValueChange = { formHpWali = it },
                                label = { Text("No. WhatsApp Wali Santri", fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        item {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(
                                    onClick = { showAddEditDialog = false },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Batal")
                                }

                                Button(
                                    onClick = {
                                        if (formNama.isNotBlank() && formNis.isNotBlank()) {
                                            val newSantri = Santri(
                                                nis = formNis,
                                                nama = formNama,
                                                gender = formGender,
                                                usia = formUsia.toIntOrNull() ?: 15,
                                                jenjang = formJenjang,
                                                asrama = formAsrama,
                                                kamar = formKamar,
                                                golDarah = formGolDarah,
                                                alergi = formAlergi,
                                                namaWali = formNamaWali,
                                                hpWali = formHpWali,
                                                status = SantriHealthStatus.SEHAT_DI_ASRAMA,
                                                avatarUrl = ""
                                            )
                                            santriList = if (editingSantri != null) {
                                                santriList.map { if (it.nis == editingSantri!!.nis) newSantri else it }
                                            } else {
                                                listOf(newSantri) + santriList
                                            }
                                            showAddEditDialog = false
                                            toastMsg = "Data santri $formNama berhasil disimpan."
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Simpan", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Feedback toast
        if (toastMsg != null) {
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
                    Text(toastMsg!!, color = Color.White, fontSize = 12.sp)
                    IconButton(onClick = { toastMsg = null }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun SantriStatCard(title: String, valText: String, sub: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ClinicalOutline)
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
            }
            Text(valText, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
            Text(sub, fontSize = 9.sp, color = ClinicalOnSurfaceVariant)
        }
    }
}

@Composable
private fun FilterDropdownButton(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = ClinicalSurfaceContainerLow,
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = ClinicalOnSurface, maxLines = 1)
            Icon(Icons.Default.ExpandMore, contentDescription = null, tint = ClinicalOutline, modifier = Modifier.size(14.dp))
        }
    }
}

@Composable
private fun SantriCard(
    santri: Santri,
    onEditClick: () -> Unit,
    onMedicalRecordClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                ) {
                    RemoteImage(
                        url = santri.avatarUrl,
                        contentDescription = santri.nama,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(santri.nama, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ClinicalOnSurface)
                        Text(santri.nis, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                    }
                    Text("${santri.gender} • ${santri.usia} Tahun • ${santri.jenjang}", fontSize = 11.sp, color = ClinicalOutline)
                    Text("Asrama ${santri.kamar}", fontSize = 11.sp, color = ClinicalOnSurfaceVariant)
                }

                // Status chip
                val (statusText, statusBg, statusColor) = when (santri.status) {
                    SantriHealthStatus.DIPERIKSA_POSKESTREN -> Triple("Diperiksa", ClinicalErrorContainer, ClinicalError)
                    SantriHealthStatus.ANTREAN_TRIASE -> Triple("Triase", ClinicalErrorContainer, ClinicalError)
                    SantriHealthStatus.IZIN_ISTIRAHAT -> Triple("Izin Kamar", ClinicalTertiaryFixed, ClinicalOnTertiaryFixed)
                    SantriHealthStatus.RAWAT_INAP -> Triple("Rawat Inap", ClinicalTertiaryFixed, ClinicalOnTertiaryFixed)
                    else -> Triple("Sehat", ClinicalSecondaryContainer, ClinicalPrimary)
                }

                Surface(shape = RoundedCornerShape(12.dp), color = statusBg) {
                    Text(
                        text = statusText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Info row: Gol darah, alergi, wali
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ClinicalSurfaceContainerLow, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Gol: ${santri.golDarah}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                        Text("• Alergi: ${santri.alergi}", fontSize = 10.sp, color = if (santri.alergi.contains("Fatal", true)) ClinicalError else ClinicalOnSurfaceVariant)
                    }
                    Text("Wali: ${santri.namaWali} (${santri.hpWali})", fontSize = 10.sp, color = ClinicalOutline)
                }

                Surface(shape = RoundedCornerShape(10.dp), color = Color.White) {
                    Text(
                        text = "${santri.riwayatBerobatCount}x Periksa",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ClinicalSecondary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onEditClick, modifier = Modifier.size(30.dp)) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = ClinicalSecondary, modifier = Modifier.size(16.dp))
                }
                IconButton(onClick = onMedicalRecordClick, modifier = Modifier.size(30.dp)) {
                    Icon(Icons.Default.HistoryEdu, contentDescription = "Rekam Medis", tint = ClinicalPrimary, modifier = Modifier.size(16.dp))
                }
                IconButton(onClick = onDeleteClick, modifier = Modifier.size(30.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = ClinicalError, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
