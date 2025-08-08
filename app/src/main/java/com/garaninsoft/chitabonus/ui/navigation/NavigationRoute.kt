package com.garaninsoft.chitabonus.ui.navigation

sealed class NavigationRoute(val route: String) {
    object BookList : NavigationRoute("book_list")
    object AddBook : NavigationRoute("add_book")
    object Review : NavigationRoute("review")
}