package com.example.shoebox.data.api

import com.example.shoebox.R
import com.example.shoebox.data.shoeModel.Shoe
import kotlinx.coroutines.delay

class MockShoeApi {
    suspend fun getShoes(): List<Shoe> {
        delay(2000) // api response time delay
        return listOf(
            Shoe(
                "1", "Oxford Formal", 1229.99,
                "Featuring one or two buckles instead of laces, monk strap shoes add a touch of sophistication. They are perfect for those looking to stand out while still adhering to formal dress codes.", R.drawable.shoe1
            ),
            Shoe(
                "2", "Derby Formal", 899.99,
                "Known for their open lacing, Derby shoes offer a more relaxed fit compared to Oxfords. They are versatile and can be dressed up or down, making them ideal for semi-formal events.", R.drawable.shoe2
            ),
            Shoe(
                "3", "Monk Formal Pro", 1279.99,
                "Timeless and classic, Oxfords are a staple in any man's wardrobe. They are characterized by their closed lacing system, making them suitable for formal occasions. Pair them with a suit for a polished look.", R.drawable.shoe3
            ),
            Shoe(
                "4", "Formal Elite", 1149.99,
                "These shoes are distinguished by their decorative perforations. Brogues can be found in various styles, including Oxfords and Derbys, making them versatile for different occasions.", R.drawable.shoe4
            ),
            Shoe(
                "5", "Classic Loafers", 1099.99,
                "provide ease and comfort without sacrificing style. They are great for casual office environments or social gatherings where a smart-casual look is acceptable.", R.drawable.shoe5
            ),
            Shoe(
                "6", "Summer Formal", 949.99,
                "A playful twist on traditional loafers, tassel loafers feature decorative tassels on the front. They work well with both formal and casual attire.", R.drawable.shoe6
            ),
            Shoe(
                "7", "Slip-on Formal ", 998.99,
                "Easy to wear and stylish, slip-on shoes are perfect for quick outings or casual office settings. They often come in leather or synthetic materials for added comfort.", R.drawable.shoe7
            ),
            Shoe(
                "8", "Formal Cleats", 1139.99,
                "While technically a boot, Chelsea boots can be worn with formal attire. Their sleek design and elastic side panels make them easy to slip on and off.", R.drawable.shoe8
            ),
            Shoe(
                "9", "Ballet Formal", 1269.99,
                "Ideal for formal events like weddings or galas, patent leather shoes have a shiny finish that elevates any outfit.", R.drawable.shoe9
            ),
            Shoe(
                "10", "Chukka Boots", 1699.99,
                "These ankle-length boots are versatile enough to be worn with both formal and casual outfits, offering a more relaxed alternative to traditional dress shoes.", R.drawable.shoe10
            ),
            Shoe(
                "11", "Cap-Toe Shoes", 729.99,
                "Featuring a stitched cap over the toe area, these shoes add an element of sophistication and are often found in Oxfords and Derbys.", R.drawable.shoe11
            ),
            Shoe(
                "12", "Double Monk Straps", 1529.99,
                "A variation of the monk strap, double monk straps offer two buckles for added style and security, making them suitable for both business and formal events.", R.drawable.shoe12
            ),
            Shoe(
                "13", "Brogue Boots", 1339.99,
                "Combining the classic brogue detailing with boot styling, these shoes provide both warmth and style during colder months while maintaining a formal appearance.", R.drawable.shoe13
            ),
            Shoe(
                "14", "Formal Sneakers", 1519.99,
                "For those who prefer comfort without compromising style, formal sneakers offer a modern take on traditional dress shoes while still looking polished.", R.drawable.shoe14
            ),
            Shoe(
                "15", "Dress Sandals", 2529.99,
                "While less common in formal settings, dress sandals can be appropriate in certain climates or casual outdoor events where traditional shoes may not be suitable.", R.drawable.shoe15
            )
        )
    }
}