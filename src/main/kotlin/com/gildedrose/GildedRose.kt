package com.gildedrose

class GildedRose(var items: List<Item>) {

    private fun updateQualityAndSellIn(item: Item, qualityChange: Int) {
        item.quality += qualityChange
        if (item.quality > 50) item.quality = 50
        if (item.quality < 0) item.quality = 0

        item.sellIn--
    }

    private fun setQualityAndDecrSellIn(item: Item, quality: Int) {
        item.quality = quality
        item.sellIn--
    }

    fun updateQuality() {
        /*
        * Assumes "Conjured" can be combined with all other items, but other special items
        * (Ex. aged brie, sulfuras, backstage passes) cannot combine with each other
        * */
        for (item in items) {

            val conjured = item.name.contains("conjured", ignoreCase = true)
            val conjuredMultiplier = if(conjured) 2 else 1
            val expired = item.sellIn <= 0
            val expiredMultiplier = if (expired) 2 else 1
            val multiplier = conjuredMultiplier * expiredMultiplier

            if (item.name.contains("aged brie", ignoreCase = true)) {
                updateQualityAndSellIn(item, 1 * multiplier)
            } else if (item.name.contains("sulfuras", ignoreCase = true)) {
                // Do nothing
            } else if (item.name.contains("backstage passes", ignoreCase = true)) {
                if (expired) {
                    setQualityAndDecrSellIn(item, 0)
                } else {
                    var increment = 0
                    if (item.sellIn > 10) increment = 1
                    else if (item.sellIn > 5) increment = 2
                    else if (item.sellIn > 0) increment = 3
                    updateQualityAndSellIn(item, increment * multiplier)
                }
            } else {
                updateQualityAndSellIn(item, -1 * multiplier)
            }
        }
    }

}

