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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

enum class UserRole(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    MEDIS("Medis / Admin", Icons.Default.MedicalInformation),
    FARMASI("Farmasi", Icons.Default.Medication),
    SANTRI("Santri (NIS)", Icons.Default.School),
    WALI("Wali Santri", Icons.Default.FamilyRestroom)
}

@Composable
fun AuthScreen(
    onLoginSuccess: (UserRole, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedRole by remember { mutableStateOf(UserRole.MEDIS) }
    var identifier by remember { mutableStateOf("19890401001") }
    var credential by remember { mutableStateOf("MedisSecure2025#") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ClinicalBackground),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo and Header
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(ClinicalPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.HealthAndSafety,
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = ClinicalSurfaceContainer
                ) {
                    Text(
                        text = "Pos Kesehatan Pesantren Cabang Bogor",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ClinicalSecondary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = "SIM-Health & SPK Triase",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ClinicalOnSurface
                )

                Text(
                    text = "Portal Terpadu Manajemen Rekam Medis Santri, Pelayanan Rawat, dan Logistik Farmasi",
                    fontSize = 12.sp,
                    color = ClinicalOnSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // Encryption info banner
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = ClinicalPrimaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = ClinicalPrimaryFixed, modifier = Modifier.size(16.dp))
                        Text(
                            text = "AUTENTIKASI TERENKRIPSI",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = ClinicalPrimaryFixed
                        )
                    }

                    Text(
                        text = "Layanan Medis Khidmat Santri",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Akses multi-peran dengan protokol audit privasi rekam kesehatan terpadu dan monitoring bangsal santriwati & santriwan.",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = 15.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(ClinicalPrimaryFixed))
                            Text("SPK Triase: Normal", fontSize = 10.sp, color = ClinicalPrimaryFixed)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(ClinicalPrimaryFixed))
                            Text("Asrama: Real-time", fontSize = 10.sp, color = ClinicalPrimaryFixed)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(ClinicalPrimaryFixed))
                            Text("Apotek: Siaga", fontSize = 10.sp, color = ClinicalPrimaryFixed)
                        }
                    }
                }
            }
        }

        // Main Login Card
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = "PILIH PERAN PENGGUNA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ClinicalOutline
                    )

                    // Role 2x2 grid
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            RoleButton(
                                role = UserRole.MEDIS,
                                isSelected = selectedRole == UserRole.MEDIS,
                                onSelect = {
                                    selectedRole = UserRole.MEDIS
                                    identifier = "19890401001"
                                    credential = "MedisSecure2025#"
                                },
                                modifier = Modifier.weight(1f)
                            )
                            RoleButton(
                                role = UserRole.FARMASI,
                                isSelected = selectedRole == UserRole.FARMASI,
                                onSelect = {
                                    selectedRole = UserRole.FARMASI
                                    identifier = "19930812003"
                                    credential = "FarmasiAlbahjah!25"
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            RoleButton(
                                role = UserRole.SANTRI,
                                isSelected = selectedRole == UserRole.SANTRI,
                                onSelect = {
                                    selectedRole = UserRole.SANTRI
                                    identifier = "2023-0891"
                                    credential = "240182"
                                },
                                modifier = Modifier.weight(1f)
                            )
                            RoleButton(
                                role = UserRole.WALI,
                                isSelected = selectedRole == UserRole.WALI,
                                onSelect = {
                                    selectedRole = UserRole.WALI
                                    identifier = "0812-3456-7890"
                                    credential = "WaliSafe88#"
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Role Guidance Banner
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
                            Icon(selectedRole.icon, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(20.dp))
                            Column {
                                Text(
                                    text = when (selectedRole) {
                                        UserRole.MEDIS -> "Portal Petugas Medis & Dokter Jaga"
                                        UserRole.FARMASI -> "Portal Apoteker & Logistik Obat"
                                        UserRole.SANTRI -> "Portal Riwayat Kesehatan Santri"
                                        UserRole.WALI -> "Verifikasi Laporan Medis Wali Santri"
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ClinicalPrimary
                                )
                                Text(
                                    text = when (selectedRole) {
                                        UserRole.MEDIS -> "Pemeriksaan anamnesa, rekam triase darurat, dan e-prescription."
                                        UserRole.FARMASI -> "Validasi resep digital, racik obat, dan monitoring stok posko."
                                        UserRole.SANTRI -> "Cek riwayat periksa, antrean, dan dispensasi istirahat KBM."
                                        UserRole.WALI -> "Pantau diagnosa dokter, izin istirahat asrama, & obat santri."
                                    },
                                    fontSize = 10.sp,
                                    color = ClinicalOnSurfaceVariant
                                )
                            }
                        }
                    }

                    // Form Fields
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = when (selectedRole) {
                                UserRole.MEDIS -> "Username / NIP Petugas"
                                UserRole.FARMASI -> "Nomor Registrasi Apoteker (STRA / NIP)"
                                UserRole.SANTRI -> "Nomor Induk Santri (NIS)"
                                UserRole.WALI -> "No. HP Terdaftar / No. KK Wali"
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ClinicalOnSurface
                        )

                        OutlinedTextField(
                            value = identifier,
                            onValueChange = { identifier = it },
                            leadingIcon = {
                                Icon(Icons.Default.Badge, contentDescription = null, tint = ClinicalOutline)
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = when (selectedRole) {
                                    UserRole.SANTRI -> "PIN Keamanan Santri (6 Digit)"
                                    UserRole.WALI -> "Kode Akses / PIN Wali"
                                    else -> "Kata Sandi Akun"
                                },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ClinicalOnSurface
                            )
                            Text(
                                text = "Lupa Kredensial?",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ClinicalPrimary,
                                modifier = Modifier.clickable {
                                    errorMessage = "Hubungi Unit IT Poskestren Al-Bahjah untuk reset akun."
                                }
                            )
                        }

                        OutlinedTextField(
                            value = credential,
                            onValueChange = { credential = it },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ClinicalOutline)
                            },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = "Toggle password",
                                        tint = ClinicalOutline
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Checkbox(
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
                                    colors = CheckboxDefaults.colors(checkedColor = ClinicalPrimary)
                                )
                                Text("Ingat Sesi di Perangkat", fontSize = 11.sp, color = ClinicalOnSurfaceVariant)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Shield, contentDescription = null, tint = ClinicalOutline, modifier = Modifier.size(12.dp))
                                Text("SSL 256-bit", fontSize = 10.sp, color = ClinicalOutline)
                            }
                        }

                        if (errorMessage != null) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = ClinicalErrorContainer,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = errorMessage!!,
                                    color = ClinicalError,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        Button(
                            onClick = {
                                isLoading = true
                                errorMessage = null
                                onLoginSuccess(selectedRole, identifier)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ClinicalPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                Spacer(Modifier.width(8.dp))
                                Text("Memverifikasi Akses...")
                            } else {
                                Icon(Icons.Default.Login, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("Masuk ke Sistem", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Demo Accounts Quick Switcher
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = ClinicalSurfaceContainerLow,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.Bolt, contentDescription = null, tint = ClinicalPrimary, modifier = Modifier.size(14.dp))
                                    Text("UJI COBA CEPAT (AKUN DEMO)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                                }
                                Text("Klik untuk isi cepat", fontSize = 10.sp, color = ClinicalOutline)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                QuickDemoChip(
                                    label = "Dokter Jaga",
                                    dotColor = ClinicalPrimary,
                                    onClick = {
                                        selectedRole = UserRole.MEDIS
                                        identifier = "19890401001"
                                        credential = "MedisSecure2025#"
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                QuickDemoChip(
                                    label = "Farmasis",
                                    dotColor = ClinicalTertiary,
                                    onClick = {
                                        selectedRole = UserRole.FARMASI
                                        identifier = "19930812003"
                                        credential = "FarmasiAlbahjah!25"
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                QuickDemoChip(
                                    label = "Santri (NIS)",
                                    dotColor = ClinicalSecondary,
                                    onClick = {
                                        selectedRole = UserRole.SANTRI
                                        identifier = "2023-0891"
                                        credential = "240182"
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                QuickDemoChip(
                                    label = "Wali Santri",
                                    dotColor = ClinicalPrimaryContainer,
                                    onClick = {
                                        selectedRole = UserRole.WALI
                                        identifier = "0812-3456-7890"
                                        credential = "WaliSafe88#"
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Compliance footnote
        item {
            Text(
                text = "Sistem Terproteksi Kebijakan Privasi Data Medis Pesantren Al-Bahjah. Hak cipta terpelihara © 2025 Yayasan Al-Bahjah Bogor.",
                fontSize = 10.sp,
                color = ClinicalOutline,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    }
}

@Composable
private fun RoleButton(
    role: UserRole,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) Color.White else ClinicalSurfaceContainerLow,
        shadowElevation = if (isSelected) 2.dp else 0.dp,
        modifier = modifier.clickable { onSelect() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = role.icon,
                contentDescription = null,
                tint = if (isSelected) ClinicalPrimary else ClinicalOutline,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = role.label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) ClinicalPrimary else ClinicalOnSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun QuickDemoChip(
    label: String,
    dotColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = ClinicalOnSurface,
                maxLines = 1
            )
        }
    }
}
