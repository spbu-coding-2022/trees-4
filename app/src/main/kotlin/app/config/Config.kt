package app.config

data class Config(
    val postgresHost: String,
    val postgresPort: Int,
    val postgresDatabase: String,
    val postgresUsername: String,
    val postgresPassword: String,

    val neo4jUri: String,
    val neo4jUsername: String,
    val neo4jPassword: String
    )


