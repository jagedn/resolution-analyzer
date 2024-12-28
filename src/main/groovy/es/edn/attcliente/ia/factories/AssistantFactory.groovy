package es.edn.attcliente.ia.factories

import dev.langchain4j.chain.ConversationalChain
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.Tokenizer
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer
import dev.langchain4j.service.AiServices
import es.edn.attcliente.ia.ChatAssistant
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
@Context
class AssistantFactory {

    @Singleton
    ConversationalChain conversationalChain(ChatLanguageModel model){
        ConversationalChain.builder()
        .chatLanguageModel(model)
        .chatMemory(MessageWindowChatMemory.withMaxMessages(30))
        .build()
    }

    @Singleton
    ChatAssistant chatAssistant(ChatLanguageModel chatModel) {
        return AiServices.builder(ChatAssistant.class)
                .chatLanguageModel(chatModel)
                .build();
    }

    @Singleton
    Tokenizer tokenizer(){
        new HuggingFaceTokenizer()
    }

}
