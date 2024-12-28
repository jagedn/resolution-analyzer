package es.edn.attcliente.ia

import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.V

interface ChatAssistant {

    @SystemMessage(fromResource = "system-clean.txt")
    String clean(String conversacion);


    @SystemMessage(fromResource = "system-analyze.txt")
    String inspect(String conversacion);

}
