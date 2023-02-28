package io.freevariable.auktion.controller

import io.freevariable.auktion.repository.OfferRepository
import io.freevariable.auktion.service.OfferService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.RepositoryRestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@RepositoryRestController
class OfferController {

    @Autowired
    lateinit var offerService: OfferService

    data class CloseOfferRequest(val password: String, val selectedBid: Long)

    @PutMapping("/offers/{id}/close")
    fun closeOffer(@RequestBody request: CloseOfferRequest, @PathVariable("id") offerId: Long): ResponseEntity<String> {
        offerService.closeOffer(offerId, request.password, request.selectedBid)
        return ResponseEntity.ok().body("Offer Closed!")
    }
}