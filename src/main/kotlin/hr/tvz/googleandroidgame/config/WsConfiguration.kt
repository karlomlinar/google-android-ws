package hr.tvz.googleandroidgame.config

import lombok.extern.slf4j.Slf4j
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.messaging.simp.config.MessageBrokerRegistry



@Slf4j
@Configuration
@EnableWebSocketMessageBroker
open class WsConfiguration : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(config: MessageBrokerRegistry?) {
        config!!.enableSimpleBroker("/topic")
        config.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry?) {
        registry!!.addEndpoint("/gs-guide-websocket").withSockJS()
    }


    /*@Bean
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
*/
}


