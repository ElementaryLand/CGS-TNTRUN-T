package fr.redcraft.tnt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length > 0){
            ArrayList<String> string = new ArrayList<>();
            if(sender.isOp()){
                string.add("start");
                string.add("join");
                string.add("leave");
                string.add("regen");
                return string;
            }else{
                string.add("join");
                string.add("leave");
                return string;
            }
        }
        return null;
    }
}
