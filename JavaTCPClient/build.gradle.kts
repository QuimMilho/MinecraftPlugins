plugins {
    id("java")
}

group = "net.quimmilho.mineclient"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.yaml:snakeyaml:2.0")
}

tasks.test {
}