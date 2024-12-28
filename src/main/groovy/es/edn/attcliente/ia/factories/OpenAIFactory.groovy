package es.edn.attcliente.ia.factories

import dev.langchain4j.model.azure.AzureOpenAiChatModel
import dev.langchain4j.model.azure.AzureOpenAiStreamingChatModel
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.chat.StreamingChatLanguageModel
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiStreamingChatModel
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton

@Requires(env = "openai")
@Factory
@Context
class OpenAIFactory {

    @Singleton
    ChatLanguageModel chatModel(@Value('${openai.key}') String key,
                                @Value('${openai.model}')String model) {
        return OpenAiChatModel.builder()
                .apiKey(key)
                .modelName(model)
                .build();
    }

    @Singleton
    StreamingChatLanguageModel streamingChatLanguageModel(@Value('${openai.key}') String key) {
        return OpenAiStreamingChatModel.builder()
                .apiKey(key)
                .build();

    }
}
