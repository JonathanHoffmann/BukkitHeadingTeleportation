package me.Jonnyfant.headingteleportation;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class headingteleportation extends JavaPlugin {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
    {
        final Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players only!");
            return true;
        }

        Location currentLocation = player.getLocation();
        Location targetLocation = currentLocation;
        float yaw = currentLocation.getYaw();

        //read arguments from input


        int length;
        try {
            length = Integer.parseInt(args[0]);
        }
        catch (Exception e)
        {
            return true;
        }
        int angle;
        try {
            angle = Integer.parseInt(args[1]);
        }
        catch (Exception e)
        {
            angle = 0;
        }

        float targetangle = currentLocation.getYaw() + angle;
        while (targetangle <0)
        {
            targetangle+=360;
        }
        while (targetangle >360)
        {
            targetangle-=360;
        }
        targetLocation.setYaw(targetangle);
        //currentLocation.setYaw(targetangle);
        double x = targetLocation.getX();
        double z = targetLocation.getZ();
        double percentx=0;
        double percentz=0;


        if(targetangle <=90)  //+z -x
        {
            targetangle = shortenTo90Degree(targetangle);
            percentx= targetangle/90*100;
            percentz=100-percentx;
            x=x-(length*percentx/100);
            z=z+(length*percentz/100);
        }
        else if (targetangle <=180)  //-z -x
        {
            targetangle = shortenTo90Degree(targetangle);
            percentz=targetangle/90*100;
            percentx=100-percentz;
            x=x-(length*percentx/100);
            z=z-(length*percentz/100);
        }
        else if (targetangle <=270)  //-z +x
        {
            targetangle = shortenTo90Degree(targetangle);
            percentx= targetangle/90*100;
            percentz=100-percentx;
            x=x+(length*percentx/100);
            z=z-(length*percentz/100);
        }
        else if (targetangle <=360)  //+z+x
        {
            targetangle = shortenTo90Degree(targetangle);
            percentz=targetangle/90*100;
            percentx=100-percentz;
            x=x+(length*percentx/100);
            z=z+(length*percentz/100);
        }
        else
        {
            return  true;
        }


        targetLocation.setX(x);
        targetLocation.setZ(z);
        //currentLocation.setX(x);
        //currentLocation.setZ(z);
        player.teleport(targetLocation);
        return true;
    }

    public float shortenTo90Degree (float a)
    {
        while (a>90)
        {
            a-=90;
        }
        return a;
    }
}
