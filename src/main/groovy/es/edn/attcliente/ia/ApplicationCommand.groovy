package es.edn.attcliente.ia

import com.google.gson.Gson
import dev.langchain4j.data.document.Document
import dev.langchain4j.data.document.transformer.jsoup.HtmlToTextDocumentTransformer
import dev.langchain4j.model.Tokenizer
import es.edn.groogle.GmailService
import es.edn.attcliente.ia.data.Resolution
import es.edn.attcliente.ia.data.ResolutionRepository
import groovy.util.logging.Slf4j
import io.micronaut.configuration.picocli.PicocliRunner
import jakarta.inject.Inject
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters

@Slf4j
@Command(name = 'application', description = '...',
        mixinStandardHelpOptions = true)
class ApplicationCommand implements Runnable {

    @Option(names = ['-v', '--verbose'], description = '...')
    boolean verbose

    @Parameters(paramLabel = "client", description = "Keyword", index = "0")
    String customer

    static void main(String[] args) throws Exception {
        PicocliRunner.run(ApplicationCommand.class, args)
    }

    @Inject
    private GmailService gmailService
    @Inject
    private ChatAssistant chatAssistant
    @Inject
    private Tokenizer tokenizer
    @Inject
    private ResolutionRepository repository

    void run() {
        Optional<Resolution> resolucion
        if( resolucion=repository.findByCliente(customer)){
            log.info "$customer = "+resolucion.get()
            return
        }

        final def htmlToTextTransformer =new HtmlToTextDocumentTransformer()

        def messages =[]
        gmailService.eachMessage({
            body customer
        }, {
            var map = headers.collectEntries({
                var key = it.name.toString().toLowerCase()
                var value = it.value.toString()
                Map.of(key, value)
            }) as Map<String, String>

            var from = map.from ?: 'desconocido'
            var date = map.date ?: '01/01/1999'

            var text = body.length() ? body : ""
            var html = Document.from("<html><body>$text</body></html>".toString())
            var plain = htmlToTextTransformer.transform(html).text()

            messages << [
                    body: """$date, $from:
                    $plain
                    -----------------------\n\n
                    """,
                    date: date
            ]
        })

        def sorted = messages.sort{
            it.date
        }.reverse()

        String conversacion = ""
        for(def map : sorted) {
            String test = "$map.body\n$conversacion"
            int tokens = tokenizer.estimateTokenCountInText(test)
            if( tokens > 8000){
                break
            }
            conversacion = "$map.body\n$conversacion"
        }


        def result = chatAssistant.inspect(conversacion)
        log.info result

        def record = new Gson().fromJson(result, Resolution)
        record.client = customer
        record.resumen = record.resumen.take(5000)
        repository.save(record)
        log.info "Stored"
    }
}
