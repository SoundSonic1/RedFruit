package com.example.redfruit.util

import com.beust.klaxon.Klaxon

/**
 * Factory to create new Klaxon objects because Klaxon is not thread safe
 */
class KlaxonFactory : IFactory<Klaxon> {
    override fun build() = Klaxon()
}