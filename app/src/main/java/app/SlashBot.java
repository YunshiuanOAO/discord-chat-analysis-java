package app;


import javax.annotation.Nonnull;

import crawler.Crawler;
import java.util.EnumSet;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;



public class SlashBot extends ListenerAdapter {
    public SlashBot(String token) throws Exception {
        EnumSet<GatewayIntent> intents = GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS);
        JDA jda = JDABuilder.createDefault(token, intents).addEventListeners(new SlashCommandListener()).build();
        jda.awaitReady();
        CommandListUpdateAction commands = jda.getGuildById("1250711874111148062").updateCommands();

        // add clawer
        commands.addCommands(
                Commands.slash("crawler", "Scraping Chat Room Data")
                        .setGuildOnly(true) // This way the command can only be executed from a guild, and not the DMs
        );
        commands.addCommands(
                Commands.slash("server", "open server to show data")
                        .setGuildOnly(true) // you can add required options like this too
        );
        commands.queue();
    }
}
    
class SlashCommandListener extends ListenerAdapter{
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event)
    {
        if (event.getGuild() == null)
            return;

        switch (event.getName())
        {
        case "crawler":
            Crawler crawler = new Crawler(event.getJDA());
            String guildID = event.getGuild().getId();
            crawler.crawl(guildID);
            break;
        case "server":
            try{
                int port = CustomHttpServer.OpenServer();
                event.reply("http://localhost:" + port + "/").setEphemeral(true).queue();
                System.out.println("success");
            }catch(Exception e){
                event.reply("error").setEphemeral(true).queue();
                System.out.println("error");
            }
            break;
        default:
            event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }    
}
