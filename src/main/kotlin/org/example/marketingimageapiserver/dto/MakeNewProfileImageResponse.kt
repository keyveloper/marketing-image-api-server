package org.example.marketingimageapiserver.dto

data class MakeNewProfileImageResponse(
    val saveFileResult: SaveFileResult?
) {
    companion object {
        fun of(
            result: SaveFileResult
        ): MakeNewProfileImageResponse {
            return MakeNewProfileImageResponse(
                saveFileResult = result
            )
        }
    }
}