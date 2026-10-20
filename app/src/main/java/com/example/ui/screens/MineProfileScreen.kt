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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserProfile
import com.example.ui.components.GenderAgeBadge
import com.example.ui.components.HeartBadge
import com.example.ui.components.LevelBadge
import com.example.ui.components.VipBadge
import com.example.ui.theme.BgDark
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

data class MenuItemData(
    val title: String,
    val icon: ImageVector,
    val iconTint: Color,
    val actionType: String
)

@Composable
fun MineProfileScreen(
    userProfile: UserProfile,
    onNavigateSettings: () -> Unit,
    onNavigateHostCenter: () -> Unit,
    onNavigateAiStudio: () -> Unit,
    onRechargeClick: () -> Unit,
    onVipCenterClick: () -> Unit,
    onMyRoomClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    fun copyId() {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("User ID", userProfile.id))
        Toast.makeText(context, "ID copied: ${userProfile.id}", Toast.LENGTH_SHORT).show()
    }

    val menuItems = listOf(
        MenuItemData("VIP Center", Icons.Default.Star, Color(0xFFFBBF24), "VIP"),
        MenuItemData("Noble", Icons.Default.MilitaryTech, Color(0xFFF59E0B), "NOBLE"),
        MenuItemData("Level", Icons.Default.EmojiEvents, Color(0xFF10B981), "LEVEL"),
        MenuItemData("Group", Icons.Default.Group, Color(0xFFA855F7), "GROUP"),
        MenuItemData("Relationship", Icons.Default.Favorite, Color(0xFFEC4899), "RELATION"),
        MenuItemData("Store", Icons.Default.Storefront, Color(0xFFF97316), "STORE"),
        MenuItemData("Backpack", Icons.Default.ShoppingBag, Color(0xFF8B5CF6), "BACKPACK"),
        MenuItemData("Game Center", Icons.Default.SportsEsports, Color(0xFF3B82F6), "GAME"),
        MenuItemData("LUDO", Icons.Default.Redeem, Color(0xFF06B6D4), "LUDO"),
        MenuItemData("AI Studio", Icons.Default.Palette, Color(0xFFD946EF), "AI_STUDIO"),
        MenuItemData("Wallet", Icons.Default.MonetizationOn, Color(0xFFEAB308), "WALLET"),
        MenuItemData("Settings", Icons.Default.Settings, Color(0xFF94A3B8), "SETTINGS")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Header Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Settings quick gear button row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E1C38))
                            .clickable(onClick = onNavigateSettings),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Avatar + Name + Badges row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .border(
                                2.5.dp,
                                Brush.linearGradient(listOf(NeonPurple, NeonPink, NeonGold)),
                                CircleShape
                            )
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF6366F1), Color(0xFFEC4899))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "PK",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 26.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        // Username
                        Text(
                            text = userProfile.username,
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Badges Row: male 21, VIP1, 30, heart 3
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GenderAgeBadge(gender = userProfile.gender, age = userProfile.age)
                            VipBadge(level = userProfile.vipLevel)
                            LevelBadge(level = userProfile.userLevel)
                            HeartBadge(level = userProfile.heartLevel)
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // ID with copy icon
                        Row(
                            modifier = Modifier.clickable { copyId() },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ID: ${userProfile.id}",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy ID",
                                tint = Color.LightGray,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Row: 18 Friends, 20 Following, 19 Follower
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    StatCount(count = "${userProfile.friendsCount}", label = "Friends")
                    StatCount(count = "${userProfile.followingCount}", label = "Following")
                    StatCount(count = "${userProfile.followersCount}", label = "Follower")
                }
            }
        }

        // Coins and Diamonds Cards
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Xcoins Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF1B1833))
                        .border(1.dp, Color(0xFF2E2B4E), RoundedCornerShape(16.dp))
                        .clickable(onClick = onRechargeClick)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MonetizationOn,
                                    contentDescription = "Xcoins",
                                    tint = NeonGold,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Xcoins",
                                    color = Color.LightGray,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${userProfile.xcoins}",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(NeonPink),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Recharge",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                // Diamonds Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF1B1833))
                        .border(1.dp, Color(0xFF2E2B4E), RoundedCornerShape(16.dp))
                        .clickable(onClick = onNavigateHostCenter)
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Diamond,
                                contentDescription = "Diamonds",
                                tint = Color(0xFF38BDF8),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Diamonds",
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${userProfile.diamonds}",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        // Purple Gradient Card: Host Center - Live data & Income details
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF7E22CE), Color(0xFF9333EA), Color(0xFFC026D3))
                        )
                    )
                    .clickable(onClick = onNavigateHostCenter)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LiveTv,
                                contentDescription = "Host Center",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Host Center",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Live data & Income details",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = "Open Host Center",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Grid Menu 4x3
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF1B1833))
                    .border(1.dp, Color(0xFF2E2B4E), RoundedCornerShape(20.dp))
                    .padding(vertical = 12.dp)
            ) {
                val rows = menuItems.chunked(4)
                rows.forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        rowItems.forEach { item ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clickable {
                                        when (item.actionType) {
                                            "SETTINGS" -> onNavigateSettings()
                                            "WALLET" -> onRechargeClick()
                                            "AI_STUDIO" -> onNavigateAiStudio()
                                            "VIP", "STORE" -> onVipCenterClick()
                                            else -> {
                                                Toast.makeText(context, "${item.title} opened", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                    .padding(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(item.iconTint.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        tint = item.iconTint,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.title,
                                    color = Color.LightGray,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // My Room Card at bottom
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFF1E1C38))
                    .border(1.dp, Color(0xFF332F5C), RoundedCornerShape(18.dp))
                    .clickable(onClick = onMyRoomClick)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF8B5CF6).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Radio,
                                contentDescription = null,
                                tint = NeonPurple,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "My Personal Room",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Tap to launch your broadcaster lounge",
                                color = Color.Gray,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCount(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}
