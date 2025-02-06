package com.example.shoebox.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoebox.domain.CartViewModel
import com.example.shoebox.domain.ShoeViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class, ExperimentalAnimationApi::class)
@Composable
fun ShoeAppNavigation(
    shoeViewModel: ShoeViewModel,
    cartViewModel: CartViewModel
) {
    val navController = rememberNavController()
    val shoes by shoeViewModel.shoes.collectAsState()

    AnimatedNavHost(
        navController = navController,
        startDestination = "listing",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        composable("listing") {
            ShoeListingScreen(
                viewModel = shoeViewModel,
                cartViewModel = cartViewModel,
                onShoeSelected = { shoe ->
                    navController.navigate("details/${shoe.id}"){
                        launchSingleTop = true
                    }
                },
                onCartClick = { navController.navigate("cart"){
                    launchSingleTop = true
                } }
            )
        }
        composable(
            "details/{shoeId}",
            arguments = listOf(navArgument("shoeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val shoeId = backStackEntry.arguments?.getString("shoeId")
            val shoe = shoes.find { it.id == shoeId }

            ShoeDetailsScreen(
                shoe = shoe ?: return@composable,
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onCartClick = {
                    navController.navigate("cart"){
                        popUpTo("details/{shoeId}") { inclusive = true }
                    }
                }
            )
        }
        composable("cart") {
            CartScreen(
                cartViewModel = cartViewModel,
                onBackClick = { navController.popBackStack() },
                onShoeClick = { shoe ->
                    navController.navigate("details/${shoe.id}"){
                        popUpTo("cart") { inclusive = true }
                    }
                }
            )
        }
    }
}
