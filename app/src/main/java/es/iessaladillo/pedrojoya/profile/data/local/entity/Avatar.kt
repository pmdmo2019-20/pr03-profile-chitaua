package es.iessaladillo.pedrojoya.profile.data.local.entity

import android.os.Parcel
import android.os.Parcelable

// TODO: Avatar class
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class Avatar(val id: Long, val imageResId: Int, val name: String) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<Avatar> {
        override fun createFromParcel(parcel: Parcel): Avatar {
            return Avatar(parcel)
        }

        override fun newArray(size: Int): Array<Avatar?> {
            return arrayOfNulls(size)
        }
    }

}