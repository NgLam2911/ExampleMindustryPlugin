package nglam2911.example;

import arc.Events;
import arc.util.CommandHandler;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.mod.Plugin;
import mindustry.net.Administration;

import java.util.Objects;

public class ExamplePlugin extends Plugin {
    @Override
    public void init() {
        Events.on(EventType.BlockBuildBeginEvent.class, event -> {
            Unit unit = event.unit;
            Team team = event.team;
            if (unit.isPlayer()){
                Player player = unit.getPlayer();
                Log.info("Thằng loz " + player.name() + " team " + team.name + " đang build kìa");
            }
        });

        Vars.netServer.admins.addChatFilter((Player player, String message) -> {
            if (Objects.equals(message, "Tao muốn đi ỉa")){
                return "Tao đéo muốn ỉa";
            }
            return message;
        });

        Vars.netServer.admins.addActionFilter((Administration.PlayerAction action) -> {
            return action.type != Administration.ActionType.breakBlock;
        });
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        handler.register("hello" ,"Say hi", (String[] args) -> {
            Log.info("Hi :>");
        });
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.register("name", "Print ur name", (String[] args, Player player) -> {
            player.sendMessage("Your name is " + player.name());
        });
    }
}
