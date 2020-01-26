package pl.robert.server.user

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired

import pl.robert.server.user.domain.UserService
import pl.robert.server.user.domain.dto.CreateUserDto
import pl.robert.server.user.domain.dto.UpdateUserAgeDto


@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/user")
class UserController @Autowired constructor(val service: UserService) {

    @PostMapping("/save")
    fun save(@RequestBody dto: CreateUserDto) = ResponseEntity.ok(service.save(dto))

    @GetMapping
    fun getAll() = ResponseEntity.ok(service.getAll())

    @PutMapping
    fun updateAge(@RequestBody dto: UpdateUserAgeDto) = ResponseEntity.ok(service.updateAge(dto.uuid, dto.newAge))

    @DeleteMapping("{uuid}")
    fun deleteByUuid(@PathVariable(name = "uuid") uuid: String) = ResponseEntity.ok(service.deleteByUuid(uuid))

    @DeleteMapping
    fun deleteAll() = ResponseEntity.ok(service.deleteAll())
}