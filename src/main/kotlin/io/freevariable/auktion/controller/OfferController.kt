package io.freevariable.auktion.controller

import io.freevariable.auktion.model.Bid
import io.freevariable.auktion.service.OfferService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.projection.ProjectionFactory
import org.springframework.data.rest.webmvc.RepositoryRestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@RepositoryRestController
class OfferController(private val projectionFactory: ProjectionFactory) {

    @Autowired
    lateinit var offerService: OfferService

    @GetMapping("/offers/{id}")
    fun getOffer(@PathVariable("id") offerId: Long): ResponseEntity<OfferView> {
        val offer = offerService.getOffer(offerId) ?: return ResponseEntity.notFound().build()

        val offerView = projectionFactory.createProjection(OfferView::class.java, offer)
        return ResponseEntity.ok().body(offerView)
    }

    data class CreateBidRequest(val buyerName: String, val bidPassword: String, val amount: Int)

    @PostMapping("/offers/{id}/bids")
    fun createBid(@RequestBody bid: CreateBidRequest, @PathVariable("id") offerId: Long): ResponseEntity<Bid> {

        val createdBid = offerService.createBid(offerId, bid.buyerName, bid.bidPassword, bid.amount)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok().body(createdBid)
    }

    data class CloseOfferRequest(val password: String, val selectedBid: Long)

    @PutMapping("/offers/{id}/close")
    fun closeOffer(@RequestBody request: CloseOfferRequest, @PathVariable("id") offerId: Long): ResponseEntity<String> {
        offerService.closeOffer(offerId, request.password, request.selectedBid)
        return ResponseEntity.ok().body("Offer Closed!")
    }
}