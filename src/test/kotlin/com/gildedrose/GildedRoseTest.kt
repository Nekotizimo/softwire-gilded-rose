package com.gildedrose

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun normalUnexpiredItemDegrades() {
        val items = listOf(
            Item("Apples", 10, 50),
            Item("Passes", 3, 20),
            Item("Oranges", 1, 0)
        )
        val app = GildedRose(items)
        app.updateQuality()
        val expected = listOf(
            Item("Apples", 9, 49),
            Item("Passes", 2, 19),
            Item("Oranges", 0, 0)
        )
        for (i in expected.indices) {
            assertEquals(expected[i].toString(), app.items[i].toString())
        }
    }

    @Test
    fun normalExpiredItemDegrades() {
        val items = listOf(
            Item("Apples", 0, 50),
            Item("Passes", -2, 1),
            Item("Oranges", -100, 0)
        )
        val app = GildedRose(items)
        app.updateQuality()
        val expected = listOf(
            Item("Apples", -1, 48),
            Item("Passes", -3, 0),
            Item("Oranges", -101, 0)
        )
        for (i in expected.indices) {
            assertEquals(expected[i].toString(), app.items[i].toString())
        }
    }

    @Test
    fun doNotDegradeSulfuras() {
        val items = listOf(
            Item("Sulfuras, Hand of Ragnaros", 0, 80), // TODO: match whole name?
            Item("Sulfuras, Hand of Ragnaros", 10, 80),
        )
        val app = GildedRose(items)
        app.updateQuality()
        val expected = listOf(
            Item("Sulfuras, Hand of Ragnaros", 0, 80),
            Item("Sulfuras, Hand of Ragnaros", 10, 80),
        )
        for (i in expected.indices) {
            assertEquals(expected[i].toString(), app.items[i].toString())
        }
    }

    @Test
    fun agedBrieIncreasesInQuality() {
        val items = listOf(
            Item("Aged Brie", 0, 0),
            Item("Aged Brie", 5, 25),
            Item("Aged Brie", 10, 50) // TODO: want case sensitive?
        )
        val app = GildedRose(items)
        app.updateQuality()
        val expected = listOf(
            Item("Aged Brie", -1, 2), // TODO: should it +2 after expired?
            Item("Aged Brie", 4, 26),
            Item("Aged Brie", 9, 50),
        )
        for (i in expected.indices) {
            assertEquals(expected[i].toString(), app.items[i].toString())
        }
    }

    @Test
    fun backstagePassesIncreasesOneMoreThanTenDays() {
        val items = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 11, 0),
            Item("Backstage passes to a TAFKAL80ETC concert", 15, 50),
        )
        val app = GildedRose(items)
        app.updateQuality()
        val expected = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 10, 1),
            Item("Backstage passes to a TAFKAL80ETC concert", 14, 50),
        )
        for (i in expected.indices) {
            assertEquals(expected[i].toString(), app.items[i].toString())
        }
    }

    @Test
    fun backstagePassesIncreasesTwoLessThanTenDays() {
        val items = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 10, 3),
            Item("Backstage passes to a TAFKAL80ETC concert", 6, 34),
        )
        val app = GildedRose(items)
        app.updateQuality()
        val expected = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 9, 5),
            Item("Backstage passes to a TAFKAL80ETC concert", 5, 36),
        )
        for (i in expected.indices) {
            assertEquals(expected[i].toString(), app.items[i].toString())
        }
    }

    @Test
    fun backstagePassesIncreasesThreeLessThanFiveDays() {
        val items = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 5, 3),
            Item("Backstage passes to a TAFKAL80ETC concert", 1, 34),
        )
        val app = GildedRose(items)
        app.updateQuality()
        val expected = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 4, 6),
            Item("Backstage passes to a TAFKAL80ETC concert", 0, 37),
        )
        for (i in expected.indices) {
            assertEquals(expected[i].toString(), app.items[i].toString())
        }
    }

    @Test
    fun backstagePassesExpiredResets() {
        val items = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 0, 5),
        )
        val app = GildedRose(items)
        app.updateQuality()
        val expected = listOf(
            Item("Backstage passes to a TAFKAL80ETC concert", -1, 0),
        )
        for (i in expected.indices) {
            assertEquals(expected[i].toString(), app.items[i].toString())
        }
    }

    @Test
    fun conjuredItemsDegradesTwice() {
        val items = listOf(
            Item("Conjured", 10, 50), // TODO: match whole word?
            Item("Conjured Potion", 1, 20),
            Item("conjured cocoa", 0, 10)
        )
        val app = GildedRose(items)
        app.updateQuality()
        val expected = listOf(
            Item("Conjured", 9, 48),
            Item("Conjured Potion", 0, 18),
            Item("conjured cocoa", -1, 6)
        )
        for (i in expected.indices) {
            assertEquals(expected[i].toString(), app.items[i].toString())
        }
    }
}


