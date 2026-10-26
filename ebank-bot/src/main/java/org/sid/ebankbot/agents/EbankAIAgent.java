package org.sid.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Service
public class EbankAIAgent {
    private ChatClient chatClient;

    public EbankAIAgent(ChatClient.Builder chatClient ,
                        ChatMemory chatMemory ,
                        ToolCallbackProvider tools) {

        this.chatClient = chatClient
                .defaultSystem("""
                        Vous etes un assistant qui se charge de répondre aux questions de l'utilisateur 
                        à propos des clients et des comptes bancaires en fonction du contexte fourni.
                        si aucun contexte n'est fourni, répond avec Je ne sais pas """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultToolCallbacks(tools)
                .build();
    }


    public String chat( String query) {
        return chatClient.prompt()
                .user(query)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "default"))
                .call()
                .content();
    }
}
