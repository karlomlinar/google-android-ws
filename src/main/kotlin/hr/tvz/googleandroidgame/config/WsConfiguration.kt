package hr.tvz.googleandroidgame.config

import hr.tvz.googleandroidgame.channel.ForwardingMessageHandler
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.support.GenericMessage
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.Flux.create
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Slf4j
@Configuration
open class WsConfiguration {

    @Bean
    @Qualifier("connections")
    internal open fun connections(): Map<String, MessageHandler> {
        return ConcurrentHashMap()
    }

    @Bean
    internal open fun webSocketHandlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter()
    }

    @Bean
    internal open fun handlerMapping(webSocketHandler: WebSocketHandler): HandlerMapping {
        val simpleUrlHandlerMapping = SimpleUrlHandlerMapping()
        simpleUrlHandlerMapping.order = 10
        val mappings = HashMap<String, Any>()
        mappings["/google"] = webSocketHandler
        simpleUrlHandlerMapping.urlMap = mappings
        return simpleUrlHandlerMapping
    }

    @Bean
    internal open fun webSocketHandler(@Qualifier("connections") connections: MutableMap<String, MessageHandler>): WebSocketHandler {

        return WebSocketHandler { session -> session.send(create {
            sink -> val handler = ForwardingMessageHandler(session, sink)

            connections[session.id] = handler
            this.channel().subscribe { connections[session.id] }
            handler.handleMessage(GenericMessage<String>(session.id))
        }).doFinally {
            this.channel().unsubscribe(connections[session.id])
            connections.remove(session.id)
            }
        }
    }

    @Bean
    internal open fun channel(): PublishSubscribeChannel {
        return PublishSubscribeChannel()
    }
}


