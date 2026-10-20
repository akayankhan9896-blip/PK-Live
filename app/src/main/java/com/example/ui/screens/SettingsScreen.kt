package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BgSettings
import com.example.ui.theme.HotPink
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showSignOutDialog by remember { mutableStateOf(false) }
    var cacheSize by remember { mutableStateOf("42.5 MB") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgSettings)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 40.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = "Settings",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Card 1: Account & Security, Privacy
        SettingsCard {
            SettingsItemRow(
                title = "Account & Security",
                valueBadge = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF854D0E))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Medium",
                            color = NeonGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                onClick = {
                    Toast.makeText(context, "Account Security: Linked with Google", Toast.LENGTH_SHORT).show()
                }
            )

            HorizontalDivider(color = Color(0xFF262142), thickness = 0.5.dp)

            SettingsItemRow(
                title = "Privacy & Blocklist",
                onClick = {
                    Toast.makeText(context, "Blocklist is empty", Toast.LENGTH_SHORT).show()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 2: Notifications, Language, Clean Cache
        SettingsCard {
            SettingsItemRow(
                title = "Notifications",
                onClick = {
                    Toast.makeText(context, "Live notifications enabled", Toast.LENGTH_SHORT).show()
                }
            )

            HorizontalDivider(color = Color(0xFF262142), thickness = 0.5.dp)

            SettingsItemRow(
                title = "Language",
                valueText = "English",
                onClick = {
                    Toast.makeText(context, "Language: English (US)", Toast.LENGTH_SHORT).show()
                }
            )

            HorizontalDivider(color = Color(0xFF262142), thickness = 0.5.dp)

            SettingsItemRow(
                title = "Clean Cache",
                valueText = cacheSize,
                onClick = {
                    cacheSize = "0.0 MB"
                    Toast.makeText(context, "Cache cleared successfully!", Toast.LENGTH_SHORT).show()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 3: About PK LIVE
        SettingsCard {
            SettingsItemRow(
                title = "About PK LIVE",
                valueText = "v2.8.4",
                onClick = {
                    Toast.makeText(context, "PK LIVE v2.8.4 (Latest Version)", Toast.LENGTH_SHORT).show()
                }
            )

            HorizontalDivider(color = Color(0xFF262142), thickness = 0.5.dp)

            SettingsItemRow(
                title = "Community Guidelines & Terms",
                onClick = {
                    Toast.makeText(context, "PK LIVE safe live streaming policy", Toast.LENGTH_SHORT).show()
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card 4: Sign Out (Pink text card)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFF201B3B))
                .border(1.dp, Color(0xFF332B5E), RoundedCornerShape(18.dp))
                .clickable { showSignOutDialog = true }
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sign Out",
                color = HotPink,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }

    if (showSignOutDialog) {
        AlertDialog(
            onDismissRequest = { showSignOutDialog = false },
            containerColor = Color(0xFF1E1B38),
            title = {
                Text(
                    text = "Sign Out",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to sign out of PK LIVE?",
                    color = Color.LightGray
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSignOutDialog = false
                        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HotPink)
                ) {
                    Text("Sign Out", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showSignOutDialog = false }) {
                    Text("Cancel", color = Color.White)
                }
            }
        )
    }
}

@Composable
private fun SettingsCard(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF201B3B))
            .border(1.dp, Color(0xFF332B5E), RoundedCornerShape(18.dp))
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Column {
            content()
        }
    }
}

@Composable
private fun SettingsItemRow(
    title: String,
    valueText: String? = null,
    valueBadge: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            valueBadge?.invoke()
            if (valueText != null) {
                Text(
                    text = valueText,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}
