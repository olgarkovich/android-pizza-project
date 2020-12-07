package ru.s.pizza.models.food

import ru.s.pizza.models.food.IConfigurationStrategy
import ru.s.pizza.models.food.Pizza

class PizzaConfigurationStrategy(var pizza: Pizza) :
    IConfigurationStrategy {
    override fun getPrice(str: String): String {
        var i = 0
        when (str) {
            "little" -> i = 0
            "medium" -> i = 1
            "big" -> i = 2
        }
        return "Добавить в корзину за ${pizza.prices[i]} руб."
    }
}