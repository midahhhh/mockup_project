package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.PoskestrenRepository
import com.example.ui.components.AppClinicalHeader
import com.example.ui.screens.*
import com.example.ui.theme.*
import kotlinx.coroutines.launch

enum class Screen(val title: String, val icon: ImageVector) {
    LANDING("Beranda Publik", Icons.Default.Home),
    AUTH("Login SIMKES", Icons.Default.Lock),
    DASHBOARD("Dashboard Medis", Icons.Default.Dashboard),
    TRIASE("Papan Antrean SAW", Icons.Default.ListAlt),
    PEMERIKSAAN("Pemeriksaan Fisik SAW", Icons.Default.MedicalServices),
    SANTRI("Master Data Santri", Icons.Default.People),
    OBAT("Modul Farmasi & Stok", Icons.Default.Inventory2),
    DISPENSING("Dispensing Resep", Icons.Default.Medication),
    PORTAL("Portal Santri & Wali", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppNavigation()
            }
        }
    }
}

@Composable
fun MainAppNavigation() {
    var currentScreen by remember { mutableStateOf(Screen.LANDING) }
    var selectedPatientNisForExam by remember { mutableStateOf("2023-0891") }
    var currentRole by remember { mutableStateOf(UserRole.MEDIS) }
    var currentUserName by remember { mutableStateOf("dr. Agus Syaif, Sp.PD") }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var showGlobalSearchDialog by remember { mutableStateOf(false) }

    // Handle back button press
    BackHandler(enabled = true) {
        if (drawerState.isOpen) {
            coroutineScope.launch { drawerState.close() }
        } else {
            when (currentScreen) {
                Screen.LANDING -> {
                    // At root screen, let system handle exit
                }
                Screen.PEMERIKSAAN -> {
                    currentScreen = Screen.TRIASE
                }
                Screen.AUTH -> {
                    currentScreen = Screen.LANDING
                }
                Screen.TRIASE, Screen.SANTRI, Screen.OBAT, Screen.DISPENSING -> {
                    currentScreen = Screen.DASHBOARD
                }
                Screen.DASHBOARD, Screen.PORTAL -> {
                    currentScreen = Screen.LANDING
                }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentScreen != Screen.LANDING && currentScreen != Screen.AUTH,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                modifier = Modifier.width(320.dp)
            ) {
                // Drawer Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ClinicalPrimary)
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.LocalHospital,
                                    contentDescription = null,
                                    tint = ClinicalPrimary,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "POSKESTREN",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Al-Bahjah Kampus Utama",
                                fontSize = 12.sp,
                                color = ClinicalSecondaryContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Active User Info
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = ClinicalPrimaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = currentRole.icon,
                                    contentDescription = null,
                                    tint = ClinicalPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = currentUserName,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Hak Akses: ${currentRole.label}",
                                    fontSize = 10.sp,
                                    color = ClinicalSecondaryFixedDim
                                )
                            }
                        }
                    }
                }

                HorizontalDivider(color = ClinicalOutlineVariant)

                // Navigation Items
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 8.dp)
                ) {
                    item {
                        Text(
                            text = "PORTAL UTAMA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ClinicalOutline,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                        )
                    }

                    item {
                        DrawerNavItem(
                            screen = Screen.LANDING,
                            isSelected = currentScreen == Screen.LANDING,
                            onClick = {
                                currentScreen = Screen.LANDING
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }

                    item {
                        DrawerNavItem(
                            screen = Screen.AUTH,
                            isSelected = currentScreen == Screen.AUTH,
                            badge = "Ganti Akun",
                            onClick = {
                                currentScreen = Screen.AUTH
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }

                    item {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "MODUL KLINIS MEDIS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ClinicalOutline,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                        )
                    }

                    item {
                        DrawerNavItem(
                            screen = Screen.DASHBOARD,
                            isSelected = currentScreen == Screen.DASHBOARD,
                            onClick = {
                                currentScreen = Screen.DASHBOARD
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }

                    item {
                        DrawerNavItem(
                            screen = Screen.TRIASE,
                            isSelected = currentScreen == Screen.TRIASE,
                            badge = "4 Antrean",
                            badgeColor = ClinicalError,
                            onClick = {
                                currentScreen = Screen.TRIASE
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }

                    item {
                        DrawerNavItem(
                            screen = Screen.PEMERIKSAAN,
                            isSelected = currentScreen == Screen.PEMERIKSAAN,
                            badge = "SAW Auto",
                            onClick = {
                                currentScreen = Screen.PEMERIKSAAN
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }

                    item {
                        DrawerNavItem(
                            screen = Screen.SANTRI,
                            isSelected = currentScreen == Screen.SANTRI,
                            badge = "350 Santri",
                            onClick = {
                                currentScreen = Screen.SANTRI
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }

                    item {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "FARMASI & SANTRI",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ClinicalOutline,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                        )
                    }

                    item {
                        DrawerNavItem(
                            screen = Screen.OBAT,
                            isSelected = currentScreen == Screen.OBAT,
                            badge = "6 Kritis",
                            badgeColor = ClinicalTertiary,
                            onClick = {
                                currentScreen = Screen.OBAT
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }

                    item {
                        DrawerNavItem(
                            screen = Screen.DISPENSING,
                            isSelected = currentScreen == Screen.DISPENSING,
                            badge = "Kanban",
                            onClick = {
                                currentScreen = Screen.DISPENSING
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }

                    item {
                        DrawerNavItem(
                            screen = Screen.PORTAL,
                            isSelected = currentScreen == Screen.PORTAL,
                            badge = "Wali",
                            onClick = {
                                currentScreen = Screen.PORTAL
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }
                }

                HorizontalDivider(color = ClinicalOutlineVariant)

                // Quick Logout / Switch to Landing
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            currentScreen = Screen.LANDING
                            coroutineScope.launch { drawerState.close() }
                        }
                        .padding(16.dp),
                    color = Color.Transparent
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Keluar",
                            tint = ClinicalError
                        )
                        Text(
                            text = "Keluar ke Beranda Publik",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = ClinicalError
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (currentScreen != Screen.LANDING && currentScreen != Screen.AUTH) {
                    AppClinicalHeader(
                        currentScreenTitle = currentScreen.title,
                        onMenuClick = { coroutineScope.launch { drawerState.open() } },
                        onSearchClick = { showGlobalSearchDialog = true },
                        activeRole = currentRole.label
                    )
                }
            },
            bottomBar = {
                if (currentScreen != Screen.LANDING && currentScreen != Screen.AUTH) {
                    NavigationBar(
                        containerColor = Color.White,
                        tonalElevation = 8.dp
                    ) {
                        NavigationBarItem(
                            selected = currentScreen == Screen.DASHBOARD,
                            onClick = { currentScreen = Screen.DASHBOARD },
                            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                            label = { Text("Dashboard", fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ClinicalPrimary,
                                selectedTextColor = ClinicalPrimary,
                                indicatorColor = ClinicalSecondaryContainer
                            )
                        )
                        NavigationBarItem(
                            selected = currentScreen == Screen.TRIASE || currentScreen == Screen.PEMERIKSAAN,
                            onClick = { currentScreen = Screen.TRIASE },
                            icon = {
                                BadgedBox(badge = {
                                    Badge(containerColor = ClinicalError) { Text("4") }
                                }) {
                                    Icon(Icons.Default.ListAlt, contentDescription = "Triase SAW")
                                }
                            },
                            label = { Text("Triase", fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ClinicalPrimary,
                                selectedTextColor = ClinicalPrimary,
                                indicatorColor = ClinicalSecondaryContainer
                            )
                        )
                        NavigationBarItem(
                            selected = currentScreen == Screen.OBAT || currentScreen == Screen.DISPENSING,
                            onClick = { currentScreen = Screen.DISPENSING },
                            icon = { Icon(Icons.Default.Medication, contentDescription = "Farmasi") },
                            label = { Text("Farmasi", fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ClinicalPrimary,
                                selectedTextColor = ClinicalPrimary,
                                indicatorColor = ClinicalSecondaryContainer
                            )
                        )
                        NavigationBarItem(
                            selected = currentScreen == Screen.SANTRI,
                            onClick = { currentScreen = Screen.SANTRI },
                            icon = { Icon(Icons.Default.People, contentDescription = "Santri") },
                            label = { Text("Santri", fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ClinicalPrimary,
                                selectedTextColor = ClinicalPrimary,
                                indicatorColor = ClinicalSecondaryContainer
                            )
                        )
                        NavigationBarItem(
                            selected = currentScreen == Screen.PORTAL,
                            onClick = { currentScreen = Screen.PORTAL },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Portal") },
                            label = { Text("Portal", fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ClinicalPrimary,
                                selectedTextColor = ClinicalPrimary,
                                indicatorColor = ClinicalSecondaryContainer
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentScreen) {
                    Screen.LANDING -> {
                        LandingScreen(
                            onNavigateToLogin = { currentScreen = Screen.AUTH },
                            onNavigateToQueue = { currentScreen = Screen.TRIASE }
                        )
                    }

                    Screen.AUTH -> {
                        AuthScreen(
                            onLoginSuccess = { role, name ->
                                currentRole = role
                                currentUserName = name
                                currentScreen = when (role) {
                                    UserRole.MEDIS -> Screen.DASHBOARD
                                    UserRole.FARMASI -> Screen.DISPENSING
                                    UserRole.SANTRI, UserRole.WALI -> Screen.PORTAL
                                }
                            }
                        )
                    }

                    Screen.DASHBOARD -> {
                        DashboardScreen(
                            onNavigateToTriage = { currentScreen = Screen.TRIASE },
                            onNavigateToExamination = {
                                selectedPatientNisForExam = "2023-0891"
                                currentScreen = Screen.PEMERIKSAAN
                            },
                            onNavigateToSantri = { currentScreen = Screen.SANTRI },
                            onNavigateToPharmacy = { currentScreen = Screen.OBAT }
                        )
                    }

                    Screen.TRIASE -> {
                        TriageQueueScreen(
                            onNavigateToExamination = { nis ->
                                selectedPatientNisForExam = nis
                                currentScreen = Screen.PEMERIKSAAN
                            }
                        )
                    }

                    Screen.PEMERIKSAAN -> {
                        ExaminationScreen(
                            patientNis = selectedPatientNisForExam,
                            onNavigateToPrescription = { currentScreen = Screen.DISPENSING },
                            onNavigateBack = { currentScreen = Screen.TRIASE }
                        )
                    }

                    Screen.SANTRI -> {
                        SantriDataScreen(
                            onNavigateToExamination = { nis ->
                                selectedPatientNisForExam = nis
                                currentScreen = Screen.PEMERIKSAAN
                            }
                        )
                    }

                    Screen.OBAT -> {
                        MedicineDataScreen(
                            onNavigateToDispensing = { currentScreen = Screen.DISPENSING }
                        )
                    }

                    Screen.DISPENSING -> {
                        PharmacyDispensingScreen()
                    }

                    Screen.PORTAL -> {
                        SantriPortalScreen()
                    }
                }
            }
        }
    }

    // Global Fast Search Dialog
    if (showGlobalSearchDialog) {
        Dialog(onDismissRequest = { showGlobalSearchDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                var searchQuery by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Pencarian SIMKES Poskestren",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = ClinicalOnSurface
                    )

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Ketik NIS, nama santri, atau nama obat...", fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ClinicalPrimary) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Hapus")
                                }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Quick Jump suggestions
                    Text(
                        text = "Pintas Navigasi Cepat:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ClinicalOutline
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        SuggestionChip(
                            onClick = {
                                selectedPatientNisForExam = "2023-0891"
                                currentScreen = Screen.PEMERIKSAAN
                                showGlobalSearchDialog = false
                            },
                            label = { Text("M. Fatih (Darurat)", fontSize = 10.sp) }
                        )
                        SuggestionChip(
                            onClick = {
                                currentScreen = Screen.OBAT
                                showGlobalSearchDialog = false
                            },
                            label = { Text("Paracetamol", fontSize = 10.sp) }
                        )
                    }

                    // Matching results
                    val matchingSantri = remember(searchQuery) {
                        if (searchQuery.isBlank()) emptyList()
                        else PoskestrenRepository.initialSantriList.filter {
                            it.nama.contains(searchQuery, ignoreCase = true) || it.nis.contains(searchQuery)
                        }
                    }

                    if (matchingSantri.isNotEmpty()) {
                        Text("Hasil Santri:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ClinicalPrimary)
                        matchingSantri.take(3).forEach { s ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = ClinicalSurfaceContainerLow,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedPatientNisForExam = s.nis
                                        currentScreen = Screen.PEMERIKSAAN
                                        showGlobalSearchDialog = false
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(s.nama, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text("${s.nis} • ${s.asrama}", fontSize = 10.sp, color = ClinicalOutline)
                                    }
                                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = ClinicalOutline)
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showGlobalSearchDialog = false }) {
                            Text("Tutup", color = ClinicalOutline)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerNavItem(
    screen: Screen,
    isSelected: Boolean,
    badge: String? = null,
    badgeColor: Color = ClinicalPrimary,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) ClinicalSecondaryContainer else Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = screen.title,
                    tint = if (isSelected) ClinicalPrimary else ClinicalOnSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = screen.title,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) ClinicalPrimary else ClinicalOnSurface
                )
            }

            if (badge != null) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Color.White else badgeColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = badge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
