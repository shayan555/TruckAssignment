package com.example.truckassignment.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Data(
    val breakdown: Boolean,
    val companyId: Int,
    val createTime: Long,
    val deactivated: Boolean,
    val durationInsideSite: Int,
    val externalTruck: Boolean,
    val fuelSensorInstalled: Boolean,
    val id: Int,
    val imeiNumber: String?,
    val lastRunningState: LastRunningState,
    val lastWaypoint: LastWaypoint,
    val name: String?,
    val password: String?,
    val simNumber: String?,
    val trackerType: Int,
    val transporterId: Int,
    val truckNumber: String?,
    val truckSizeId: Int,
    val truckTypeId: Int
):Parcelable,Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString(),
        TODO("lastRunningState"),
        TODO("lastWaypoint"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (breakdown) 1 else 0)
        parcel.writeInt(companyId)
        parcel.writeLong(createTime)
        parcel.writeByte(if (deactivated) 1 else 0)
        parcel.writeInt(durationInsideSite)
        parcel.writeByte(if (externalTruck) 1 else 0)
        parcel.writeByte(if (fuelSensorInstalled) 1 else 0)
        parcel.writeInt(id)
        parcel.writeString(imeiNumber)
        parcel.writeString(name)
        parcel.writeString(password)
        parcel.writeString(simNumber)
        parcel.writeInt(trackerType)
        parcel.writeInt(transporterId)
        parcel.writeString(truckNumber)
        parcel.writeInt(truckSizeId)
        parcel.writeInt(truckTypeId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}