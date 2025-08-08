package com.garaninsoft.chitabonus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.garaninsoft.chitabonus.ui.screens.addbook.AddBookScreen
import com.garaninsoft.chitabonus.ui.screens.booklist.BookListScreen
import com.garaninsoft.chitabonus.ui.screens.review.ReviewScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.BookList.route) {
        composable(NavigationRoute.BookList.route) {
            BookListScreen(
                onAddBookClick = { navController.navigate(NavigationRoute.AddBook.route) },
                onReviewClick = { bookId -> navController.navigate(NavigationRoute.Review.createRoute(bookId)) }
            )
        }
        composable(NavigationRoute.AddBook.route) {
            AddBookScreen(onBack = { navController.popBackStack() })
        }
        composable(NavigationRoute.Review.route) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")
            ReviewScreen(onBack = { navController.popBackStack() }, bookId = bookId)
        }
    }
}
