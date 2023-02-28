package io.freevariable.auktion.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
data class Offer(
    @Id @GeneratedValue val id: Long? = null,
    val title: String,
    val description: String,
    val price: Int,
    val password: String,
    val open: Boolean,
    @OneToOne(cascade = [CascadeType.ALL]) val selectedBid: Bid? = null,
    @OneToMany(mappedBy = "offer", cascade = [CascadeType.ALL]) val bids: List<Bid> = emptyList()
)
