package com.amichal2.brief.repository

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.bson.Document

interface MongoDbService {
    fun retrieveCollectionData(): String
}

class MongoDbServiceImpl : MongoDbService {

    @KtorExperimentalAPI
    override fun retrieveCollectionData(): String {
        val collection = getCollection()
        val firstElement = collection.find().first()
        return firstElement?.toJson() ?: "empty collection"
    }

    @KtorExperimentalAPI
    private fun getCollection(dbName: String = "briefDB", collectionName: String = "articles"): MongoCollection<Document> {

        val config = HoconApplicationConfig(ConfigFactory.load())
        val username = config.property("ktor.mongodb.username").getString()
        val password = config.property("ktor.mongodb.password").getString()

        val settings = MongoClientSettings
            .builder()
            .applyConnectionString(ConnectionString("mongodb+srv://$username:$password@cluster0.josdm.mongodb.net/?retryWrites=true&w=majority"))
            .retryWrites(true)
            .build()

        val client: MongoClient = MongoClients.create(settings)
        val database = client.getDatabase(dbName)
        return database.getCollection(collectionName)
    }
}
