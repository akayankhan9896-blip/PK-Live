package com.example.ui.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.SoundEffects
import com.example.model.RechargePackage
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

val DefaultRechargePackages = listOf(
    RechargePackage(pkrAmount = 100, coinsAmount = 5000),
    RechargePackage(pkrAmount = 200, coinsAmount = 11000, bonusCoins = 1000, isPopular = true),
    RechargePackage(pkrAmount = 500, coinsAmount = 30000, bonusCoins = 5000),
    RechargePackage(pkrAmount = 1000, coinsAmount = 65000, bonusCoins = 15000)
)

const val OFFICIAL_PAYMENT_NUMBER = "03214585825"

@Composable
fun RechargeDialog(
    currentCoins: Long,
    onRechargeSuccess: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedPackage by remember { mutableStateOf(DefaultRechargePackages[1]) }
    var selectedMethod by remember { mutableStateOf("Easypaisa") }
    var transactionId by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    fun copyToClipboard(text: String, label: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "$label copied: $text", Toast.LENGTH_SHORT).show()
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFF161329),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF2E2B4E), RoundedCornerShape(24.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                if (showSuccess) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Recharge Successful!",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "+${selectedPackage.coinsAmount} coins credited to wallet!",
                            color = NeonGold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Done", color = Color.White)
                        }
                    }
                } else {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Coin Recharge",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "Current Balance: $currentCoins coins",
                                color = NeonGold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Packages Grid (2x2)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            PackageItem(
                                pkg = DefaultRechargePackages[0],
                                isSelected = selectedPackage == DefaultRechargePackages[0],
                                modifier = Modifier.weight(1f),
                                onClick = { selectedPackage = DefaultRechargePackages[0] }
                            )
                            PackageItem(
                                pkg = DefaultRechargePackages[1],
                                isSelected = selectedPackage == DefaultRechargePackages[1],
                                modifier = Modifier.weight(1f),
                                onClick = { selectedPackage = DefaultRechargePackages[1] }
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            PackageItem(
                                pkg = DefaultRechargePackages[2],
                                isSelected = selectedPackage == DefaultRechargePackages[2],
                                modifier = Modifier.weight(1f),
                                onClick = { selectedPackage = DefaultRechargePackages[2] }
                            )
                            PackageItem(
                                pkg = DefaultRechargePackages[3],
                                isSelected = selectedPackage == DefaultRechargePackages[3],
                                modifier = Modifier.weight(1f),
                                onClick = { selectedPackage = DefaultRechargePackages[3] }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Payment Method selector (Easypaisa / JazzCash)
                    Text(
                        text = "Payment Method:",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        PaymentMethodTab(
                            name = "Easypaisa",
                            selected = selectedMethod == "Easypaisa",
                            modifier = Modifier.weight(1f),
                            onClick = { selectedMethod = "Easypaisa" }
                        )
                        PaymentMethodTab(
                            name = "JazzCash",
                            selected = selectedMethod == "JazzCash",
                            modifier = Modifier.weight(1f),
                            onClick = { selectedMethod = "JazzCash" }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Payment Account details card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF221F3D))
                            .border(1.dp, Color(0xFF373264), RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.PhoneAndroid,
                                        contentDescription = null,
                                        tint = NeonPink,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "$selectedMethod Account",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF332E5D))
                                        .clickable {
                                            copyToClipboard(OFFICIAL_PAYMENT_NUMBER, "$selectedMethod Number")
                                        }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            tint = NeonGold,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = "Copy", color = NeonGold, fontSize = 11.sp)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = OFFICIAL_PAYMENT_NUMBER,
                                color = NeonGold,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Account Title: PK LIVE OFFICIAL",
                                color = Color.LightGray,
                                fontSize = 11.sp
                            )
                            Text(
                                text = "Pay Rs ${selectedPackage.pkrAmount} and enter TRX ID below",
                                color = Color(0xFFA78BFA),
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // TRX ID field
                    OutlinedTextField(
                        value = transactionId,
                        onValueChange = { transactionId = it },
                        label = { Text("Transaction ID / TID (e.g. 81928374)", fontSize = 11.sp) },
                        placeholder = { Text("Enter payment TID", fontSize = 11.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonPurple,
                            unfocusedBorderColor = Color(0xFF373264),
                            focusedLabelColor = NeonPurple,
                            unfocusedLabelColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit button
                    Button(
                        onClick = {
                            SoundEffects.playCoinSound(context)
                            onRechargeSuccess(selectedPackage.coinsAmount)
                            showSuccess = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Submit & Add ${selectedPackage.coinsAmount} Coins",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PackageItem(
    pkg: RechargePackage,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) Color(0xFF2E2452) else Color(0xFF1E1C38))
            .border(
                width = if (isSelected) 1.5.dp else 0.5.dp,
                color = if (isSelected) NeonPink else Color(0xFF373264),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        Column {
            if (pkg.isPopular) {
                Text(
                    text = "HOT",
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(NeonPink)
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
            Text(
                text = "Rs ${pkg.pkrAmount}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.MonetizationOn,
                    contentDescription = null,
                    tint = NeonGold,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "${pkg.coinsAmount}",
                    color = NeonGold,
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun PaymentMethodTab(
    name: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) NeonPurple else Color(0xFF221F3D))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}
