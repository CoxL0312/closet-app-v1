package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String = "Empty",

    @ColumnInfo(name = "style")
    val style: String = "Empty",

    @ColumnInfo(name = "material")
    val material: String = "Empty",

    @ColumnInfo(name = "color")
    val color: String = "Empty",

    @ColumnInfo(name = "comfort")
    val comfort: Int = 5,

    @ColumnInfo(name = "occasion")
    val occasion: String = "Empty"
)