package com.example.superheroeswiki.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HeroAppearanceData(
    @SerializedName("gender") val gender: String,
    @SerializedName("race") val race: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(gender)
        parcel.writeString(race)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HeroAppearanceData> {
        override fun createFromParcel(parcel: Parcel): HeroAppearanceData {
            return HeroAppearanceData(parcel)
        }

        override fun newArray(size: Int): Array<HeroAppearanceData?> {
            return arrayOfNulls(size)
        }
    }

}
