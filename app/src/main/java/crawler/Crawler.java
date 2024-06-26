package crawler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.exceptions.MissingAccessException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.io.File;


public class Crawler {
    JDA jda;

    public Crawler(JDA jda) {
        this.jda = jda;
        jda.addEventListener(new ReadyListener());
    }

    public void crawl(String guildID){
        File dataFile = new File("src/main/resource/data/"+guildID+"/data.json");
        File externalFile = new File("src/main/resource/data/"+guildID+"/external.json");
        try{
            
            DataSet dataSet = new DataSet(dataFile);
            DataSet ExternalDataSet = new DataSet(externalFile);            
            Guild guild = jda.getGuildById(guildID);
            for (TextChannel channel : guild.getTextChannels()){
                String channelId = channel.getId();
                String channelName = channel.getName();
                String channelCategoryName = channel.getParentCategory() != null ? channel.getParentCategory().getName() : "";

                List<Message> messages;
                try{
                    while(!(messages = channel.getHistoryAfter(dataSet.getChannelLastUpdate(channelId), 100).complete().getRetrievedHistory()).isEmpty()){
                        messages.reversed().forEach( m -> dataSet.addMessage(m));
                        dataSet.save();
                        System.out.println("crawling " + channel.getName());
                    }
                } catch(MissingAccessException e){
                    System.err.println("Missing permission: " + channelName);
                }
                
                ExternalDataSet.addExternalChannel(channelId,channelName,channelCategoryName);
            }
            ExternalDataSet.saveExternalData();          
            guild.loadMembers().onSuccess(members -> {
                for (Member member : members) {
                    ExternalDataSet.addExternalUser(member.getId(), member.getEffectiveName(), member.getUser().getName());
                    try{
                        ExternalDataSet.saveExternalData();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("Data has been updated, use /server on discord guild to generate statistics");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        System.out.println("hello world");
    }
}