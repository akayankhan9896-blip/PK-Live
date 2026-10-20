package com.example.ui.screens

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Broadcaster
import com.example.model.PartyRoom
import com.example.ui.components.FireCountBadge
import com.example.ui.components.GenderAgeBadge
import com.example.ui.components.PkBadge
import com.example.ui.theme.BgDark
import com.example.ui.theme.NeonBlue
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

@Composable
fun LiveGridScreen(
    broadcasters: List<Broadcaster>,
    partyRooms: List<PartyRoom>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    onBroadcasterClick: (Broadcaster) -> Unit,
    onStartLiveClick: () -> Unit,
    onMyRoomClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf("Recently", "Following", "Friends")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Top action buttons: My Room (purple) + Start Live (blue)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // My Room Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9))
                                )
                            )
                            .clickable(onClick = onMyRoomClick)
                            .padding(vertical = 12.dp, horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.MeetingRoom,
                                contentDescription = "My Room",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "My Room",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    // Start Live Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFF3B82F6), Color(0xFF06B6D4))
                                )
                            )
                            .clickable(onClick = onStartLiveClick)
                            .padding(vertical = 12.dp, horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Start Live",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Start Live",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // Filter Chips (Recently active purple, Following, Friends)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filters.forEach { filter ->
                        val isSelected = filter == selectedFilter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) NeonPurple else Color(0xFF1E1C38))
                                .clickable { onFilterSelected(filter) }
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = filter,
                                color = if (isSelected) Color.White else Color.Gray,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            // Broadcaster 2-Column Grid
            item {
                if (broadcasters.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF1B1833))
                            .border(1.dp, Color(0xFF2E2B4E), RoundedCornerShape(20.dp))
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Podcasts,
                                contentDescription = null,
                                tint = NeonPink,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No Broadcasters Live Right Now",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Be the first broadcaster to go live on PK LIVE!",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(NeonBlue)
                                    .clickable(onClick = onStartLiveClick)
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = "Start Live Now",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                } else {
                    Column(modifier = Modifier.padding(12.dp)) {
                        val pairs = broadcasters.chunked(2)
                        pairs.forEach { pair ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                BroadcasterCard(
                                    broadcaster = pair[0],
                                    onClick = { onBroadcasterClick(pair[0]) },
                                    modifier = Modifier.weight(1f)
                                )
                                if (pair.size > 1) {
                                    BroadcasterCard(
                                        broadcaster = pair[1],
                                        onClick = { onBroadcasterClick(pair[1]) },
                                        modifier = Modifier.weight(1f)
                                    )
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }

            // Party Rooms Section
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(
                        text = "Voice Party Rooms",
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
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF1B1833))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No party rooms active. Join or create your first voice lounge!",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            } else {
                items(partyRooms) { room ->
                    PartyRoomRowItem(
                        room = room,
                        onClick = {
                            onBroadcasterClick(
                                Broadcaster(
                                    id = room.id,
                                    name = room.hostName,
                                    title = room.title,
                                    viewerCount = room.listenerCount,
                                    isPkActive = false
                                )
                            )
                        },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Floating Go Live FAB bottom right
        FloatingActionButton(
            onClick = onStartLiveClick,
            containerColor = NeonBlue,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 80.dp, end = 20.dp)
                .size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Podcasts,
                contentDescription = "Go Live",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun BroadcasterCard(
    broadcaster: Broadcaster,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1B1833))
            .border(1.dp, Color(0xFF2E2B4E), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Column {
            // Stream Preview Thumbnail with fire count & PK badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF312E81),
                                Color(0xFF1E1B4B),
                                Color(0xFF0F0F19)
                            )
                        )
                    )
            ) {
                // Central Broadcaster Silhouette or Initial Avatar
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(NeonPurple, NeonPink)
                            )
                        )
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = broadcaster.name.take(2).uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp
                    )
                }

                // Fire Viewer count bottom left
                FireCountBadge(
                    count = broadcaster.fireCount,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )

                // PK Badge bottom right
                if (broadcaster.isPkActive) {
                    PkBadge(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                    )
                }
            }

            // Info below image
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = broadcaster.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    GenderAgeBadge(gender = broadcaster.gender, age = broadcaster.age)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = broadcaster.title,
                        color = Color.Gray,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun PartyRoomRowItem(
    room: PartyRoom,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF1B1833))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.linearGradient(listOf(Color(0xFF7C3AED), Color(0xFFEC4899)))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = room.hostName.take(2).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = room.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    text = "Host: ${room.hostName} • ${room.category}",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }

        // Listener count badge
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF2A264F))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = NeonPink,
                modifier = Modifier.size(12.dp)
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
}
