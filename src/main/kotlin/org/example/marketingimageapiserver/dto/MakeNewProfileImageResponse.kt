package org.example.marketingimageapiserver.dto

data class MakeNewProfileImageResponse(
    val success: Boolean,
    val message: String,
    val saveFileResult: SaveFileResult?
) {
    companion object {
        fun of() {}
    }
}