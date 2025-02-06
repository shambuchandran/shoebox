package com.example.shoebox.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shoebox.data.shoeModel.Shoe
import com.example.shoebox.domain.CartViewModel
import com.example.shoebox.domain.ShoeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoeListingScreen(
    viewModel: ShoeViewModel,
    cartViewModel: CartViewModel,
    onShoeSelected: (Shoe) -> Unit,
    onCartClick: () -> Unit
) {
    val shoes by viewModel.shoes.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shoe Box") },
                actions = {
                    IconButton(onClick = onCartClick, modifier = Modifier.wrapContentSize()) {
                        BadgedBox(badge = {
                            if (cartItems.isNotEmpty()) Text(
                                cartItems.size.toString(),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .wrapContentSize(),
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        }) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (shoes.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(shoes) { shoe ->
                    ShoeItem(shoe, onShoeSelected)
                }
            }
        }
    }
}

@Composable
private fun ShoeItem(shoe: Shoe, onShoeSelected: (Shoe) -> Unit) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.8f),
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { onShoeSelected(shoe) },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = shoe.imageId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    shoe.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "$${shoe.price}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Blue
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoeDetailsScreen(
    shoe: Shoe,
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onCartClick: () -> Unit
) {
    val snackBarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val isInCart = remember(cartItems) {
        cartItems.any { it.shoe.id == shoe.id }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(shoe.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = shoe.imageId),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = shoe.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$${shoe.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = shoe.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(24.dp))
//            Button(onClick = { cartViewModel.addToCart(shoe)
//            coroutineScope.launch {
//                snackBarState.showSnackbar("${shoe.name} added to cart")
//            }
//            }) {
//                Icon(Icons.Default.ShoppingCart, contentDescription = "Add to Cart")
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Add to Cart")
//            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        cartViewModel.addToCart(shoe)
                        coroutineScope.launch {
                            snackBarState.showSnackbar("${shoe.name} added to cart")
                        }
                    }
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Add to Cart")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add to Cart")
                }
                if (isInCart) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = onCartClick
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Go to Cart")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Go to Cart")
                    }
                }
            }
        }
    }
}
