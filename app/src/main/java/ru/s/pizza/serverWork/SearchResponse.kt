package ru.s.pizza.serverWork

data class PizzaData(
    val id: Int,
    val name: String,
    val prices: String,
    val weights: String,
    val ingredient: String,
    val picture: String
)

data class DessertData(
    val id: Int,
    val name: String,
    val price: String,
    val ingredient: String,
    val count: String,
    val weight: String,
    val picture: String
)

data class DrinkData(
    val id: Int,
    val name: String,
    val prices: String,
    val volumes: String,
    val picture: String
)