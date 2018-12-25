package hr.tvz.googleandroidgame.channel

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.SneakyThrows
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.FluxSink

class ForwardingMessageHandler(private val session: WebSocketSession, private val sink: FluxSink<WebSocketMessage>) : MessageHandler {
    private val om = ObjectMapper()

    @SneakyThrows
    override fun handleMessage(message: Message<*>) {
        sink.next(session.textMessage(om.writeValueAsString(message.payload)))
    }
}
