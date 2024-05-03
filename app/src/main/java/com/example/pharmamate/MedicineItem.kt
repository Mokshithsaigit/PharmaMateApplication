package com.example.pharmamate

import android.os.Parcel
import android.os.Parcelable

data class MedicineItem(
    val uniqueId: String?,
    val name: String?,
    val price: Int,
    val quantity: Int,
    val qty : Int,
    val mfgDate: String?,
    val expDate: String?,
    val amount : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uniqueId)
        parcel.writeString(name)
        parcel.writeInt(price)
        parcel.writeInt(quantity)
        parcel.writeInt(qty)
        parcel.writeString(mfgDate)
        parcel.writeString(expDate)
        parcel.writeInt(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MedicineItem> {
        override fun createFromParcel(parcel: Parcel): MedicineItem {
            return MedicineItem(parcel)
        }

        override fun newArray(size: Int): Array<MedicineItem?> {
            return arrayOfNulls(size)
        }
    }

}