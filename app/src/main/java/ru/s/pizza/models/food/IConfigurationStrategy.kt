package ru.s.pizza.models.food

interface IConfigurationStrategy {
    fun getPrice (str: String): String
}