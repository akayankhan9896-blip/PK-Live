package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserProfile
import com.example.ui.theme.BgDark
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

@Composable
fun HostCenterScreen(
    userProfile: UserProfile,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var payoutRequested by remember { mutableStateOf(false) }

    fun copyText(text: String, label: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
        Toast.makeText(context, "$label copied", Toast.LENGTH_SHORT).show()
    }

    val rtmpUrl = "rtmp://live.pk-live.web.app/live/${userProfile.id}"
    val streamKey = "live_pk_${userProfile.id}_secret"

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 40.dp)
    ) {
        // Top App Bar
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
                text = "Host Center & Income",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        // Host Summary Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF7E22CE), Color(0xFFC026D3))
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = userProfile.username,
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Agency: ${userProfile.agencyName}",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black.copy(alpha = 0.35f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Level ${userProfile.userLevel} Host",
                            color = NeonGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricMini(label = "Daily Hours", value = "${userProfile.liveHoursToday}h")
                    MetricMini(label = "Valid Days", value = "14 / 22")
                    MetricMini(label = "Monthly Rank", value = "#${userProfile.monthlyRank}")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Diamonds & Income Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1B1833))
                .border(1.dp, Color(0xFF2E2B4E), RoundedCornerShape(20.dp))
                .padding(18.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Diamond,
                        contentDescription = "Diamonds",
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Diamonds Balance & Earnings",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${userProfile.diamonds} Diamonds",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp
                        )
                        Text(
                            text = "≈ Rs ${(userProfile.diamonds * 5)} PKR ($${userProfile.diamonds / 56} USD)",
                            color = NeonGold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }

                    Button(
                        onClick = {
                            payoutRequested = true
                            Toast.makeText(context, "Withdrawal request submitted to Agency!", Toast.LENGTH_LONG).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = if (payoutRequested) "Pending" else "Withdraw",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Payout available via Easypaisa, JazzCash, or direct bank transfer on the 1st & 15th of every month.",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Broadcast RTMP Stream Credentials Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1B1833))
                .border(1.dp, Color(0xFF2E2B4E), RoundedCornerShape(20.dp))
                .padding(18.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.VpnKey,
                        contentDescription = "RTMP",
                        tint = NeonPurple,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "RTMP Live Streaming Config",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // RTMP Server URL
                Text(text = "Server URL (RTMP):", color = Color.Gray, fontSize = 11.sp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF231F42))
                        .clickable { copyText(rtmpUrl, "RTMP URL") }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = rtmpUrl,
                        color = Color(0xFF67E8F9),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = Color.LightGray,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Stream Key
                Text(text = "Stream Key:", color = Color.Gray, fontSize = 11.sp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF231F42))
                        .clickable { copyText(streamKey, "Stream Key") }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "••••••••••••••••••••",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = Color.LightGray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricMini(label: String, value: String) {
    Column {
        Text(text = label, color = Color.White.copy(alpha = 0.75f), fontSize = 11.sp)
        Text(text = value, color = Color.White, fontWeight = FontWeight.Black, fontSize = 16.sp)
    }
}
