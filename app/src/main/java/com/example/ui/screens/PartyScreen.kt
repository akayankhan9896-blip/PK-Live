package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Broadcaster
import com.example.model.PartyRoom
import com.example.ui.components.OfficialBadge
import com.example.ui.theme.BgDark
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

@Composable
fun PartyScreen(
    partyRooms: List<PartyRoom>,
    onRoomClick: (PartyRoom) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Official", "Music", "Games", "Dating")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Banner: AGENCY POWER CLASH
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .aspectRatio(16f / 9f)
                    .border(
                        1.5.dp,
                        Brush.horizontalGradient(listOf(Color(0xFF3B82F6), Color(0xFF8B5CF6))),
                        RoundedCornerShape(20.dp)
                    )
            ) {
                // Generated Banner Image
                Image(
                    painter = painterResource(id = R.drawable.banner_agency_clash_1792456899129),
                    contentDescription = "AGENCY POWER CLASH 11 Oct - 21 Oct",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Bottom gradient label overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color(0xFF0F0F19).copy(alpha = 0.95f))
                            )
                        )
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "AGENCY POWER CLASH",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "11 Oct - 21 Oct • Clash of Titans",
                                color = NeonGold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(NeonPink)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "JOIN NOW",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // Xena News Ticker Carousel
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF201B3B))
                    .border(1.dp, Color(0xFF332B5E), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = "News",
                        tint = NeonGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Xena News: police is back won 600,000 in lion Vs tiger!",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = cat == "All"
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) NeonPink else Color(0xFF1E1C38))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) Color.White else Color.Gray,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // Official & Featured Party Cards
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Recommended Party Rooms",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        if (partyRooms.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF1B1833))
                        .border(1.dp, Color(0xFF2E2B4E), RoundedCornerShape(20.dp))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Headphones,
                            contentDescription = null,
                            tint = NeonPurple,
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No Party Rooms Active",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Start a voice party room to chat, listen, and battle with friends!",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        } else {
            items(partyRooms) { room ->
                PartyRoomCard(
                    room = room,
                    onClick = { onRoomClick(room) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun PartyRoomCard(
    room: PartyRoom,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF1B1833))
            .border(1.dp, Color(0xFF2E2B4E), RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(NeonPurple, NeonPink)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = room.hostName.take(2).uppercase(),
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = room.title,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            if (room.isOfficial) {
                                OfficialBadge()
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Host: ${room.hostName} • ${room.category}",
                            color = Color.Gray,
                            fontSize = 11.sp
                        )
                    }
                }

                // Listener count
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF28244B))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Headphones,
                        contentDescription = "Listeners",
                        tint = NeonPink,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${room.listenerCount}",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Stacked Listener Avatars row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    room.avatars.forEachIndexed { i, initial ->
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, Color(0xFF1B1833), CircleShape)
                                .background(
                                    when (i % 3) {
                                        0 -> Color(0xFF3B82F6)
                                        1 -> Color(0xFFEC4899)
                                        else -> Color(0xFF10B981)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = initial,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${room.listenerCount} in room",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(NeonPurple.copy(alpha = 0.2f))
                        .border(1.dp, NeonPurple, RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = null,
                        tint = NeonPurple,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Join Voice",
                        color = NeonPurple,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}
