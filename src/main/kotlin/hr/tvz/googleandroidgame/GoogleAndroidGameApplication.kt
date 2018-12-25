package hr.tvz.googleandroidgame

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class GoogleAndroidGameApplication

fun main(args: Array<String>) {
    SpringApplication.run(GoogleAndroidGameApplication::class.java, *args)
}


