package org.example.marketingimageapiserver.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile-image-meta")
class ProfileImageMetaController {

    @GetMapping
    fun getAllProfileImageMeta(): ResponseEntity<String> {
        return ResponseEntity.ok("Get all profile image meta")
    }

    @GetMapping("/{id}")
    fun getProfileImageMetaById(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Get profile image meta with id: $id")
    }

    @PostMapping
    fun createProfileImageMeta(@RequestBody body: String): ResponseEntity<String> {
        return ResponseEntity.ok("Create profile image meta")
    }

    @PutMapping("/{id}")
    fun updateProfileImageMeta(@PathVariable id: Long, @RequestBody body: String): ResponseEntity<String> {
        return ResponseEntity.ok("Update profile image meta with id: $id")
    }

    @DeleteMapping("/{id}")
    fun deleteProfileImageMeta(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Delete profile image meta with id: $id")
    }
}