package ru.s.pizza.models.food

class DrinkConfigurationStrategy (var drink: Drink) :
    IConfigurationStrategy {
    override fun getPrice(str: String): String {
        var i = 0
        when (str) {
            "0.5" -> i = 0
            "1" -> i = 1
            "1.5" -> i = 2
            "2" -> i = 3
        }
        return "Добавить в корзину за ${drink.prices[i]} руб."
    }
}