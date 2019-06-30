package hr.tvz.googleandroidgame.controller

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.socket.config.annotation.EnableWebSocket


@Controller
class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    @Throws(Exception::class)
    fun google(message: String): String {
        Thread.sleep(1000) // simulated delay
        return message + ", odjebi"
    }

}