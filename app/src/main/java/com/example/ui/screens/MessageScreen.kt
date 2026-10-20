package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChatConversation
import com.example.ui.components.GenderAgeBadge
import com.example.ui.components.OfficialBadge
import com.example.ui.components.VipBadge
import com.example.ui.theme.BgDark
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

@Composable
fun MessageScreen(
    messages: List<ChatConversation>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredMessages = if (selectedFilter == "All") {
        messages
    } else {
        messages.filter { it.filterType == selectedFilter }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Title: Message
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Message",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp
                )
            }
        }

        // 4 Top Filter Icons: Friend request, Events, Notification, Visitors
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TopFilterIcon(
                    title = "Friend request",
                    icon = Icons.Default.PersonAdd,
                    badgeCount = 3,
                    iconBg = Color(0xFF3B82F6),
                    isSelected = selectedFilter == "Friend request",
                    onClick = {
                        selectedFilter = if (selectedFilter == "Friend request") "All" else "Friend request"
                    }
                )
                TopFilterIcon(
                    title = "Events",
                    icon = Icons.Default.Campaign,
                    badgeCount = 1,
                    iconBg = Color(0xFFEC4899),
                    isSelected = selectedFilter == "Events",
                    onClick = {
                        selectedFilter = if (selectedFilter == "Events") "All" else "Events"
                    }
                )
                TopFilterIcon(
                    title = "Notification",
                    icon = Icons.Default.Notifications,
                    badgeCount = 5,
                    iconBg = Color(0xFFF59E0B),
                    isSelected = selectedFilter == "Notification",
                    onClick = {
                        selectedFilter = if (selectedFilter == "Notification") "All" else "Notification"
                    }
                )
                TopFilterIcon(
                    title = "Visitors",
                    icon = Icons.Default.RemoveRedEye,
                    badgeCount = 12,
                    iconBg = Color(0xFF8B5CF6),
                    isSelected = selectedFilter == "Visitors",
                    onClick = {
                        selectedFilter = if (selectedFilter == "Visitors") "All" else "Visitors"
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Conversation List
        if (filteredMessages.isEmpty()) {
            item {
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
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = null,
                            tint = NeonGold,
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No Messages Yet",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Connect with broadcasters and friends in live rooms to chat!",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        } else {
            items(filteredMessages) { conv ->
                ConversationRow(
                    conversation = conv,
                    onClick = {
                        Toast.makeText(context, "Opening chat with ${conv.name}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
private fun TopFilterIcon(
    title: String,
    icon: ImageVector,
    badgeCount: Int,
    iconBg: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier.size(52.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconBg.copy(alpha = if (isSelected) 1f else 0.25f))
                    .border(
                        width = if (isSelected) 2.dp else 0.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (isSelected) Color.White else iconBg,
                    modifier = Modifier.size(24.dp)
                )
            }

            if (badgeCount > 0) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(NeonPink)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$badgeCount",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = title,
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun ConversationRow(
    conversation: ChatConversation,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        listOf(
                            if (conversation.isOfficial) Color(0xFF7C3AED) else Color(0xFF3B82F6),
                            if (conversation.isOfficial) Color(0xFFEC4899) else Color(0xFF06B6D4)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = conversation.name.take(2).uppercase(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Message body
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = conversation.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    if (conversation.isOfficial) {
                        OfficialBadge()
                    }
                    if (conversation.vipLevel > 0) {
                        Spacer(modifier = Modifier.width(4.dp))
                        VipBadge(level = conversation.vipLevel)
                    }
                    if (conversation.gender != null && conversation.age != null) {
                        Spacer(modifier = Modifier.width(4.dp))
                        GenderAgeBadge(gender = conversation.gender, age = conversation.age)
                    }
                }

                Text(
                    text = conversation.timeAgo,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = conversation.lastMessage,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                if (conversation.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(NeonPink),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${conversation.unreadCount}",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
