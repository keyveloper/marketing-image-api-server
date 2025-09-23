package org.example.marketingimageapiserver.enums

enum class ProfileImageType(val code: Int) {
    BACKGROUND(1),
    PROFILE(2);

    companion object {

        private val valueToCodeMap: Map<String, Int> = entries.associate { it.name to it.code}
        private val codeToEnumMap: Map<Int, ProfileImageType> = entries.associateBy { it.code }

        fun getByCode(code: Int): ProfileImageType? {
            return codeToEnumMap[code]
        }

        fun getCodeByValue(value: String): Int? {
            return valueToCodeMap[value]
        }
    }
}